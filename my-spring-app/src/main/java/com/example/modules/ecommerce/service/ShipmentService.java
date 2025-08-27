package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.repository.ShipmentRepository;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.repository.OrderRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ShipmentService extends BaseService<Shipment> {

    protected final ShipmentRepository shipmentRepository;
    
    protected final OrderRepository orderRepository;
    

    public ShipmentService(ShipmentRepository repository, OrderRepository orderRepository)
    {
        super(repository);
        this.shipmentRepository = repository;
        
        this.orderRepository = orderRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Shipment update(Long id, Shipment shipmentRequest) {
        Shipment existing = shipmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shipment not found"));

    // Copier les champs simples
        existing.setShipmentDate(shipmentRequest.getShipmentDate());
        existing.setCarrier(shipmentRequest.getCarrier());
        existing.setTrackingNumber(shipmentRequest.getTrackingNumber());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Shipment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Shipment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Shipment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Shipment> saveAll(List<Shipment> shipmentList) {
        return super.saveAll(shipmentList);
    }

}