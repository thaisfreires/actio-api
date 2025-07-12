package com.actio.actio_api.repository;

import com.actio.actio_api.model.ActioUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link ActioUser} entities.
 *
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * Includes additional query methods to support user authentication and validation workflows,
 * such as checking for email and NIF uniqueness.
 */
@Repository
public interface ActioUserRepository extends JpaRepository<ActioUser, Long> {


    Optional<ActioUser> findByEmail(String name);

    boolean existsActioUserByEmail(String email);

    boolean existsActioUserByNif(String nif);
}
