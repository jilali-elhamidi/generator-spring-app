package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingMethodRepository;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseShippingMethodService extends BaseService<MerchandiseShippingMethod> {

    protected final MerchandiseShippingMethodRepository merchandiseshippingmethodRepository;
    
    protected final MerchandiseShippingRepository shipmentsRepository;
    

    public MerchandiseShippingMethodService(MerchandiseShippingMethodRepository repository, MerchandiseShippingRepository shipmentsRepository)
    {
        super(repository);
        this.merchandiseshippingmethodRepository = repository;
        
        this.shipmentsRepository = shipmentsRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseShippingMethod save(MerchandiseShippingMethod merchandiseshippingmethod) {
    // ---------- OneToMany ----------
        if (merchandiseshippingmethod.getShipments() != null) {
            List<MerchandiseShipping> managedShipments = new ArrayList<>();
            for (MerchandiseShipping item : merchandiseshippingmethod.getShipments()) {
                if (item.getId() != null) {
                    MerchandiseShipping existingItem = shipmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));

                     existingItem.setShippingMethod(merchandiseshippingmethod);
                     managedShipments.add(existingItem);
                } else {
                    item.setShippingMethod(merchandiseshippingmethod);
                    managedShipments.add(item);
                }
            }
            merchandiseshippingmethod.setShipments(managedShipments);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return merchandiseshippingmethodRepository.save(merchandiseshippingmethod);
}

    @Transactional
    @Override
    public MerchandiseShippingMethod update(Long id, MerchandiseShippingMethod merchandiseshippingmethodRequest) {
        MerchandiseShippingMethod existing = merchandiseshippingmethodRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseShippingMethod not found"));

    // Copier les champs simples
        existing.setName(merchandiseshippingmethodRequest.getName());
        existing.setCost(merchandiseshippingmethodRequest.getCost());
        existing.setEstimatedDeliveryTime(merchandiseshippingmethodRequest.getEstimatedDeliveryTime());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getShipments().clear();

        if (merchandiseshippingmethodRequest.getShipments() != null) {
            for (var item : merchandiseshippingmethodRequest.getShipments()) {
                MerchandiseShipping existingItem;
                if (item.getId() != null) {
                    existingItem = shipmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
                } else {
                existingItem = item;
                }

                existingItem.setShippingMethod(existing);
                existing.getShipments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return merchandiseshippingmethodRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseShippingMethod> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseShippingMethod> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseShippingMethod.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseShippingMethod> saveAll(List<MerchandiseShippingMethod> merchandiseshippingmethodList) {
        return super.saveAll(merchandiseshippingmethodList);
    }

}