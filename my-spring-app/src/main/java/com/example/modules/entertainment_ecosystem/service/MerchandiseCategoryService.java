package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseCategoryService extends BaseService<MerchandiseCategory> {

    protected final MerchandiseCategoryRepository merchandisecategoryRepository;
    
    protected final MerchandiseRepository merchandiseRepository;
    

    public MerchandiseCategoryService(MerchandiseCategoryRepository repository, MerchandiseRepository merchandiseRepository)
    {
        super(repository);
        this.merchandisecategoryRepository = repository;
        
        this.merchandiseRepository = merchandiseRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseCategory save(MerchandiseCategory merchandisecategory) {
    // ---------- OneToMany ----------
        if (merchandisecategory.getMerchandise() != null) {
            List<Merchandise> managedMerchandise = new ArrayList<>();
            for (Merchandise item : merchandisecategory.getMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = merchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                     existingItem.setCategory(merchandisecategory);
                     managedMerchandise.add(existingItem);
                } else {
                    item.setCategory(merchandisecategory);
                    managedMerchandise.add(item);
                }
            }
            merchandisecategory.setMerchandise(managedMerchandise);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return merchandisecategoryRepository.save(merchandisecategory);
}

    @Transactional
    @Override
    public MerchandiseCategory update(Long id, MerchandiseCategory merchandisecategoryRequest) {
        MerchandiseCategory existing = merchandisecategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseCategory not found"));

    // Copier les champs simples
        existing.setName(merchandisecategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getMerchandise().clear();

        if (merchandisecategoryRequest.getMerchandise() != null) {
            for (var item : merchandisecategoryRequest.getMerchandise()) {
                Merchandise existingItem;
                if (item.getId() != null) {
                    existingItem = merchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCategory(existing);
                existing.getMerchandise().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return merchandisecategoryRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseCategory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseCategory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseCategory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseCategory> saveAll(List<MerchandiseCategory> merchandisecategoryList) {
        return super.saveAll(merchandisecategoryList);
    }

}