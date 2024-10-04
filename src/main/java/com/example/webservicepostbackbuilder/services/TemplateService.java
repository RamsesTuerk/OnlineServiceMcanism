package com.example.webservicepostbackbuilder.services;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void saveTemplate(Template template) {
        templateRepository.save(template);
    }

    public Template getTemplate(String name) {
        Template template = templateRepository.findByName(name);
        System.out.println("Retrieved Template: " + (template != null ? template.getName() : "Not Found"));
        return template;
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public void deleteTemplate(String name) {
        templateRepository.deleteByName(name);
    }
}
