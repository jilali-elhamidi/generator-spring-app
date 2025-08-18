package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;

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

    public MerchandiseShippingService(MerchandiseShippingRepository repository,MerchandiseRepository merchandiseItemRepository,MerchandiseOrderRepository orderRepository)
    {
        super(repository);
        this.merchandiseshippingRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public MerchandiseShipping save(MerchandiseShipping merchandiseshipping) {


    

    

    if (merchandiseshipping.getMerchandiseItem() != null
        && merchandiseshipping.getMerchandiseItem().getId() != null) {
        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandiseshipping.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandiseshipping.setMerchandiseItem(existingMerchandiseItem);
        }
    
    
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

// Relations ManyToOne : mise à jour conditionnelle
        if (merchandiseshippingRequest.getMerchandiseItem() != null &&
        merchandiseshippingRequest.getMerchandiseItem().getId() != null) {

        Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(
        merchandiseshippingRequest.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

        existing.setMerchandiseItem(existingMerchandiseItem);
        } else {
        existing.setMerchandiseItem(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

        if (merchandiseshippingRequest.getOrder() != null
        && merchandiseshippingRequest.getOrder().getId() != null) {

        MerchandiseOrder order = orderRepository.findById(
        merchandiseshippingRequest.getOrder().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setOrder(order);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
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

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    
        if (entity.getOrder() != null) {
        // Dissocier côté inverse automatiquement
        entity.getOrder().setShippingDetails(null);
        // Dissocier côté direct
        entity.setOrder(null);
        }
    


// --- Dissocier ManyToOne ---

    
        if (entity.getMerchandiseItem() != null) {
        entity.setMerchandiseItem(null);
        }
    

    


repository.delete(entity);
return true;
}
}