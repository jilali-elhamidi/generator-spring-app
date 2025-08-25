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

    public ShipmentService(ShipmentRepository repository, OrderRepository orderRepository)
    {
        super(repository);
        this.shipmentRepository = repository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (shipment.getOrder() != null) {
            if (shipment.getOrder().getId() != null) {
                Order existingOrder = orderRepository.findById(shipment.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id "
                        + shipment.getOrder().getId()));
                shipment.setOrder(existingOrder);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Order newOrder = orderRepository.save(shipment.getOrder());
                shipment.setOrder(newOrder);
            }

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

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (shipmentRequest.getOrder() != null &&shipmentRequest.getOrder().getId() != null) {

        Order order = orderRepository.findById(shipmentRequest.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existing.setOrder(order);
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
            entity.getOrder().setShipment(null);
            entity.setOrder(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Shipment> saveAll(List<Shipment> shipmentList) {

        return shipmentRepository.saveAll(shipmentList);
    }

}