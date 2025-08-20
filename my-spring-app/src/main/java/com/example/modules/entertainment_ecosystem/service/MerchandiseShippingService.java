package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingMethodRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingStatusRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseShippingService extends BaseService<MerchandiseShipping> {

    protected final MerchandiseShippingRepository merchandiseshippingRepository;
    private final MerchandiseRepository merchandiseItemRepository;
    private final MerchandiseOrderRepository orderRepository;
    private final MerchandiseShippingMethodRepository shippingMethodRepository;
    private final MerchandiseShippingStatusRepository statusHistoryRepository;

    public MerchandiseShippingService(MerchandiseShippingRepository repository, MerchandiseRepository merchandiseItemRepository, MerchandiseOrderRepository orderRepository, MerchandiseShippingMethodRepository shippingMethodRepository, MerchandiseShippingStatusRepository statusHistoryRepository)
    {
        super(repository);
        this.merchandiseshippingRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.orderRepository = orderRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Override
    public MerchandiseShipping save(MerchandiseShipping merchandiseshipping) {
    // ---------- OneToMany ----------
        if (merchandiseshipping.getStatusHistory() != null) {
            List<MerchandiseShippingStatus> managedStatusHistory = new ArrayList<>();
            for (MerchandiseShippingStatus item : merchandiseshipping.getStatusHistory()) {
                if (item.getId() != null) {
                    MerchandiseShippingStatus existingItem = statusHistoryRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShippingStatus not found"));

                     existingItem.setShipment(merchandiseshipping);
                     managedStatusHistory.add(existingItem);
                } else {
                    item.setShipment(merchandiseshipping);
                    managedStatusHistory.add(item);
                }
            }
            merchandiseshipping.setStatusHistory(managedStatusHistory);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandiseshipping.getMerchandiseItem() != null &&
            merchandiseshipping.getMerchandiseItem().getId() != null) {

            Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                merchandiseshipping.getMerchandiseItem().getId()
            ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

            merchandiseshipping.setMerchandiseItem(existingMerchandiseItem);
        }
        
        if (merchandiseshipping.getShippingMethod() != null &&
            merchandiseshipping.getShippingMethod().getId() != null) {

            MerchandiseShippingMethod existingShippingMethod = shippingMethodRepository.findById(
                merchandiseshipping.getShippingMethod().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseShippingMethod not found"));

            merchandiseshipping.setShippingMethod(existingShippingMethod);
        }
        
    // ---------- OneToOne ----------
        if (merchandiseshipping.getOrder() != null) {
            
            
                // Vérifier si l'entité est déjà persistée
            merchandiseshipping.setOrder(
                orderRepository.findById(merchandiseshipping.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("order not found"))
            );
            
            merchandiseshipping.getOrder().setShippingDetails(merchandiseshipping);
        }
        

    return merchandiseshippingRepository.save(merchandiseshipping);
}


    public MerchandiseShipping update(Long id, MerchandiseShipping merchandiseshippingRequest) {
        MerchandiseShipping existing = merchandiseshippingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));

    // Copier les champs simples
        existing.setShippingDate(merchandiseshippingRequest.getShippingDate());
        existing.setCarrier(merchandiseshippingRequest.getCarrier());
        existing.setTrackingNumber(merchandiseshippingRequest.getTrackingNumber());

    // ---------- Relations ManyToOne ----------
        if (merchandiseshippingRequest.getMerchandiseItem() != null &&
            merchandiseshippingRequest.getMerchandiseItem().getId() != null) {

            Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
                merchandiseshippingRequest.getMerchandiseItem().getId()
            ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

            existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
            existing.setMerchandiseItem(null);
        }
        
        if (merchandiseshippingRequest.getShippingMethod() != null &&
            merchandiseshippingRequest.getShippingMethod().getId() != null) {

            MerchandiseShippingMethod existingShippingMethod = shippingMethodRepository.findById(
                merchandiseshippingRequest.getShippingMethod().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseShippingMethod not found"));

            existing.setShippingMethod(existingShippingMethod);
        } else {
            existing.setShippingMethod(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getStatusHistory().clear();

        if (merchandiseshippingRequest.getStatusHistory() != null) {
            for (var item : merchandiseshippingRequest.getStatusHistory()) {
                MerchandiseShippingStatus existingItem;
                if (item.getId() != null) {
                    existingItem = statusHistoryRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShippingStatus not found"));
                } else {
                existingItem = item;
                }

                existingItem.setShipment(existing);
                existing.getStatusHistory().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
            if (merchandiseshippingRequest.getOrder() != null &&
            merchandiseshippingRequest.getOrder().getId() != null) {

            MerchandiseOrder order = orderRepository.findById(
                merchandiseshippingRequest.getOrder().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));

            existing.setOrder(order);

            
            order.setShippingDetails(existing);
            
        }
        

    return merchandiseshippingRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseShipping> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseShipping entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getStatusHistory() != null) {
            for (var child : entity.getStatusHistory()) {
                
                child.setShipment(null); // retirer la référence inverse
                
            }
            entity.getStatusHistory().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getOrder() != null) {
            entity.getOrder().setShippingDetails(null);
            entity.setOrder(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getMerchandiseItem() != null) {
            entity.setMerchandiseItem(null);
        }
        
        if (entity.getShippingMethod() != null) {
            entity.setShippingMethod(null);
        }
        
        repository.delete(entity);
        return true;
    }
}