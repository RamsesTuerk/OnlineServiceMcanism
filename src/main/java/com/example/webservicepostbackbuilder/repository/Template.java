package com.example.webservicepostbackbuilder.repository;

import jakarta.persistence.*;



/**
 * Repräsentiert ein Template-Objekt in der Datenbank.
 * Diese Klasse wird verwendet, um Template-Daten zu speichern und abzurufen,
 * die später in der Anwendung verwendet werden, um dynamische Inhalte zu generieren.
 */
@Entity
public class Template {

    // Eindeutige ID des Templates (Primärschlüssel).
    // Die ID wird automatisch von der Datenbank generiert.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Der Name des Templates. Dies könnte z.B. "Sale", "Lead" oder "Install" sein.
    private String name;

    // Der Inhalt des Templates für den "Sale"-Typ.
    // Dies ist der Text, der angezeigt wird, wenn das Template für einen Verkauf verwendet wird.
    private String saleContent;

    // Der Inhalt des Templates für den "Lead"-Typ.
    // Dies ist der Text, der angezeigt wird, wenn das Template für einen Lead verwendet wird.
    private String leadContent;

    // Der Inhalt des Templates für den "Install"-Typ.
    // Dies ist der Text, der angezeigt wird, wenn das Template für eine Installation verwendet wird.
    private String installContent;

    // Platzhalter für den Betrag im Template.
    // Dieser Platzhalter wird durch einen tatsächlichen Betrag ersetzt, wenn das Template gerendert wird.
    private String amountPlaceholder;

    // Platzhalter für die ID im Template.
    // Dieser Platzhalter wird durch eine tatsächliche ID ersetzt, wenn das Template gerendert wird.
    private String idPlaceholder;

    // Eine Beschreibung des Templates.
    // Dies könnte zusätzliche Informationen zum Template oder Hinweise zur Verwendung enthalten.
    private String description;

    @Column(nullable = true)
    private int orderId;

    // Standard-Konstruktor (erforderlich für JPA, um Instanzen der Klasse zu erstellen).
    // Dieser Konstruktor wird von JPA verwendet, um neue Objekte aus der Datenbank zu laden.
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
     * @param orderId Platzhalter für die ID zum ordnen .
     */
    public Template(String name, String saleContent, String leadContent, String installContent, String amountPlaceholder, String idPlaceholder, int orderId) {
        this.name = name;
        this.saleContent = saleContent;
        this.leadContent = leadContent;
        this.installContent = installContent;
        this.amountPlaceholder = amountPlaceholder;
        this.idPlaceholder = idPlaceholder;
        this.orderId = orderId;
    }

    // Getter und Setter für die Eigenschaften.

    // Gibt die ID des Templates zurück.
    public Long getId() {
        return id;
    }

    // Setzt die ID des Templates.
    public void setId(Long id) {
        this.id = id;
    }

    // Gibt den Namen des Templates zurück.
    public String getName() {
        return name;
    }

    // Setzt den Namen des Templates.
    public void setName(String name) {
        this.name = name;
    }

    // Gibt den Inhalt des "Sale"-Templates zurück.
    public String getSaleContent() {
        return saleContent;
    }

    // Setzt den Inhalt des "Sale"-Templates.
    public void setSaleContent(String saleContent) {
        this.saleContent = saleContent;
    }

    // Gibt den Inhalt des "Lead"-Templates zurück.
    public String getLeadContent() {
        return leadContent;
    }

    // Setzt den Inhalt des "Lead"-Templates.
    public void setLeadContent(String leadContent) {
        this.leadContent = leadContent;
    }

    // Gibt den Inhalt des "Install"-Templates zurück.
    public String getInstallContent() {
        return installContent;
    }

    // Setzt den Inhalt des "Install"-Templates.
    public void setInstallContent(String installContent) {
        this.installContent = installContent;
    }

    // Gibt den Platzhalter für den Betrag zurück.
    public String getAmountPlaceholder() {
        return amountPlaceholder;
    }

    // Setzt den Platzhalter für den Betrag.
    public void setAmountPlaceholder(String amountPlaceholder) {
        this.amountPlaceholder = amountPlaceholder;
    }

    // Gibt den Platzhalter für die ID zurück.
    public String getIdPlaceholder() {
        return idPlaceholder;
    }

    // Setzt den Platzhalter für die ID.
    public void setIdPlaceholder(String idPlaceholder) {
        this.idPlaceholder = idPlaceholder;
    }

    // Gibt die Beschreibung des Templates zurück.
    public String getDescription() {
        return description;
    }

    // Setzt die Beschreibung des Templates.
    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderid() {
        return orderId;
    }

    public void setOrderid(int orderid) {
        this.orderId = orderid;
    }
}
