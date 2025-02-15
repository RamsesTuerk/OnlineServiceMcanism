package com.example.webservicepostbackbuilder.controller;

import com.example.webservicepostbackbuilder.repository.Campaign;
import com.example.webservicepostbackbuilder.services.BaseConverter;
import com.example.webservicepostbackbuilder.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @GetMapping("/test")
    public String test(){
        return "askForCampaignId";
    }


    @GetMapping("/campaign")
    public String getCampaign(@RequestParam("campaignId") String campaignId, Model model) {
        BaseConverter baseConverter = new BaseConverter();
        int numericId = baseConverter.decode(campaignId);

        Campaign campaign;

        try {
            // Prüfen, ob die Kampagne bereits existiert
            campaign = campaignService.getCampaignByNumericId(numericId);
        } catch (Exception e) {
            // Falls nicht vorhanden, erstelle eine neue Kampagne mit API-Daten
            campaign = new Campaign(campaignId);
            campaignService.saveCampaign(campaign);
        }

        model.addAttribute("campaign", campaign);
        return "askForCampaignId";
    }

}
