package com.example.webservicepostbackbuilder.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Repräsentiert ein Template-Objekt in der Datenbank.
 */
@Entity
public class Template {

    // Eindeutige ID des Templates (Primärschlüssel).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name des Templates.
    private String name;

    // Inhalt für den "Sale"-Typ.
    private String saleContent;

    // Inhalt für den "Lead"-Typ.
    private String leadContent;

    // Inhalt für den "Install"-Typ.
    private String installContent;

    // Platzhalter für den Betrag im Template.
    private String amountPlaceholder;

    // Platzhalter für die ID im Template.
    private String idPlaceholder;

    // Beschreibung des Templates.
    private String description;

    // Standard-Konstruktor (erforderlich für JPA).
    public Template() {}

    /**
     * Konstruktor zum Erstellen eines Templates mit den angegebenen Werten.
     *
     * @param name Name des Templates.
     * @param saleContent Inhalt für den "Sale"-Typ.
     * @param leadContent Inhalt für den "Lead"-Typ.
     * @param installContent Inhalt für den "Install"-Typ.
     * @param amountPlaceholder Platzhalter für den Betrag.
     * @param idPlaceholder Platzhalter für die ID.
     */
    public Template(String name, String saleContent, String leadContent, String installContent, String amountPlaceholder, String idPlaceholder) {
        this.name = name;
        this.saleContent = saleContent;
        this.leadContent = leadContent;
        this.installContent = installContent;
        this.amountPlaceholder = amountPlaceholder;
        this.idPlaceholder = idPlaceholder;
    }

    // Getter und Setter für die Eigenschaften.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaleContent() {
        return saleContent;
    }

    public void setSaleContent(String saleContent) {
        this.saleContent = saleContent;
    }

    public String getLeadContent() {
        return leadContent;
    }

    public void setLeadContent(String leadContent) {
        this.leadContent = leadContent;
    }

    public String getInstallContent() {
        return installContent;
    }

    public void setInstallContent(String installContent) {
        this.installContent = installContent;
    }

    public String getAmountPlaceholder() {
        return amountPlaceholder;
    }

    public void setAmountPlaceholder(String amountPlaceholder) {
        this.amountPlaceholder = amountPlaceholder;
    }

    public String getIdPlaceholder() {
        return idPlaceholder;
    }

    public void setIdPlaceholder(String idPlaceholder) {
        this.idPlaceholder = idPlaceholder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
