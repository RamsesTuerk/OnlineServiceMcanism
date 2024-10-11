package com.example.webservicepostbackbuilder.controller;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    // Landing page with list of templates and optional EID and CID
    @GetMapping("/")
    public String index(@RequestParam(required = false) String eid,
                        @RequestParam(required = false) String cid,
                        Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("eid", eid != null ? eid : "");
        model.addAttribute("cid", cid != null ? cid : "");
        return "landingPage";
    }

    // Form for creating or editing a template
    @GetMapping("/template")
    public String showTemplateForm(Model model) {
        model.addAttribute("template", new Template());
        return "templateForm";
    }

    // Edit an existing template by its name
    @GetMapping("/template/edit/{name}")
    public String editTemplate(@PathVariable String name, Model model) {
        Template template = templateService.getTemplate(name);
        if (template == null) {
            return "redirect:/templates";
        }
        model.addAttribute("template", template);
        return "templateForm";
    }

    // Save a new or edited template
    @PostMapping("/template/save")
    public String saveTemplate(@ModelAttribute Template template) {
        templateService.saveTemplate(template);
        return "redirect:/templates";
    }

    // List all templates
    @GetMapping("/templates")
    public String listTemplates(Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        return "templateList";
    }

    // View a specific template by its name
    @GetMapping("/template/{name}")
    public String viewTemplate(@PathVariable String name, Model model) {
        Template template = templateService.getTemplate(name);
        model.addAttribute("template", template);
        return "templateView";
    }

    // Delete a template by its name
    @PostMapping("/template/delete/{name}")
    public String deleteTemplate(@PathVariable String name) {
        templateService.deleteTemplate(name);
        return "redirect:/templates";
    }

    // Generate content based on selected template and content type
    @PostMapping("/template/generate")
    public String generateContent(@RequestParam String selectedTemplate,
                                  @RequestParam String contentType,
                                  @RequestParam String eid,
                                  @RequestParam String cid,
                                  @RequestParam(required = false) String amount,
                                  @RequestParam String id,
                                  Model model) {

        Template template = templateService.getTemplate(selectedTemplate);
        if (template == null) {
            return "redirect:/";
        }

        String content = switch (contentType) {
            case "sale" -> template.getSaleContent();
            case "lead" -> template.getLeadContent();
            case "install" -> template.getInstallContent();
            default -> "";
        };

        content = content.replace("{eid}", eid)
                .replace("{cid}", cid)
                .replace("{amount}", amount != null ? amount : "")
                .replace("{id}", id);

        model.addAttribute("generatedContent", content);
        model.addAttribute("templates", templateService.getAllTemplates());

        return "landingPage";
    }

    // Load template-specific content in an Iframe
    @PostMapping("/loadContent")
    public String loadContent(@RequestParam String templateName,
                              @RequestParam String saleType,
                              @RequestParam(required = false) String eid,
                              @RequestParam(required = false) String cid,
                              Model model) {
        // Hier den Inhalt basierend auf dem Template und Sale Typ abrufen
        Template template = templateService.getTemplate(templateName);
        String content = switch (saleType) {
            case "sale" -> template.getSaleContent();
            case "lead" -> template.getLeadContent();
            case "install" -> template.getInstallContent();
            default -> "";
        };

        // Füge die Werte für eid und cid zum Model hinzu
        model.addAttribute("eid", eid);
        model.addAttribute("cid", cid);
        model.addAttribute("content", content);
        model.addAttribute("description", template.getDescription());

        return "templateContent"; // Erstelle eine separate Ansicht für den Inhalt
    }


}
