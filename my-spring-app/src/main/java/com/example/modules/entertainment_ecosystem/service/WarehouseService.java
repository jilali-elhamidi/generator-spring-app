package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.repository.WarehouseRepository;

import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseInventoryRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class WarehouseService extends BaseService<Warehouse> {

    protected final WarehouseRepository warehouseRepository;
    
    protected final MerchandiseInventoryRepository inventoryItemsRepository;
    

    public WarehouseService(WarehouseRepository repository, MerchandiseInventoryRepository inventoryItemsRepository)
    {
        super(repository);
        this.warehouseRepository = repository;
        
        this.inventoryItemsRepository = inventoryItemsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Warehouse update(Long id, Warehouse warehouseRequest) {
        Warehouse existing = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));

    // Copier les champs simples
        existing.setName(warehouseRequest.getName());
        existing.setAddress(warehouseRequest.getAddress());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Warehouse> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Warehouse> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Warehouse.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Warehouse> saveAll(List<Warehouse> warehouseList) {
        return super.saveAll(warehouseList);
    }

}