package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final ProfileRepository profileRepository;

    /**
     * Upload photo for a profile.
     * POST /api/photos/upload/{profileId}
     */
    @PostMapping("/upload/{profileId}")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found: " + profileId));

        try {
            // Delete old photo if exists
            if (profile.getPhotoFileName() != null) {
                photoService.deletePhoto(profile.getPhotoFileName());
            }

            // Save new photo
            String filename = photoService.savePhoto(file);
            profile.setPhotoFileName(filename);
            profile.setPhotoContentType(file.getContentType());
            profileRepository.save(profile);

            return ResponseEntity.ok("Photo uploaded successfully: " + filename);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    /**
     * Get photo for a profile.
     * GET /api/photos/{profileId}
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found: " + profileId));

        if (!profile.hasPhoto()) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] imageBytes = photoService.readPhoto(profile.getPhotoFileName());
            MediaType mediaType = profile.getPhotoContentType() != null &&
                    profile.getPhotoContentType().contains("png")
                    ? MediaType.IMAGE_PNG
                    : MediaType.IMAGE_JPEG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Delete photo for a profile.
     * DELETE /api/photos/{profileId}
     */
    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deletePhoto(@PathVariable Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found: " + profileId));

        if (profile.hasPhoto()) {
            photoService.deletePhoto(profile.getPhotoFileName());
            profile.setPhotoFileName(null);
            profile.setPhotoContentType(null);
            profileRepository.save(profile);
        }

        return ResponseEntity.ok("Photo deleted successfully");
    }
}