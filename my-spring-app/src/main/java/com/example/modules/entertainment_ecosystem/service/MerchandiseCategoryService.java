package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseCategoryService extends BaseService<MerchandiseCategory> {

    protected final MerchandiseCategoryRepository merchandisecategoryRepository;
    private final MerchandiseRepository merchandiseRepository;

    public MerchandiseCategoryService(MerchandiseCategoryRepository repository, MerchandiseRepository merchandiseRepository)
    {
        super(repository);
        this.merchandisecategoryRepository = repository;
        this.merchandiseRepository = merchandiseRepository;
    }

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


    public MerchandiseCategory update(Long id, MerchandiseCategory merchandisecategoryRequest) {
        MerchandiseCategory existing = merchandisecategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseCategory not found"));

    // Copier les champs simples
        existing.setName(merchandisecategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseCategory> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseCategory entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getMerchandise() != null) {
            for (var child : entity.getMerchandise()) {
                
                child.setCategory(null); // retirer la référence inverse
                
            }
            entity.getMerchandise().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}