package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.repository.WarehouseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class WarehouseService extends BaseService<Warehouse> {

    protected final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository repository)
    {
        super(repository);
        this.warehouseRepository = repository;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {

        if (warehouse.getInventoryItems() != null) {
            for (MerchandiseInventory item : warehouse.getInventoryItems()) {
            item.setWarehouse(warehouse);
            }
        }

        return warehouseRepository.save(warehouse);
    }


    public Warehouse update(Long id, Warehouse warehouseRequest) {
        Warehouse existing = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));

    // Copier les champs simples
        existing.setName(warehouseRequest.getName());
        existing.setAddress(warehouseRequest.getAddress());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getInventoryItems().clear();
        if (warehouseRequest.getInventoryItems() != null) {
            for (var item : warehouseRequest.getInventoryItems()) {
            item.setWarehouse(existing);
            existing.getInventoryItems().add(item);
            }
        }

    


        return warehouseRepository.save(existing);
    }
}