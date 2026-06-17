package com.example.demo;

import com.example.demo.controller.ProfileController;
import com.example.demo.model.*;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfileControllerTest {

    @Autowired
    private ProfileController profileController;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TemplateRepository templateRepository;

    private Template testTemplate;

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
        templateRepository.deleteAll();

        testTemplate = Template.builder()
                .code("TEST_TEMP")
                .name("Test Template")
                .organizationName("Test Corp")
                .layout("VERTICAL")
                .primaryColor("#000000")
                .secondaryColor("#ffffff")
                .textColor("#000000")
                .tagline("Test Tagline")
                .build();
        testTemplate = templateRepository.save(testTemplate);
    }

    @Test
    void testProfileCRUD() {
        // Create Profile
        Profile profile = ProfileBuilder.builder()
                .withDefaultValues(ProfileType.STUDENT)
                .fullName("Alice Wonderland")
                .email("alice@example.com")
                .template(testTemplate)
                .build();

        ResponseEntity<Profile> createResponse = profileController.createProfile(profile);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Profile createdProfile = createResponse.getBody();
        assertNotNull(createdProfile);
        assertEquals("Alice Wonderland", createdProfile.getFullName());
        assertEquals("alice@example.com", createdProfile.getEmail());
        assertNotNull(createdProfile.getRegistrationNumber());
        Long id = createdProfile.getId();

        // Read all
        ResponseEntity<List<Profile>> listResponse = profileController.getAllProfiles();
        assertEquals(HttpStatus.OK, listResponse.getStatusCode());
        List<Profile> profiles = listResponse.getBody();
        assertNotNull(profiles);
        assertEquals(1, profiles.size());
        assertEquals("Alice Wonderland", profiles.get(0).getFullName());

        // Read by ID
        ResponseEntity<Profile> getResponse = profileController.getProfileById(id);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Alice Wonderland", getResponse.getBody().getFullName());

        // Update
        createdProfile.setFullName("Alice Smith");
        ResponseEntity<Profile> updateResponse = profileController.updateProfile(id, createdProfile);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Alice Smith", updateResponse.getBody().getFullName());

        // Delete (should return confirmation message)
        ResponseEntity<Map<String, String>> deleteResponse = profileController.deleteProfile(id);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        Map<String, String> deleteBody = deleteResponse.getBody();
        assertNotNull(deleteBody);
        assertTrue(deleteBody.get("message").contains("deleted successfully"));

        // Read deleted should return 404
        ResponseEntity<Profile> getDeletedResponse = profileController.getProfileById(id);
        assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());
    }

    @Test
    void testLivePreview() {
        Profile profile = ProfileBuilder.builder()
                .withDefaultValues(ProfileType.STUDENT)
                .fullName("Preview Bob")
                .registrationNumber("2026-PREVIEW-001")
                .build();

        ResponseEntity<String> previewResponse = profileController.getLivePreview(profile);
        assertEquals(HttpStatus.OK, previewResponse.getStatusCode());
        String html = previewResponse.getBody();
        assertNotNull(html);
        assertTrue(html.contains("Preview Bob"));
        assertTrue(html.contains("2026-PREVIEW-001"));
    }

    @Test
    void testPdfExport() {
        Profile profile = ProfileBuilder.builder()
                .withDefaultValues(ProfileType.EMPLOYEE)
                .fullName("Charlie Employee")
                .email("charlie@example.com")
                .template(testTemplate)
                .build();

        profile = profileController.createProfile(profile).getBody();
        assertNotNull(profile);
        Long id = profile.getId();

        ResponseEntity<byte[]> pdfResponse = profileController.getPdf(id);
        assertEquals(HttpStatus.OK, pdfResponse.getStatusCode());
        byte[] pdfBytes = pdfResponse.getBody();
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        
        String contentDisp = pdfResponse.getHeaders().getFirst("Content-Disposition");
        assertNotNull(contentDisp);
        assertTrue(contentDisp.contains("attachment; filename=idcard_"));
    }

    @Test
    void testBatchPdfExport() {
        Profile p1 = ProfileBuilder.builder()
                .withDefaultValues(ProfileType.STUDENT)
                .fullName("Student One")
                .email("one@example.com")
                .template(testTemplate)
                .build();
        p1 = profileController.createProfile(p1).getBody();
        assertNotNull(p1);

        Profile p2 = ProfileBuilder.builder()
                .withDefaultValues(ProfileType.EMPLOYEE)
                .fullName("Employee Two")
                .email("two@example.com")
                .template(testTemplate)
                .build();
        p2 = profileController.createProfile(p2).getBody();
        assertNotNull(p2);

        List<Long> ids = List.of(p1.getId(), p2.getId());

        ResponseEntity<byte[]> batchPdfResponse = profileController.getBatchPdf(ids);
        assertEquals(HttpStatus.OK, batchPdfResponse.getStatusCode());
        byte[] pdfBytes = batchPdfResponse.getBody();
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        
        String contentDisp = batchPdfResponse.getHeaders().getFirst("Content-Disposition");
        assertNotNull(contentDisp);
        assertTrue(contentDisp.contains("attachment; filename=idcards_batch.pdf"));
    }
}
