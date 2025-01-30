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

    // TemplateService wird hier durch Dependency Injection eingebunden.
    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/")
    public String landingPage(){
     return "landingPage";
    }

    @GetMapping("/advertiser")
    public String advertiserLandingPage(){
        return "advertiserLandingPage";
    }

    @GetMapping("/publisher")
    public String publisherLandingPage(){
        return "publisherLandingPage";
    }

    // Landingpage mit der Liste aller Templates und optionaler EID und CID
    @GetMapping("/scriptGenerator")
    public String index(@RequestParam(required = false) String eid,
                        @RequestParam(required = false) String cid,
                        Model model) {
        // Alle Templates werden aus der TemplateService geladen
        model.addAttribute("templates", templateService.getAllTemplates());
        // EID und CID werden optional als Parameter mitgegeben und ins Model gelegt
        model.addAttribute("eid", eid != null ? eid : "");
        model.addAttribute("cid", cid != null ? cid : "");
        return "scriptGenerator";  // Rückgabe der View "landingPage"
    }

    // Formular zur Erstellung oder Bearbeitung eines Templates
    @GetMapping("/template")
    public String showTemplateForm(Model model) {
        // Ein neues Template-Objekt wird ins Model eingefügt
        model.addAttribute("template", new Template());
        return "templateForm";  // Rückgabe der View für das Formular
    }

    // Template speichern (neu oder bearbeitet)
    @PostMapping("/template/save")
    public String saveTemplate(@ModelAttribute Template template) {
        // Das Template wird über die TemplateService gespeichert
        templateService.saveTemplate(template);
        return "redirect:/templates";  // Nach dem Speichern wird die Liste der Templates angezeigt
    }

    // Auflistung aller Templates
    @GetMapping("/templates")
    public String listTemplates(Model model) {
        // Alle Templates werden ins Model geladen
        model.addAttribute("templates", templateService.getAllTemplates());
        return "templateList";  // Rückgabe der View, die die Liste der Templates anzeigt
    }

    // Formular zum Bearbeiten eines bestehenden Templates anhand der ID
    @GetMapping("/template/edit/{id}")
    public String editTemplate(@PathVariable Long id, Model model) {
        // Das Template mit der angegebenen ID wird aus der TemplateService geladen
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return "redirect:/templates";  // Falls das Template nicht existiert, wird zur Template-Liste umgeleitet
        }
        model.addAttribute("template", template);  // Das Template wird ins Model eingefügt
        return "templateForm";  // Rückgabe der View für das Bearbeitungsformular
    }

    // Templates löschen anhand der ID
    @PostMapping("/template/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        // Das Template mit der angegebenen ID wird aus der TemplateService gelöscht
        templateService.deleteTemplate(id);
        return "redirect:/templates";  // Nach dem Löschen wird die Template-Liste angezeigt
    }

    // Dynamische Optionen basierend auf der Template-ID laden
    @GetMapping("/templates/{id}/options")
    public ResponseEntity<Map<String, Boolean>> getTemplateOptions(@PathVariable Long id) {
        // Das Template mit der angegebenen ID wird aus der TemplateService geladen
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Falls das Template nicht gefunden wird, gibt es eine 404-Antwort
        }

        // Rückgabe einer Map mit den Optionen des Templates (sale, lead, install)
        Map<String, Boolean> options = Map.of(
                "sale", template.getSaleContent() != null && !template.getSaleContent().isEmpty(),
                "lead", template.getLeadContent() != null && !template.getLeadContent().isEmpty(),
                "install", template.getInstallContent() != null && !template.getInstallContent().isEmpty()
        );

        return ResponseEntity.ok(options);  // Rückgabe der Optionen als JSON-Antwort
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
            @RequestParam(required = false) String shop_id,
            @RequestParam String description,
            @RequestParam String selectedTemplate,
            Model model) {

        // Vermeidung von Nullwerten und sicherstellen, dass Platzhalter korrekt ersetzt werden
        String generatedContent = content.replace("{eid}", eid)
                .replace("{track_id}", track_id != null ? track_id : "")
                .replace("{cid}", cid)
                .replace("{id}", id)
                .replace("{amount}", amount != null ? amount : "")
                .replace("{shop_id}", shop_id != null ? shop_id : "");

        // Daten ins Model legen, um sie an die View weiterzugeben
        model.addAttribute("content", content); // Originalinhalt bleibt erhalten
        model.addAttribute("generatedContent", generatedContent); // Generierte Ausgabe
        model.addAttribute("eid", eid);
        model.addAttribute("cid", cid);
        model.addAttribute("id", id);
        model.addAttribute("amount", amount != null ? amount : "");
        model.addAttribute("shop_id", shop_id != null ? shop_id : "");
        model.addAttribute("track_id", track_id != null ? track_id : ""); // track_id hinzufügen
        model.addAttribute("description", description);
        model.addAttribute("selectedTemplate", selectedTemplate);

        return "templateContent";  // Rückgabe der View, die den generierten Inhalt anzeigt
    }

    // Template-spezifischen Inhalt in einem Iframe laden
    @PostMapping("/loadContent")
    public String loadContent(@RequestParam Long id,
                              @RequestParam String saleType,
                              @RequestParam(required = false) String eid,
                              @RequestParam(required = false) String cid,
                              Model model) {
        // Das Template mit der angegebenen ID wird aus der TemplateService geladen
        Template template = templateService.getTemplateById(id);
        if (template == null) {
            return "redirect:/";  // Falls das Template nicht existiert, wird zur Landingpage umgeleitet
        }

        // Der Inhalt wird je nach Typ des Templates ausgewählt
        String content = switch (saleType) {
            case "sale" -> template.getSaleContent();
            case "lead" -> template.getLeadContent();
            case "install" -> template.getInstallContent();
            default -> "";
        };

        // Die relevanten Daten werden ins Model eingefügt
        model.addAttribute("content", content);
        model.addAttribute("eid", eid);
        model.addAttribute("cid", cid);
        model.addAttribute("amount", template.getAmountPlaceholder() != null ? template.getAmountPlaceholder() : "");
        model.addAttribute("id", template.getIdPlaceholder() != null ? template.getIdPlaceholder() : "");
        model.addAttribute("selectedTemplate", template.getName());
        model.addAttribute("description", template.getDescription());

        return "templateContent";  // Rückgabe der View, die den Inhalt im Iframe anzeigt
    }

    // Bild hochladen
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        String uploadDirectory = "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/images/";  // Zielverzeichnis für das Bild
        try {
            Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);  // Bild wird in das Verzeichnis kopiert
            model.addAttribute("message", "Bild erfolgreich hochgeladen: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Fehler beim Hochladen des Bildes.");
        }
        return "redirect:/template";  // Rückgabe zur Template-Seite nach dem Hochladen
    }

    // Login-Formular anzeigen
    @GetMapping("/login")
    public String login() {
        return "login";  // Rückgabe der View für das Login-Formular
    }

    // Login-Daten verarbeiten (nur Beispiel-Logik)
    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password) {
        // Die Benutzerdaten werden überprüft (hier als Beispiel ohne wirkliche Authentifizierung)
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "redirect:/";  // Falls Login erfolgreich, wird zur Landingpage weitergeleitet
        } else {
            return "redirect:/login";  // Ansonsten bleibt der Benutzer auf der Login-Seite
        }
    }
}
