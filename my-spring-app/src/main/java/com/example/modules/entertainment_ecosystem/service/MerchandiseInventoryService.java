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

    public MerchandiseInventoryService(MerchandiseInventoryRepository repository, MerchandiseRepository merchandiseItemRepository, WarehouseRepository warehouseRepository)
    {
        super(repository);
        this.merchandiseinventoryRepository = repository;
        this.merchandiseItemRepository = merchandiseItemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public MerchandiseInventory save(MerchandiseInventory merchandiseinventory) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandiseinventory.getWarehouse() != null) {
            if (merchandiseinventory.getWarehouse().getId() != null) {
                Warehouse existingWarehouse = warehouseRepository.findById(
                    merchandiseinventory.getWarehouse().getId()
                ).orElseThrow(() -> new RuntimeException("Warehouse not found with id "
                    + merchandiseinventory.getWarehouse().getId()));
                merchandiseinventory.setWarehouse(existingWarehouse);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Warehouse newWarehouse = warehouseRepository.save(merchandiseinventory.getWarehouse());
                merchandiseinventory.setWarehouse(newWarehouse);
            }
        }
        
    // ---------- OneToOne ----------
        if (merchandiseinventory.getMerchandiseItem() != null) {
            if (merchandiseinventory.getMerchandiseItem().getId() != null) {
                Merchandise existingMerchandiseItem = merchandiseItemRepository.findById(merchandiseinventory.getMerchandiseItem().getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found with id "
                        + merchandiseinventory.getMerchandiseItem().getId()));
                merchandiseinventory.setMerchandiseItem(existingMerchandiseItem);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Merchandise newMerchandiseItem = merchandiseItemRepository.save(merchandiseinventory.getMerchandiseItem());
                merchandiseinventory.setMerchandiseItem(newMerchandiseItem);
            }

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

    // ---------- Relations ManyToOne ----------
        if (merchandiseinventoryRequest.getWarehouse() != null &&
            merchandiseinventoryRequest.getWarehouse().getId() != null) {

            Warehouse existingWarehouse = warehouseRepository.findById(
                merchandiseinventoryRequest.getWarehouse().getId()
            ).orElseThrow(() -> new RuntimeException("Warehouse not found"));

            existing.setWarehouse(existingWarehouse);
        } else {
            existing.setWarehouse(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (merchandiseinventoryRequest.getMerchandiseItem() != null &&merchandiseinventoryRequest.getMerchandiseItem().getId() != null) {

        Merchandise merchandiseItem = merchandiseItemRepository.findById(merchandiseinventoryRequest.getMerchandiseItem().getId())
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));

        existing.setMerchandiseItem(merchandiseItem);
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
            entity.getMerchandiseItem().setInventory(null);
            entity.setMerchandiseItem(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getWarehouse() != null) {
            entity.setWarehouse(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<MerchandiseInventory> saveAll(List<MerchandiseInventory> merchandiseinventoryList) {

        return merchandiseinventoryRepository.saveAll(merchandiseinventoryList);
    }

}