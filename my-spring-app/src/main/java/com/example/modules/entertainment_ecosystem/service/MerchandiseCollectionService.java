package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCollectionRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseCollectionService extends BaseService<MerchandiseCollection> {

    protected final MerchandiseCollectionRepository merchandisecollectionRepository;
    private final MerchandiseRepository itemsRepository;

    public MerchandiseCollectionService(MerchandiseCollectionRepository repository, MerchandiseRepository itemsRepository)
    {
        super(repository);
        this.merchandisecollectionRepository = repository;
        this.itemsRepository = itemsRepository;
    }

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


    public MerchandiseCollection update(Long id, MerchandiseCollection merchandisecollectionRequest) {
        MerchandiseCollection existing = merchandisecollectionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseCollection not found"));

    // Copier les champs simples
        existing.setName(merchandisecollectionRequest.getName());
        existing.setDescription(merchandisecollectionRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseCollection> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseCollection entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getItems() != null) {
            for (var child : entity.getItems()) {
                // retirer la référence inverse
                child.setCollection(null);
            }
            entity.getItems().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<MerchandiseCollection> saveAll(List<MerchandiseCollection> merchandisecollectionList) {

        return merchandisecollectionRepository.saveAll(merchandisecollectionList);
    }

}