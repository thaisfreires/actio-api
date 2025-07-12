package com.actio.actio_api.repository;

import com.actio.actio_api.model.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementTypeRepository extends JpaRepository<MovementType, Integer> {
}
