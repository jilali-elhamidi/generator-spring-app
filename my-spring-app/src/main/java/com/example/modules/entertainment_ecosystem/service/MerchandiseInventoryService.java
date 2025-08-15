package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseInventoryRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.repository.WarehouseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseInventoryService extends BaseService<MerchandiseInventory> {

    protected final MerchandiseInventoryRepository merchandiseinventoryRepository;
    private final MerchandiseRepository merchandiseItemRepository;
    private final WarehouseRepository warehouseRepository;

    public MerchandiseInventoryService(MerchandiseInventoryRepository repository,MerchandiseRepository merchandiseItemRepository,WarehouseRepository warehouseRepository)
    {
        super(repository);
        this.merchandiseinventoryRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public MerchandiseInventory save(MerchandiseInventory merchandiseinventory) {


    

    

    
    if (merchandiseinventory.getWarehouse() != null
        && merchandiseinventory.getWarehouse().getId() != null) {
        Warehouse existingWarehouse = warehouseRepository.findById(
        merchandiseinventory.getWarehouse().getId()
        ).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        merchandiseinventory.setWarehouse(existingWarehouse);
        }
    
        if (merchandiseinventory.getMerchandiseItem() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            merchandiseinventory.setMerchandiseItem(
            merchandiseItemRepository.findById(merchandiseinventory.getMerchandiseItem().getId())
            .orElseThrow(() -> new RuntimeException("merchandiseItem not found"))
            );
        
        merchandiseinventory.getMerchandiseItem().setInventory(merchandiseinventory);
        }

        return merchandiseinventoryRepository.save(merchandiseinventory);
    }


    public MerchandiseInventory update(Long id, MerchandiseInventory merchandiseinventoryRequest) {
        MerchandiseInventory existing = merchandiseinventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseInventory not found"));

    // Copier les champs simples
        existing.setStockQuantity(merchandiseinventoryRequest.getStockQuantity());
        existing.setLastUpdated(merchandiseinventoryRequest.getLastUpdated());

// Relations ManyToOne : mise à jour conditionnelle
        if (merchandiseinventoryRequest.getWarehouse() != null &&
        merchandiseinventoryRequest.getWarehouse().getId() != null) {

        Warehouse existingWarehouse = warehouseRepository.findById(
        merchandiseinventoryRequest.getWarehouse().getId()
        ).orElseThrow(() -> new RuntimeException("Warehouse not found"));

        existing.setWarehouse(existingWarehouse);
        } else {
        existing.setWarehouse(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (merchandiseinventoryRequest.getMerchandiseItem() != null
        && merchandiseinventoryRequest.getMerchandiseItem().getId() != null) {

        Merchandise merchandiseItem = merchandiseItemRepository.findById(
        merchandiseinventoryRequest.getMerchandiseItem().getId()
        ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setMerchandiseItem(merchandiseItem);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            merchandiseItem.setInventory(existing);
        
        }

    

    


        return merchandiseinventoryRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MerchandiseInventory> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MerchandiseInventory entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    
        if (entity.getMerchandiseItem() != null) {
        // Dissocier côté inverse automatiquement
        entity.getMerchandiseItem().setInventory(null);
        // Dissocier côté direct
        entity.setMerchandiseItem(null);
        }
    

    


// --- Dissocier ManyToOne ---

    

    
        if (entity.getWarehouse() != null) {
        entity.setWarehouse(null);
        }
    


repository.delete(entity);
return true;
}
}