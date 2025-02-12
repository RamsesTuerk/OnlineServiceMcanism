package com.example.webservicepostbackbuilder.repository;

import com.example.webservicepostbackbuilder.services.BaseConverter;
import com.example.webservicepostbackbuilder.services.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Campaign {

    private String sid;
    private int numericId;

    private String testLink;

    private String campaignName;

    private String campaignLogoURL;

    private HashMap<String, String> eids = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    public Campaign(){

        this.sid = "5h";
        BaseConverter converter = new BaseConverter();
        this.numericId = converter.decode(this.sid);


        try {
            String token = ApiClient.getAuthToken();
            if (token == null) {
                throw new RuntimeException("Fehler beim Abrufen des Tokens");
            }
            String jsonResponse;
            jsonResponse = ApiClient.getData(token,"https://api.skynet.mcanism.com/event_id/?offer=" + numericId);
            parseJsonResponse(jsonResponse);
            jsonResponse = ApiClient.getData(token,"https://api.skynet.mcanism.com/offer_media_mapping/?media=15&offer=" + numericId);
            parseJsonResponseTrackingLink(jsonResponse);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen der Kampagnendaten", e);
        }
    }

    private void parseJsonResponseTrackingLink(String jsonResponse) throws Exception {

        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        if (rootNode.isArray()) {  // Sicherstellen, dass es ein Array ist
            for (JsonNode node : rootNode) {
                testLink = node.has("default_tracking_url") ? node.get("default_tracking_url").asText() : null;
                JsonNode tempOfferNode = node.has("offer_info") ? node.get("offer_info") : null;
                assert tempOfferNode != null;
                campaignName = tempOfferNode.has("name") ? tempOfferNode.get("name").asText(): null;
                campaignLogoURL = tempOfferNode.has("logo") ? tempOfferNode.get("logo").asText() : null;
            }
        }
    }

    private void parseJsonResponse(String jsonResponse) throws Exception {

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

    public String getTestLink(){
        return testLink;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public HashMap<String, String> getEids() {
        return eids;
    }

    public String getCampaignLogoURL() {
        return campaignLogoURL;
    }
}
