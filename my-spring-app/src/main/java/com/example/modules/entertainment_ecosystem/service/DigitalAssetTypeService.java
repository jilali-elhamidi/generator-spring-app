package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetTypeRepository;

import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DigitalAssetTypeService extends BaseService<DigitalAssetType> {

    protected final DigitalAssetTypeRepository digitalassettypeRepository;
    
    protected final DigitalAssetRepository assetsRepository;
    

    public DigitalAssetTypeService(DigitalAssetTypeRepository repository, DigitalAssetRepository assetsRepository)
    {
        super(repository);
        this.digitalassettypeRepository = repository;
        
        this.assetsRepository = assetsRepository;
        
    }

    @Transactional
    @Override
    public DigitalAssetType save(DigitalAssetType digitalassettype) {
    // ---------- OneToMany ----------
        if (digitalassettype.getAssets() != null) {
            List<DigitalAsset> managedAssets = new ArrayList<>();
            for (DigitalAsset item : digitalassettype.getAssets()) {
                if (item.getId() != null) {
                    DigitalAsset existingItem = assetsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));

                     existingItem.setAssetType(digitalassettype);
                     managedAssets.add(existingItem);
                } else {
                    item.setAssetType(digitalassettype);
                    managedAssets.add(item);
                }
            }
            digitalassettype.setAssets(managedAssets);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return digitalassettypeRepository.save(digitalassettype);
}

    @Transactional
    @Override
    public DigitalAssetType update(Long id, DigitalAssetType digitalassettypeRequest) {
        DigitalAssetType existing = digitalassettypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DigitalAssetType not found"));

    // Copier les champs simples
        existing.setName(digitalassettypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAssets().clear();

        if (digitalassettypeRequest.getAssets() != null) {
            for (var item : digitalassettypeRequest.getAssets()) {
                DigitalAsset existingItem;
                if (item.getId() != null) {
                    existingItem = assetsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAssetType(existing);
                existing.getAssets().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return digitalassettypeRepository.save(existing);
}

    // Pagination simple
    public Page<DigitalAssetType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<DigitalAssetType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(DigitalAssetType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<DigitalAssetType> saveAll(List<DigitalAssetType> digitalassettypeList) {
        return super.saveAll(digitalassettypeList);
    }

}