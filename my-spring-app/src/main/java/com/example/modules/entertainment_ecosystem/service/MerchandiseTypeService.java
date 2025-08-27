package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseTypeRepository;

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
public class MerchandiseTypeService extends BaseService<MerchandiseType> {

    protected final MerchandiseTypeRepository merchandisetypeRepository;
    
    protected final MerchandiseRepository itemsRepository;
    

    public MerchandiseTypeService(MerchandiseTypeRepository repository, MerchandiseRepository itemsRepository)
    {
        super(repository);
        this.merchandisetypeRepository = repository;
        
        this.itemsRepository = itemsRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseType save(MerchandiseType merchandisetype) {
    // ---------- OneToMany ----------
        if (merchandisetype.getItems() != null) {
            List<Merchandise> managedItems = new ArrayList<>();
            for (Merchandise item : merchandisetype.getItems()) {
                if (item.getId() != null) {
                    Merchandise existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                     existingItem.setProductType(merchandisetype);
                     managedItems.add(existingItem);
                } else {
                    item.setProductType(merchandisetype);
                    managedItems.add(item);
                }
            }
            merchandisetype.setItems(managedItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return merchandisetypeRepository.save(merchandisetype);
}

    @Transactional
    @Override
    public MerchandiseType update(Long id, MerchandiseType merchandisetypeRequest) {
        MerchandiseType existing = merchandisetypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

    // Copier les champs simples
        existing.setName(merchandisetypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getItems().clear();

        if (merchandisetypeRequest.getItems() != null) {
            for (var item : merchandisetypeRequest.getItems()) {
                Merchandise existingItem;
                if (item.getId() != null) {
                    existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProductType(existing);
                existing.getItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return merchandisetypeRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseType> saveAll(List<MerchandiseType> merchandisetypeList) {
        return super.saveAll(merchandisetypeList);
    }

}