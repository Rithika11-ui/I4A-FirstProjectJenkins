package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.model.ProfileType;
import com.example.demo.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    // Create - auto generates uuid and registrationNumber
    public Profile createProfile(Profile profile) {
        // Auto-generate UUID if not provided
        if (profile.getUuid() == null || profile.getUuid().isBlank()) {
            profile.setUuid(UUID.randomUUID().toString());
        }

        // Auto-generate registration number if not provided
        if (profile.getRegistrationNumber() == null || profile.getRegistrationNumber().isBlank()) {
            profile.setRegistrationNumber(generateRegistrationNumber(profile.getType()));
        }

        // Set default issue date
        if (profile.getIssueDate() == null) {
            profile.setIssueDate(LocalDate.now());
        }

        return profileRepository.save(profile);
    }

    // Read all
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    // Read by ID
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    // Read by registration number
    public Optional<Profile> getProfileByRegistrationNumber(String registrationNumber) {
        return profileRepository.findByRegistrationNumber(registrationNumber);
    }

    // Read by type
    public List<Profile> getProfilesByType(ProfileType profileType) {
        return profileRepository.findByType(profileType);
    }

    // Update
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Profile existing = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        existing.setFullName(updatedProfile.getFullName());
        existing.setEmail(updatedProfile.getEmail());
        existing.setPhone(updatedProfile.getPhone());
        existing.setDepartment(updatedProfile.getDepartment());
        existing.setTitle(updatedProfile.getTitle());
        existing.setType(updatedProfile.getType());
        existing.setBarcodeType(updatedProfile.getBarcodeType());
        return profileRepository.save(existing);
    }

    // Delete
    public void deleteProfile(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new RuntimeException("Profile not found with id: " + id);
        }
        profileRepository.deleteById(id);
    }

    // Batch create
    @org.springframework.transaction.annotation.Transactional
    public List<Profile> createProfilesBatch(List<Profile> profiles) {
        if (profiles == null) return List.of();
        return profiles.stream()
                .map(this::createProfile)
                .toList();
    }

    // Check existence
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return profileRepository.existsByRegistrationNumber(registrationNumber);
    }

    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }

    // Generate registration number: YEAR-TYPE-### format
    private String generateRegistrationNumber(ProfileType type) {
        int year = LocalDate.now().getYear();
        String typeCode = switch (type) {
            case STUDENT  -> "STU";
            case EMPLOYEE -> "EMP";
            case USER     -> "USR";
        };
        long count = profileRepository.countByType(type) + 1;
        return String.format("%d-%s-%03d", year, typeCode, count);
    }
}