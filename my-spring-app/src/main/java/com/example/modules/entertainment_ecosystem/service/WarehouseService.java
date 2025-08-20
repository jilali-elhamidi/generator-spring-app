package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.repository.WarehouseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseInventoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class WarehouseService extends BaseService<Warehouse> {

    protected final WarehouseRepository warehouseRepository;
    private final MerchandiseInventoryRepository inventoryItemsRepository;

    public WarehouseService(WarehouseRepository repository, MerchandiseInventoryRepository inventoryItemsRepository)
    {
        super(repository);
        this.warehouseRepository = repository;
        this.inventoryItemsRepository = inventoryItemsRepository;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
    // ---------- OneToMany ----------
        if (warehouse.getInventoryItems() != null) {
            List<MerchandiseInventory> managedInventoryItems = new ArrayList<>();
            for (MerchandiseInventory item : warehouse.getInventoryItems()) {
                if (item.getId() != null) {
                    MerchandiseInventory existingItem = inventoryItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseInventory not found"));

                     existingItem.setWarehouse(warehouse);
                     managedInventoryItems.add(existingItem);
                } else {
                    item.setWarehouse(warehouse);
                    managedInventoryItems.add(item);
                }
            }
            warehouse.setInventoryItems(managedInventoryItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return warehouseRepository.save(warehouse);
}


    public Warehouse update(Long id, Warehouse warehouseRequest) {
        Warehouse existing = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));

    // Copier les champs simples
        existing.setName(warehouseRequest.getName());
        existing.setAddress(warehouseRequest.getAddress());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getInventoryItems().clear();

        if (warehouseRequest.getInventoryItems() != null) {
            for (var item : warehouseRequest.getInventoryItems()) {
                MerchandiseInventory existingItem;
                if (item.getId() != null) {
                    existingItem = inventoryItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseInventory not found"));
                } else {
                existingItem = item;
                }

                existingItem.setWarehouse(existing);
                existing.getInventoryItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return warehouseRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Warehouse> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Warehouse entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getInventoryItems() != null) {
            for (var child : entity.getInventoryItems()) {
                
                child.setWarehouse(null); // retirer la référence inverse
                
            }
            entity.getInventoryItems().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}