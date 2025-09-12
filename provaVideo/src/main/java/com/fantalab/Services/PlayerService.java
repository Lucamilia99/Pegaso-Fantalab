package com.fantalab.Services;

import com.fantalab.models.Player;
import com.fantalab.models.PlayerInfo;
import org.json.JSONObject;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerService {
    private final ApiService apiService;

    public PlayerService(String authToken) {
        this.apiService = new ApiService(authToken);
    }


    public List<Player> getFilteredPlayers() throws Exception {
        List<JSONObject> strategyPlayers = apiService.getPlayerStrategies();
        Map<String, JSONObject> playerInfos = apiService.getPlayerInfosFromLocalFile();

        return strategyPlayers.stream()
                .filter(player -> {
                    int price = player.optInt("price", 0);
                    return price > 0;
                })
                .map(Player::new)
                .filter(player -> playerInfos.containsKey(player.getPlayerId()))
                .collect(Collectors.toList());
    }

    public Map<String, PlayerInfo> getPlayerInfoMap() throws Exception {
        Map<String, JSONObject> playerInfoJsonMap = apiService.getPlayerInfosFromLocalFile();
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();

        for (Map.Entry<String, JSONObject> entry : playerInfoJsonMap.entrySet()) {
            playerInfoMap.put(entry.getKey(), new PlayerInfo(entry.getValue()));
        }

        return playerInfoMap;
    }

    public List<Player> getPlayersWithInfo() throws Exception {
        List<Player> filteredPlayers = getFilteredPlayers();
        Map<String, PlayerInfo> playerInfoMap = getPlayerInfoMap();

        return filteredPlayers.stream()
                .filter(player -> playerInfoMap.containsKey(player.getPlayerId()))
                .collect(Collectors.toList());
    }

    public void printPlayersWithNames(List<Player> players, Map<String, PlayerInfo> playerInfoMap) {
        // Mappa per l'ordinamento dei ruoli
        Map<String, Integer> ROLE_ORDER = Map.ofEntries(
                Map.entry("Por", 1), Map.entry("Ds", 2), Map.entry("Dc", 3),
                Map.entry("B", 4), Map.entry("Dd", 5), Map.entry("E", 6),
                Map.entry("M", 7), Map.entry("C", 8), Map.entry("W", 9),
                Map.entry("T", 10), Map.entry("A", 11), Map.entry("Pc", 12)
        );

        // Ordina per ruolo principale e poi per fascia (1, 2, 3, 4)
        List<Player> sortedPlayers = players.stream()
                .filter(player -> player.getFascia() != 0)
                .sorted(Comparator
                        .comparingInt((Player player) -> {
                            String mainRole = player.getMantraRoles() != null && !player.getMantraRoles().isEmpty()
                                    ? player.getMantraRoles().get(0)
                                    : "UNKNOWN";
                            return ROLE_ORDER.getOrDefault(mainRole, Integer.MAX_VALUE);
                        })
                        .thenComparingInt(Player::getFascia) // Ordina per fascia (crescente: 1, 2, 3, 4)
                )
                .toList();

        // Stampa i risultati
        String currentRole = "";
        for (Player player : sortedPlayers) {
            PlayerInfo info = playerInfoMap.get(player.getPlayerId());
            String mainRole = player.getMantraRoles() != null && !player.getMantraRoles().isEmpty()
                    ? player.getMantraRoles().get(0)
                    : "UNKNOWN";

            if (!mainRole.equals(currentRole)) {
                currentRole = mainRole;
                System.out.println("\n=== " + currentRole + " ===");
            }

            System.out.printf("%s - %s - Fascia %d - Crediti:%.1f%n",
                    info != null ? info.getName() : "N/A",
                    info != null ? info.getTeam() : "N/A",
                    player.getFascia(),
                    player.getPrice());
            System.out.println("Info aggiuntive FVM: " + info.getFvm());
            System.out.println("Info aggiuntive QUOTAZIONE: " + info.getQuotazione());

            if (player.getMantraRoles() != null && !player.getMantraRoles().isEmpty()) {
                System.out.println("   Ruoli: " + String.join(", ", player.getMantraRoles()));
            }

            if (player.getComment() != null && !player.getComment().isEmpty()) {
                String comment = player.getComment();
                System.out.println("   Commento: " + comment);
            }

            if (player.getNotes() != null && !player.getNotes().isEmpty()) {
                List<String> notes = player.getNotes();
                for (String note : notes) {
                    System.out.println("   Note: " + note);
                }
            }
            System.out.println("---");
        }
    }
}