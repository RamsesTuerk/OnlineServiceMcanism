package com.example.webservicepostbackbuilder.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String saleContent;
    private String leadContent;
    private String installContent;
    private String amountPlaceholder;
    private String idPlaceholder;
    private String description;

    public Template() {}

    public Template(String name, String saleContent, String leadContent, String installContent, String amountPlaceholder, String idPlaceholder) {
        this.name = name;
        this.saleContent = saleContent;
        this.leadContent = leadContent;
        this.installContent = installContent;
        this.amountPlaceholder = amountPlaceholder;
        this.idPlaceholder = idPlaceholder;
    }

    // Getters and setters
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
