package com.example.webservicepostbackbuilder.repository;

import com.example.webservicepostbackbuilder.services.BaseConverter;
import com.example.webservicepostbackbuilder.services.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Campaign {

    private String sid;
    private int numericId;

    private HashMap<String, String> eids = new HashMap<>();

    public Campaign(){

        this.sid = "3";
        BaseConverter converter = new BaseConverter();
        this.numericId = converter.decode(this.sid);


        try {
            String token = ApiClient.getAuthToken();
            if (token == null) {
                throw new RuntimeException("Fehler beim Abrufen des Tokens");
            }

            String jsonResponse = ApiClient.getData(token,"https://api.skynet.mcanism.com/event_id/?offer=" + numericId);
            parseJsonResponse(jsonResponse);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen der Kampagnendaten", e);
        }



    }

    private void parseJsonResponse(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        if (rootNode.isArray()) {  // Sicherstellen, dass es ein Array ist
            for (JsonNode node : rootNode) {
                String sid = node.has("sid") ? node.get("sid").asText() : null;
                String name = node.has("name") ? node.get("name").asText() : null;

                if (sid != null && name != null) {
                    eids.put(sid, name);
                }
            }
        }
    }

    public int getNumericId() {
        return numericId;
    }

    public HashMap<String, String> getEids() {
        return eids;
    }
}
