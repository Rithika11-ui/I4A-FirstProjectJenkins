package com.example.demo.controller;

import com.example.demo.model.Template;
import com.example.demo.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    // Create
    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        Template created = templateService.createTemplate(template);
        return ResponseEntity.ok(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplateById(@PathVariable Long id) {
        return templateService.getTemplateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Read by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Template> getTemplateByName(@PathVariable String name) {
        return templateService.getTemplateByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Template> updateTemplate(@PathVariable Long id,
                                                    @RequestBody Template template) {
        Template updated = templateService.updateTemplate(id, template);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        java.util.Map<String, String> response = new java.util.HashMap<>();
        response.put("message", "Template with ID " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}