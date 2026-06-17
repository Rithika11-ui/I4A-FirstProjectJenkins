package com.example.demo.model;

import java.time.LocalDate;

/**
 * Helper builder to build default profiles or customize them.
 */
public class ProfileBuilder {
    private Profile profile;

    public ProfileBuilder() {
        this.profile = new Profile();
    }

    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }

    public ProfileBuilder withDefaultValues(ProfileType type) {
        this.profile.setType(type);
        this.profile.setFullName("John Doe");
        this.profile.setDepartment("General");
        this.profile.setTitle(type == ProfileType.STUDENT ? "Student" : "Staff");
        this.profile.setEmail("john.doe@example.com");
        this.profile.setPhone("+123456789");
        this.profile.setBloodGroup("O+");
        this.profile.setDateOfBirth(LocalDate.of(2000, 1, 1));
        this.profile.setIssueDate(LocalDate.now());
        this.profile.setExpiryDate(LocalDate.now().plusYears(4));
        this.profile.setBarcodeType(BarcodeType.CODE_128);
        return this;
    }

    public ProfileBuilder id(Long id) {
        this.profile.setId(id);
        return this;
    }

    public ProfileBuilder fullName(String fullName) {
        this.profile.setFullName(fullName);
        return this;
    }

    public ProfileBuilder department(String department) {
        this.profile.setDepartment(department);
        return this;
    }

    public ProfileBuilder title(String title) {
        this.profile.setTitle(title);
        return this;
    }

    public ProfileBuilder email(String email) {
        this.profile.setEmail(email);
        return this;
    }

    public ProfileBuilder phone(String phone) {
        this.profile.setPhone(phone);
        return this;
    }

    public ProfileBuilder type(ProfileType type) {
        this.profile.setType(type);
        return this;
    }

    public ProfileBuilder template(Template template) {
        this.profile.setTemplate(template);
        return this;
    }

    public ProfileBuilder barcodeType(BarcodeType barcodeType) {
        this.profile.setBarcodeType(barcodeType);
        return this;
    }

    public ProfileBuilder registrationNumber(String registrationNumber) {
        this.profile.setRegistrationNumber(registrationNumber);
        return this;
    }

    public ProfileBuilder uuid(String uuid) {
        this.profile.setUuid(uuid);
        return this;
    }

    public Profile build() {
        return this.profile;
    }
}
