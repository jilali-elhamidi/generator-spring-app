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
public Shipment update(Long id, Shipment shipmentRequest) {
Shipment existing = shipmentRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Shipment not found"));

// Copier les champs simples

    existing.setShipmentDate(shipmentRequest.getShipmentDate());

    existing.setCarrier(shipmentRequest.getCarrier());

    existing.setTrackingNumber(shipmentRequest.getTrackingNumber());


// Relations ManyToOne : mise à jour conditionnelle

    


// Relations OneToMany : synchronisation sécurisée

    


return shipmentRepository.save(existing);
}
}
