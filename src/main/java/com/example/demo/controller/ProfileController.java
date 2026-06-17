package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ProfileService;
import com.example.demo.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final PdfService pdfService;

    // Create
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile created = profileService.createProfile(profile);
        return ResponseEntity.ok(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Read by registration number
    @GetMapping("/registration/{regNumber}")
    public ResponseEntity<Profile> getByRegistrationNumber(@PathVariable String regNumber) {
        return profileService.getProfileByRegistrationNumber(regNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Read by type
    @GetMapping("/type/{profileType}")
    public ResponseEntity<List<Profile>> getByType(@PathVariable ProfileType profileType) {
        return ResponseEntity.ok(profileService.getProfilesByType(profileType));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id,
                                                  @RequestBody Profile profile) {
        Profile updated = profileService.updateProfile(id, profile);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        java.util.Map<String, String> response = new java.util.HashMap<>();
        response.put("message", "Profile with ID " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Get live preview
    @PostMapping("/preview")
    public ResponseEntity<String> getLivePreview(@RequestBody Profile profile) {
        if (profile.getFullName() == null || profile.getFullName().isBlank()) {
            profile.setFullName("John Doe");
        }
        if (profile.getType() == null) {
            profile.setType(ProfileType.STUDENT);
        }
        if (profile.getRegistrationNumber() == null || profile.getRegistrationNumber().isBlank()) {
            profile.setRegistrationNumber("2026-PREVIEW-000");
        }
        String html = pdfService.renderHtml(profile);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    // Get live preview via query parameters (for easy browser viewing)
    @GetMapping("/preview")
    public ResponseEntity<String> getLivePreviewGet(
            @RequestParam(required = false, defaultValue = "John Doe") String fullName,
            @RequestParam(required = false, defaultValue = "STUDENT") ProfileType type,
            @RequestParam(required = false, defaultValue = "2026-PREVIEW-001") String regNum,
            @RequestParam(required = false, defaultValue = "Engineering") String department,
            @RequestParam(required = false, defaultValue = "john.doe@example.com") String email,
            @RequestParam(required = false, defaultValue = "Staff") String title,
            @RequestParam(required = false, defaultValue = "O+") String bloodGroup) {
        
        Profile profile = ProfileBuilder.builder()
                .withDefaultValues(type)
                .fullName(fullName)
                .registrationNumber(regNum)
                .department(department)
                .email(email)
                .title(title)
                .build();
        profile.setBloodGroup(bloodGroup);

        String html = pdfService.renderHtml(profile);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    // Get preview html by ID
    @GetMapping("/{id}/preview")
    public ResponseEntity<String> getPreviewHtml(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        String html = pdfService.renderHtml(profile);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    // Get PDF by ID
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        byte[] pdfBytes = pdfService.generatePdf(profile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=idcard_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    // Batch create
    @PostMapping("/batch")
    public ResponseEntity<List<Profile>> createProfilesBatch(@RequestBody List<Profile> profiles) {
        List<Profile> created = profileService.createProfilesBatch(profiles);
        return ResponseEntity.ok(created);
    }

    // Batch PDF export
    @PostMapping("/batch/pdf")
    public ResponseEntity<byte[]> getBatchPdf(@RequestBody List<Long> ids) {
        List<Profile> profiles = profileService.getAllProfiles().stream()
                .filter(p -> ids.contains(p.getId()))
                .toList();
        if (profiles.isEmpty()) {
            throw new RuntimeException("No valid profiles found for the given IDs");
        }
        byte[] pdfBytes = pdfService.generateBatchPdf(profiles);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=idcards_batch.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}