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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseInventoryService extends BaseService<MerchandiseInventory> {

    protected final MerchandiseInventoryRepository merchandiseinventoryRepository;
    
    protected final MerchandiseRepository merchandiseItemRepository;
    
    protected final WarehouseRepository warehouseRepository;
    

    public MerchandiseInventoryService(MerchandiseInventoryRepository repository, MerchandiseRepository merchandiseItemRepository, WarehouseRepository warehouseRepository)
    {
        super(repository);
        this.merchandiseinventoryRepository = repository;
        
        this.merchandiseItemRepository = merchandiseItemRepository;
        
        this.warehouseRepository = warehouseRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<MerchandiseInventory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseInventory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseInventory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseInventory> saveAll(List<MerchandiseInventory> merchandiseinventoryList) {
        return super.saveAll(merchandiseinventoryList);
    }

}