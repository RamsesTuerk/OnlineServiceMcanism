package com.example.webservicepostbackbuilder.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTemplateRepository implements TemplateRepository {
    private final Map<String, Template> templateStore = new ConcurrentHashMap<>();
    @Override
    public void save(Template template) {
        templateStore.put(template.getName(), template);
    }
    @Override
    public Template findByName(String name) {
        return templateStore.get(name);
    }
    @Override
    public void deleteByName(String name) {
        templateStore.remove(name);
    }
    @Override
    public List<Template> findAll() {
        return new ArrayList<>(templateStore.values());
    }
}
