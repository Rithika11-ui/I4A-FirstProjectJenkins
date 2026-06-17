package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ProfileService;
import com.example.demo.service.TemplateService;
import com.example.demo.service.PhotoService;
import com.example.demo.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ProfileService profileService;
    private final TemplateService templateService;
    private final PhotoService photoService;
    private final ProfileRepository profileRepository;

    // List all profiles
    @GetMapping({"/", "/profiles", "/index", "/index.html"})
    public String listProfiles(Model model) {
        model.addAttribute("profiles", profileService.getAllProfiles());
        return "profiles";
    }

    // New profile form
    @GetMapping("/profiles/new")
    public String newProfileForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("profileTypes", ProfileType.values());
        model.addAttribute("barcodeTypes", BarcodeType.values());
        return "profile-form";
    }

    // Edit profile form
    @GetMapping("/profiles/edit/{id}")
    public String editProfileForm(@PathVariable Long id, Model model) {
        Profile profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found: " + id));
        model.addAttribute("profile", profile);
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("profileTypes", ProfileType.values());
        model.addAttribute("barcodeTypes", BarcodeType.values());
        return "profile-form";
    }

    // Save profile (Create/Update)
    @PostMapping("/profiles/save")
    public String saveProfile(@ModelAttribute Profile profile,
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        Profile saved;
        if (profile.getId() != null) {
            saved = profileService.updateProfile(profile.getId(), profile);
        } else {
            saved = profileService.createProfile(profile);
        }

        if (file != null && !file.isEmpty()) {
            try {
                if (saved.getPhotoFileName() != null) {
                    photoService.deletePhoto(saved.getPhotoFileName());
                }
                String filename = photoService.savePhoto(file);
                saved.setPhotoFileName(filename);
                saved.setPhotoContentType(file.getContentType());
                profileRepository.save(saved);
            } catch (Exception e) {
                System.err.println("Could not save profile photo: " + e.getMessage());
            }
        }
        return "redirect:/profiles";
    }

    // Delete profile
    @GetMapping("/profiles/delete/{id}")
    public String deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
        } catch (Exception e) {
            System.err.println("Could not delete profile: " + e.getMessage());
        }
        return "redirect:/profiles";
    }

    // List templates
    @GetMapping("/templates")
    public String listTemplates(Model model) {
        model.addAttribute("templates", templateService.getAllTemplates());
        model.addAttribute("template", new Template());
        return "templates-list";
    }

    // Save template
    @PostMapping("/templates/save")
    public String saveTemplate(@ModelAttribute Template template) {
        templateService.createTemplate(template);
        return "redirect:/templates";
    }

    // Delete template
    @GetMapping("/templates/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        try {
            templateService.deleteTemplate(id);
        } catch (Exception e) {
            System.err.println("Could not delete template: " + e.getMessage());
        }
        return "redirect:/templates";
    }

    // Batch Export page
    @GetMapping("/batch-export")
    public String batchExport(Model model) {
        model.addAttribute("profiles", profileService.getAllProfiles());
        return "batch-export";
    }
}
