package com.example.webservicepostbackbuilder.services;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void saveTemplate(Template template) {
        // Überprüfen, ob die ID der Vorlage gesetzt ist
        if (template.getId() != null) {
            // Vorhandenes Template aktualisieren
            Template existingTemplate = templateRepository.findById(template.getId()).orElseThrow(() -> new RuntimeException("Template not found"));
            existingTemplate.setName(template.getName());
            existingTemplate.setDescription(template.getDescription());
            existingTemplate.setSaleContent(template.getSaleContent());
            existingTemplate.setLeadContent(template.getLeadContent());
            existingTemplate.setInstallContent(template.getInstallContent());
            existingTemplate.setAmountPlaceholder(template.getAmountPlaceholder());
            existingTemplate.setIdPlaceholder(template.getIdPlaceholder());
            templateRepository.save(existingTemplate);
        } else {
            // Neues Template speichern
            templateRepository.save(template);
        }
    }

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + id));
    }


    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    public Template getTemplateByName(String name) {
        return templateRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Template not found with name: " + name));
    }
}
