package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.repository.ShipmentRepository;


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ShipmentService extends BaseService<Shipment> {

protected final ShipmentRepository shipmentRepository;



public ShipmentService(
ShipmentRepository repository

) {
super(repository);
this.shipmentRepository = repository;

}

@Override
public Shipment save(Shipment shipment) {







return shipmentRepository.save(shipment);
}
}
