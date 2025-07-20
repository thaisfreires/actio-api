package com.actio.actio_api.service;

import com.actio.actio_api.model.MovementType;
import com.actio.actio_api.repository.MovementTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for operations related to MovementType entities.
 *
 * This service uses MovementTypeRepository to retrieve movement type data from the database.
 */
@Service
@RequiredArgsConstructor
public class MovementTypeService {

    private final MovementTypeRepository  movementTypeRepository;

    /**
     * Retrieves a MovementType by its unique identifier.
     *
     * This method returns a reference to the MovementType entity with the specified ID.
     * Note: The entity is loaded lazily. If it does not exist when accessed,
     * an exception may be thrown at runtime.
     *
     * @param id the unique identifier of the MovementType
     * @return a reference to the MovementType with the specified ID
     */
    public MovementType getById(Integer id){
        return movementTypeRepository.getReferenceById(id);
    }
}
