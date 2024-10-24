package com.example.webservicepostbackbuilder.services;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Templates.
 */
@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    // Konstruktor, um das TemplateRepository zu injizieren.
    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Speichert ein Template. Aktualisiert ein bestehendes Template, wenn die ID gesetzt ist.
     *
     * @param template Das zu speichernde Template.
     */
    public void saveTemplate(Template template) {
        // Überprüfen, ob die ID der Vorlage gesetzt ist
        if (template.getId() != null) {
            // Vorhandenes Template aktualisieren
            Template existingTemplate = templateRepository.findById(template.getId())
                    .orElseThrow(() -> new RuntimeException("Template nicht gefunden"));
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

    /**
     * Gibt ein Template anhand der ID zurück.
     *
     * @param id Die ID des gesuchten Templates.
     * @return Das gefundene Template.
     */
    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template nicht gefunden mit ID: " + id));
    }

    /**
     * Gibt eine Liste aller Templates zurück.
     *
     * @return Eine Liste aller Templates.
     */
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    /**
     * Löscht ein Template anhand der ID.
     *
     * @param id Die ID des zu löschenden Templates.
     */
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    /**
     * Gibt ein Template anhand des Namens zurück.
     *
     * @param name Der Name des gesuchten Templates.
     * @return Das gefundene Template.
     */
    public Template getTemplateByName(String name) {
        return templateRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Template nicht gefunden mit Namen: " + name));
    }
}
