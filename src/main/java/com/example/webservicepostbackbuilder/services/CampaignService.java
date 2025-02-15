package com.example.webservicepostbackbuilder.services;

import com.example.webservicepostbackbuilder.repository.Campaign;
import com.example.webservicepostbackbuilder.repository.CampaignRepository;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }

    public void saveCampaign(Campaign campaign){
        campaignRepository.save(campaign);
    }

    public Campaign getCampaignByNumericId(int numericId){
        return campaignRepository.findByNumericId(numericId).orElseThrow(() -> new RuntimeException("Campaign nicht gefunden mit id: "));
    }

    public void deleteCampaign(int numericId){
        campaignRepository.deleteById(numericId);
    }

}
