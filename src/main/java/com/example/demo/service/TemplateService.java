package com.example.demo.service;

import com.example.demo.model.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    // Create
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    // Read all
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    // Read by ID
    public Optional<Template> getTemplateById(Long id) {
        return templateRepository.findById(id);
    }

    // Read by name
    public Optional<Template> getTemplateByName(String name) {
        return templateRepository.findByName(name);
    }

    // Update
    public Template updateTemplate(Long id, Template updatedTemplate) {
        Template existing = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + id));
        existing.setName(updatedTemplate.getName());
        existing.setCode(updatedTemplate.getCode());
        existing.setOrganizationName(updatedTemplate.getOrganizationName());
        existing.setLayout(updatedTemplate.getLayout());
        existing.setPrimaryColor(updatedTemplate.getPrimaryColor());
        existing.setSecondaryColor(updatedTemplate.getSecondaryColor());
        existing.setTextColor(updatedTemplate.getTextColor());
        existing.setTagline(updatedTemplate.getTagline());
        return templateRepository.save(existing);
    }

    // Delete
    public void deleteTemplate(Long id) {
        if (!templateRepository.existsById(id)) {
            throw new RuntimeException("Template not found with id: " + id);
        }
        templateRepository.deleteById(id);
    }

    // Check existence
    public boolean existsByName(String name) {
        return templateRepository.existsByName(name);
    }
}