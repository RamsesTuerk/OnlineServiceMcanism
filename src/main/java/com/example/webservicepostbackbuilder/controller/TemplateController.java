package com.example.webservicepostbackbuilder.controller;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String eid,
                        @RequestParam(required = false) String cid,
                        Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("eid", eid != null ? eid : "");
        model.addAttribute("cid", cid != null ? cid : "");
        return "PostbackBuilder"; // Ensure this is the correct view name
    }


    @GetMapping("/template")
    public String showTemplateForm(Model model) {
        model.addAttribute("template", new Template());
        return "templateForm";
    }

    @GetMapping("/templates")
    public String listTemplates(Model model) {
        List<Template> templates = templateService.getAllTemplates();
        model.addAttribute("templates", templates);
        return "templateList";
    }

    @GetMapping("/template/{name}")
    public String viewTemplate(@PathVariable String name, Model model) {
        Template template = templateService.getTemplate(name);
        model.addAttribute("name", template.getName());
        model.addAttribute("content", template.getSaleContent());
        return "templateView";
    }

    @PostMapping("/template/delete/{name}")
    public String deleteTemplate(@PathVariable String name) {
        templateService.deleteTemplate(name);
        return "redirect:/templates";
    }
    @PostMapping("/template/generate")
    public String generateContent(
            @RequestParam String selectedTemplate,
            @RequestParam String contentType,
            @RequestParam String eid,
            @RequestParam String cid,
            @RequestParam(required = false) String amount,
            @RequestParam String id,
            Model model) {

        System.out.println("Selected Template: " + selectedTemplate);
        System.out.println("Content Type: " + contentType);

        // Fetch the selected template
        Template template = templateService.getTemplate(selectedTemplate);
        if (template == null) {
            System.out.println("Template not found! Available templates:");
            templateService.getAllTemplates().forEach(t -> System.out.println(" - " + t.getName()));
            return "redirect:/"; // Redirect or show an error if the template is not found
        }

        // Determine which content to use based on the selected type
        String content = "";
        switch (contentType) {
            case "sale":
                content = template.getSaleContent();
                break;
            case "lead":
                content = template.getLeadContent();
                break;
            case "install":
                content = template.getInstallContent();
                break;
            default:
                break;
        }

        // Replace placeholders with actual values
        content = content.replace("{eid}", eid)
                .replace("{cid}", cid)
                .replace("{amount}", amount != null ? amount : "")
                .replace("{id}", id);

        // Log generated content
        System.out.println("Generated Content: " + content);
        model.addAttribute("generatedContent", content);
        model.addAttribute("templates", templateService.getAllTemplates()); // To repopulate the dropdown

        return "PostbackBuilder"; // Ensure this is the correct Thymeleaf template name
    }

    @GetMapping("/template/edit/{name}")
    public String editTemplate(@PathVariable String name, Model model) {
        Template template = templateService.getTemplate(name);
        if (template == null) {
            return "redirect:/templates"; // Falls das Template nicht existiert, umleiten
        }
        model.addAttribute("template", template); // Fülle das Formular mit den Template-Daten
        return "templateForm"; // Zeige das Formular zur Bearbeitung an
    }

    @PostMapping("/template/save")
    public String saveTemplate(@ModelAttribute Template template) {
        templateService.saveTemplate(template); // Template wird gespeichert, egal ob neu oder aktualisiert
        return "redirect:/templates"; // Zurück zur Liste
    }
}
