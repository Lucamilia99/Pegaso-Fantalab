package com.pegaso.PEGASO.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pegaso.PEGASO.exception.ControllerException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.pegaso.PEGASO.controller.VideoControllerRestApi.OBJECT_MAPPER;

public class RestClient {

    private static final String AUTH_TOKEN;
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();


    static {
        try {
            AUTH_TOKEN = getToken();
        } catch (IOException | InterruptedException | ControllerException e) {
            throw new RuntimeException("Errore durante l'inizializzazione del token.", e);
        }
    }

    private static String getToken() throws IOException, InterruptedException, ControllerException {
        String url = "https://signin-api.prod.multiversity.click/oauth/token";

        ObjectNode bodyJson = OBJECT_MAPPER.createObjectNode()
                .put("client_id", "4")
                .put("client_secret", "x4KFuQnc95bjsmFPV9CbgvEtKEKRFkMGvkdpRNrC")
                .put("grant_type", "password")
                .put("username", "lmilia_0312301081")
                .put("password", "ec24a54d")
                .put("scope", "*");

        HttpResponse<String> response = sendPostRequest(url, bodyJson);

        if (response.statusCode() != 200) {
            throw new ControllerException("Errore autenticazione: ", response.statusCode());
        }

        return OBJECT_MAPPER.readTree(response.body()).get("access_token").asText();
    }

    public static HttpResponse<String> sendPostRequest(String url, ObjectNode bodyJson) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyJson.toString()))
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendGetRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Accept", "application/json")
                .GET()
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
