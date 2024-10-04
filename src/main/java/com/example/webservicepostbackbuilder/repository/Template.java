package com.example.webservicepostbackbuilder.repository;

public class Template {

    private String key;

    private String name;
    private String content;

    public Template() {}

    public Template(String name, String content) {
        this.name = name;
        this.content = content;
        this.key = name;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
