package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.repository.ShipmentRepository;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ShipmentService extends BaseService<Shipment> {

    protected final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentService(ShipmentRepository repository,OrderRepository orderRepository)
    {
        super(repository);
        this.shipmentRepository = repository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {


    

    
        if (shipment.getOrder() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            shipment.setOrder(
            orderRepository.findById(shipment.getOrder().getId())
            .orElseThrow(() -> new RuntimeException("order not found"))
            );
        
        shipment.getOrder().setShipment(shipment);
        }

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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (shipmentRequest.getOrder() != null
        && shipmentRequest.getOrder().getId() != null) {

        Order order = orderRepository.findById(
        shipmentRequest.getOrder().getId()
        ).orElseThrow(() -> new RuntimeException("Order not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setOrder(order);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            order.setShipment(existing);
        
        }

    


        return shipmentRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Shipment> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Shipment entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    
        if (entity.getOrder() != null) {
        // Dissocier côté inverse automatiquement
        entity.getOrder().setShipment(null);
        // Dissocier côté direct
        entity.setOrder(null);
        }
    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}