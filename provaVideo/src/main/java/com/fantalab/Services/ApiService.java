package com.fantalab.Services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiService {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    String numberPayload = "a9970f77-d028-4535-a7d2-b8a8e141909f";
    String apiUrl = "https://api.fantalab.it/v2/player-strategy";
    String payload = "{\"strategy_id\":\"" + numberPayload + "\"}";
    private final String authToken;
    private final String localFilePath = "C:\\Users\\luca.milia\\players_decompressed.json";;

    // Costruttore originale per retrocompatibilità
    public ApiService(String authToken) {
        this.authToken = authToken;
    }


    public List<JSONObject> getPlayerStrategies() throws Exception {
        HttpRequest request = getHttpRequest(apiUrl, payload);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("HTTP Error: " + response.statusCode() + " - " + response.body());
        }

        JSONArray responseArray = new JSONArray(response.body());
        List<JSONObject> result = new ArrayList<>();

        for (int i = 0; i < responseArray.length(); i++) {
            result.add(responseArray.getJSONObject(i));
        }

        return result;
    }

    // Metodo per leggere dal file locale
    public Map<String, JSONObject> getPlayerInfosFromLocalFile() throws Exception {
        Map<String, JSONObject> playerInfoMap = new HashMap<>();
        JsonFactory factory = new JsonFactory();

        try (FileInputStream fis = new FileInputStream(localFilePath);
             JsonParser parser = factory.createParser(fis)) {

            // Avanza fino all'inizio dell'array
            while (parser.nextToken() != JsonToken.START_ARRAY) {
                // Skip fino all'inizio dell'array
            }

            // Itera attraverso tutti gli elementi dell'array
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    JSONObject playerInfo = parsePlayerObject(parser);
                    String season = playerInfo.optString("season");
                    String playerId = playerInfo.optString("player_id");

                    if ("s_25_26".equals(season) && playerId != null) {
                        // Mantieni solo i campi necessari
                        JSONObject minimalInfo = createMinimalPlayerInfo(playerInfo);
                        playerInfoMap.put(playerId, minimalInfo);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Errore nella lettura del file locale: " + localFilePath + " - " + e.getMessage(), e);
        }
        return playerInfoMap;
    }

    private JSONObject parsePlayerObject(JsonParser parser) throws Exception {
        JSONObject playerInfo = new JSONObject();

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
            parser.nextToken(); // Avanza al valore

            switch (parser.currentToken()) {
                case VALUE_STRING:
                    playerInfo.put(fieldName, parser.getValueAsString());
                    break;
                case VALUE_NUMBER_INT:
                    playerInfo.put(fieldName, parser.getValueAsInt());
                    break;
                case VALUE_NUMBER_FLOAT:
                    playerInfo.put(fieldName, parser.getValueAsDouble());
                    break;
                case VALUE_TRUE:
                    playerInfo.put(fieldName, true);
                    break;
                case VALUE_FALSE:
                    playerInfo.put(fieldName, false);
                    break;
                case START_ARRAY:
                case START_OBJECT:
                    // Salta oggetti/array complessi (stats, etc.)
                    parser.skipChildren();
                    break;
                default:
                    // Ignora altri token
            }
        }

        return playerInfo;
    }

    private JSONObject createMinimalPlayerInfo(JSONObject fullInfo) {
        JSONObject minimal = new JSONObject();
        minimal.put("player_id", fullInfo.optString("player_id"));
        minimal.put("name", fullInfo.optString("name"));
        minimal.put("team_name_short", fullInfo.optString("team_name_short"));
        minimal.put("role", fullInfo.optString("role"));
        minimal.put("season", fullInfo.optString("season"));
        minimal.put("quotazione", fullInfo.optInt("quotazione"));
        minimal.put("fvm", fullInfo.optInt("fvm"));
        return minimal;
    }

    private HttpRequest getHttpRequest(String apiUrl, String payload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
    }
}