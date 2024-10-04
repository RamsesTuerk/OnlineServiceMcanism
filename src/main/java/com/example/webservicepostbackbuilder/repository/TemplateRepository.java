package com.example.webservicepostbackbuilder.repository;

import java.util.List;

public interface TemplateRepository {

    void save(Template template);

    Template findByName(String name);

    void deleteByName(String name);

    List<Template> findAll();
}
