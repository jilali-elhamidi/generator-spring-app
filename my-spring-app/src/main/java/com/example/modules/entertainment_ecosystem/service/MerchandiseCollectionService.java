package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCollectionRepository;

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
public class MerchandiseCollectionService extends BaseService<MerchandiseCollection> {

    protected final MerchandiseCollectionRepository merchandisecollectionRepository;
    
    protected final MerchandiseRepository itemsRepository;
    

    public MerchandiseCollectionService(MerchandiseCollectionRepository repository, MerchandiseRepository itemsRepository)
    {
        super(repository);
        this.merchandisecollectionRepository = repository;
        
        this.itemsRepository = itemsRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseCollection save(MerchandiseCollection merchandisecollection) {
    // ---------- OneToMany ----------
        if (merchandisecollection.getItems() != null) {
            List<Merchandise> managedItems = new ArrayList<>();
            for (Merchandise item : merchandisecollection.getItems()) {
                if (item.getId() != null) {
                    Merchandise existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                     existingItem.setCollection(merchandisecollection);
                     managedItems.add(existingItem);
                } else {
                    item.setCollection(merchandisecollection);
                    managedItems.add(item);
                }
            }
            merchandisecollection.setItems(managedItems);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return merchandisecollectionRepository.save(merchandisecollection);
}

    @Transactional
    @Override
    public MerchandiseCollection update(Long id, MerchandiseCollection merchandisecollectionRequest) {
        MerchandiseCollection existing = merchandisecollectionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseCollection not found"));

    // Copier les champs simples
        existing.setName(merchandisecollectionRequest.getName());
        existing.setDescription(merchandisecollectionRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getItems().clear();

        if (merchandisecollectionRequest.getItems() != null) {
            for (var item : merchandisecollectionRequest.getItems()) {
                Merchandise existingItem;
                if (item.getId() != null) {
                    existingItem = itemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCollection(existing);
                existing.getItems().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return merchandisecollectionRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseCollection> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseCollection> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseCollection.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseCollection> saveAll(List<MerchandiseCollection> merchandisecollectionList) {
        return super.saveAll(merchandisecollectionList);
    }

}