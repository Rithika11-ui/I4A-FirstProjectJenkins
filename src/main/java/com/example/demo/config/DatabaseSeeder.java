package com.example.demo.config;

import com.example.demo.model.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    @Override
    public void run(String... args) throws Exception {
        if (!templateRepository.existsByCode("DEFAULT_VERTICAL")) {
            // Seed default vertical template
            Template vertical = Template.builder()
                    .code("DEFAULT_VERTICAL")
                    .name("Default Vertical Theme")
                    .organizationName("UNIVERSITY OF EXCELLENCE")
                    .layout("VERTICAL")
                    .primaryColor("#1d4ed8") // Blue
                    .secondaryColor("#1e3a8a") // Dark blue
                    .textColor("#ffffff")
                    .tagline("KNOWLEDGE IS POWER")
                    .build();
            templateRepository.save(vertical);
            System.out.println("DatabaseSeeder: Default vertical template seeded successfully.");
        }

        if (!templateRepository.existsByCode("DEFAULT_HORIZONTAL")) {
            // Seed default horizontal template
            Template horizontal = Template.builder()
                    .code("DEFAULT_HORIZONTAL")
                    .name("Default Horizontal Theme")
                    .organizationName("TECH CORP SOLUTIONS")
                    .layout("HORIZONTAL")
                    .primaryColor("#059669") // Emerald green
                    .secondaryColor("#064e3b") // Dark green
                    .textColor("#ffffff")
                    .tagline("INNOVATION & TECHNOLOGY")
                    .build();
            templateRepository.save(horizontal);
            System.out.println("DatabaseSeeder: Default horizontal template seeded successfully.");
        }
    }}
