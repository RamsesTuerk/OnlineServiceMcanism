package com.example.webservicepostbackbuilder.controller;

import com.example.webservicepostbackbuilder.repository.Template;
import com.example.webservicepostbackbuilder.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Controller
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    // Landingpage mit der Liste aller Templates und optionaler EID und CID
    @GetMapping("/")
    public String index(@RequestParam(required = false) String eid,
                        @RequestParam(required = false) String cid,
                        Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("eid", eid != null ? eid : "");
        model.addAttribute("cid", cid != null ? cid : "");
        return "landingPage";
    }

    // Formular zur Erstellung oder Bearbeitung eines Templates
    @GetMapping("/template")
    public String showTemplateForm(Model model) {
        model.addAttribute("template", new Template());
        return "templateForm";
    }

    // Template speichern (neu oder bearbeitet)
    @PostMapping("/template/save")
    public String saveTemplate(@ModelAttribute Template template) {
        templateService.saveTemplate(template);
        return "redirect:/templates";
    }

    // Auflistung aller Templates
    @GetMapping("/templates")
    public String listTemplates(Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        return "templateList";
    }

    // Formular zum Bearbeiten eines bestehenden Templates anhand der ID
    @GetMapping("/template/edit/{id}")
    public String editTemplate(@PathVariable Long id, Model model) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return "redirect:/templates";
        }
        model.addAttribute("template", template);
        return "templateForm";
    }

    // Spezifisches Template anzeigen anhand der ID
    @GetMapping("/template/{id}")
    public String viewTemplate(@PathVariable Long id, Model model) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return "redirect:/templates";
        }
        model.addAttribute("template", template);
        return "templateView";
    }

    // Template löschen anhand der ID
    @PostMapping("/template/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return "redirect:/templates";
    }

    // Dynamische Optionen basierend auf der Template-ID laden
    @GetMapping("/templates/{id}/options")
    public ResponseEntity<Map<String, Boolean>> getTemplateOptions(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Map<String, Boolean> options = Map.of(
                "sale", template.getSaleContent() != null && !template.getSaleContent().isEmpty(),
                "lead", template.getLeadContent() != null && !template.getLeadContent().isEmpty(),
                "install", template.getInstallContent() != null && !template.getInstallContent().isEmpty()
        );

        return ResponseEntity.ok(options);
    }

    // Inhalt basierend auf ausgewähltem Template und Typ generieren
    @PostMapping("/template/generate")
    public String generateContent(
            @RequestParam String content, // Der ursprüngliche Inhalt
            @RequestParam String eid,
            @RequestParam String cid,
            @RequestParam String id,
            @RequestParam(required = false) String amount,
            @RequestParam(required = false) String track_id, // Optionaler Parameter
            @RequestParam String description,
            @RequestParam String selectedTemplate,
            Model model) {

        // Vermeidung von Nullwerten und sicherstellen, dass Platzhalter korrekt ersetzt werden
        String generatedContent = content.replace("{eid}", eid)
                .replace("{track_id}", track_id != null ? track_id : "")
                .replace("{cid}", cid)
                .replace("{id}", id)
                .replace("{amount}", amount != null ? amount : "");

        // Daten ins Model legen
        model.addAttribute("content", content); // Originalinhalt bleibt erhalten
        model.addAttribute("generatedContent", generatedContent); // Generierte Ausgabe
        model.addAttribute("eid", eid);
        model.addAttribute("cid", cid);
        model.addAttribute("id", id);
        model.addAttribute("amount", amount != null ? amount : "");
        model.addAttribute("track_id", track_id != null ? track_id : ""); // track_id hinzufügen
        model.addAttribute("description", description);
        model.addAttribute("selectedTemplate", selectedTemplate);

        return "templateContent";
    }


    // Template-spezifischen Inhalt in einem Iframe laden
    @PostMapping("/loadContent")
    public String loadContent(@RequestParam Long id,
                              @RequestParam String saleType,
                              @RequestParam(required = false) String eid,
                              @RequestParam(required = false) String cid,
                              Model model) {
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return "redirect:/";
        }

        String content = switch (saleType) {
            case "sale" -> template.getSaleContent();
            case "lead" -> template.getLeadContent();
            case "install" -> template.getInstallContent();
            default -> "";
        };

        model.addAttribute("content", content);
        model.addAttribute("eid", eid);
        model.addAttribute("cid", cid);
        model.addAttribute("amount", template.getAmountPlaceholder() != null ? template.getAmountPlaceholder() : "");
        model.addAttribute("id", template.getIdPlaceholder() != null ? template.getIdPlaceholder() : "");
        model.addAttribute("selectedTemplate", template.getName());
        model.addAttribute("description", template.getDescription());

        return "templateContent";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        String uploadDirectory = "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/images/";
        try {
            Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            model.addAttribute("message", "Bild erfolgreich hochgeladen: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Fehler beim Hochladen des Bildes.");
        }
        return "redirect:/template";  // Leitet zurück zum Formular
    }

    // Login-Formular anzeigen
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Login-Daten verarbeiten (nur Beispiel-Logik)
    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password) {
        System.out.println("Attempting login with username: " + username + " and password: " + password);
        return "redirect:/";
    }
}
