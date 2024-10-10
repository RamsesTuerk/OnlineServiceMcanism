package com.example.webservicepostbackbuilder.repository;

public class Template {

    private String key;

    private String name;
    private String saleContent;
    private String leadContent;
    private String installContent;

    public Template() {}

    public Template(String name, String saleContent, String leadContent, String installContent) {
        this.name = name;
        this.saleContent = saleContent;
        this.leadContent = leadContent;
        this.installContent = installContent;
        this.key = name;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
