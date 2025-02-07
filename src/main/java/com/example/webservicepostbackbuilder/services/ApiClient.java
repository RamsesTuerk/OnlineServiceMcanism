package com.example.webservicepostbackbuilder.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiClient {

    private static final String LOGIN_URL = "https://api.skynet.mcanism.com/rest-auth/login/";

    private static final String EMAIL = "ramses@mcanism.com";
    private static final String PASSWORD = "Hausaufgaben!1";

    public static String getAuthToken() throws IOException, InterruptedException {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", EMAIL, PASSWORD);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());

        return jsonNode.has("token") ? jsonNode.get("token").asText() : null;
    }

    public static String getData(String token, String offer_url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(offer_url))
                .header("Authorization", "JWT " + token)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
