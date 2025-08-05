package com.example.modules.ecommerce.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.ecommerce.model.Shipment;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends BaseRepository<Shipment, Long> {
}
