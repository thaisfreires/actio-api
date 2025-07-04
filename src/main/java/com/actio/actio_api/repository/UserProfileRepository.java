package com.actio.actio_api.repository;

import com.actio.actio_api.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByEmail(String name);

    boolean existsUserProfileByEmail(String email);

    boolean existsUserProfileByNif(String nif);
}
