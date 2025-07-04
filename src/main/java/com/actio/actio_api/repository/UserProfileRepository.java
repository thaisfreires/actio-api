package com.actio.actio_api.repository;

import com.actio.actio_api.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserProfile} entities.
 *
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes additional query methods to support user authentication and validation workflows,
 * such as checking for email and NIF uniqueness.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findByEmail(String name);

    boolean existsUserProfileByEmail(String email);

    boolean existsUserProfileByNif(String nif);
}
