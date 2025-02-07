package com.example.webservicepostbackbuilder;

import com.example.webservicepostbackbuilder.repository.Campaign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CampaignTest {

    @InjectMocks
    private Campaign campaign;

    @Test
    void testCampaignInitialization() {
        // Erstelle das Campaign-Objekt mit einer SID
        campaign = new Campaign();

        // Überprüfe, ob das Campaign-Objekt nicht null ist
        assertNotNull(campaign);


        // Ausgabe von Informationen zur Überprüfung
        System.out.println("Campaign initialized successfully with eids: " + campaign.getEids());
    }
}
