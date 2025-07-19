package com.actio.actio_api.service;

import com.actio.actio_api.model.TransactionType;
import com.actio.actio_api.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing operations related to TransactionType entities.
 *
 * This service provides access to transaction types stored in the database,
 * using the TransactionTypeRepository.
 */
@Service
@RequiredArgsConstructor
public class TransactionTypeService {

    private final TransactionTypeRepository    transactionTypeRepository;

    /**
     * Retrieves a TransactionType by its unique identifier.
     *
     * This method returns a reference to the TransactionType entity with the given ID.
     * Note: This does not immediately access the database; the actual entity is loaded lazily.
     *
     * @param id the unique identifier of the TransactionType
     * @return a reference to the TransactionType with the specified ID
     */
    public TransactionType findById(Integer id){
        return transactionTypeRepository.getReferenceById(id);
    }


}
