package com.example.demo.repository;

import com.example.demo.model.Profile;
import com.example.demo.model.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByRegistrationNumber(String registrationNumber);

    Optional<Profile> findByEmail(String email);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByEmail(String email);

    List<Profile> findByType(ProfileType type);

    List<Profile> findByDepartment(String department);

    @Query("SELECT p FROM Profile p WHERE " +
           "LOWER(p.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.email)    LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.registrationNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Profile> search(@Param("keyword") String keyword);

    long countByType(ProfileType type);

    List<Profile> findAllByIdIn(List<Long> ids);
}