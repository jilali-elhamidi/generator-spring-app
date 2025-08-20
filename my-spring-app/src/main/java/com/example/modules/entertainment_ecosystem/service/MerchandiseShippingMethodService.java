package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingMethodRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseShippingMethodService extends BaseService<MerchandiseShippingMethod> {

    protected final MerchandiseShippingMethodRepository merchandiseshippingmethodRepository;
    private final MerchandiseShippingRepository shipmentsRepository;

    public MerchandiseShippingMethodService(MerchandiseShippingMethodRepository repository,MerchandiseShippingRepository shipmentsRepository)
    {
        super(repository);
        this.merchandiseshippingmethodRepository = repository;
        this.shipmentsRepository = shipmentsRepository;
    }

    @Override
    public MerchandiseShippingMethod save(MerchandiseShippingMethod merchandiseshippingmethod) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandiseshippingmethod.getShipments() != null) {
            List<MerchandiseShipping> managedShipments = new ArrayList<>();
            for (MerchandiseShipping item : merchandiseshippingmethod.getShipments()) {
            if (item.getId() != null) {
            MerchandiseShipping existingItem = shipmentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setShippingMethod(merchandiseshippingmethod);
            managedShipments.add(existingItem);
            } else {
            item.setShippingMethod(merchandiseshippingmethod);
            managedShipments.add(item);
            }
            }
            merchandiseshippingmethod.setShipments(managedShipments);
            }
        
    


    

    

        return merchandiseshippingmethodRepository.save(merchandiseshippingmethod);
    }


    public MerchandiseShippingMethod update(Long id, MerchandiseShippingMethod merchandiseshippingmethodRequest) {
        MerchandiseShippingMethod existing = merchandiseshippingmethodRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseShippingMethod not found"));

    // Copier les champs simples
        existing.setName(merchandiseshippingmethodRequest.getName());
        existing.setCost(merchandiseshippingmethodRequest.getCost());
        existing.setEstimatedDeliveryTime(merchandiseshippingmethodRequest.getEstimatedDeliveryTime());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getShipments().clear();

        if (merchandiseshippingmethodRequest.getShipments() != null) {
        for (var item : merchandiseshippingmethodRequest.getShipments()) {
        MerchandiseShipping existingItem;
        if (item.getId() != null) {
        existingItem = shipmentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setShippingMethod(existing);

        // Ajouter directement dans la collection existante
        existing.getShipments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return merchandiseshippingmethodRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MerchandiseShippingMethod> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MerchandiseShippingMethod entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getShipments() != null) {
        for (var child : entity.getShipments()) {
        
            child.setShippingMethod(null); // retirer la référence inverse
        
        }
        entity.getShipments().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}