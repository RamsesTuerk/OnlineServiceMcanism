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
        templateRepository.save(template);
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

}
