package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseTypeService extends BaseService<MerchandiseType> {

    protected final MerchandiseTypeRepository merchandisetypeRepository;
    private final MerchandiseRepository itemsRepository;

    public MerchandiseTypeService(MerchandiseTypeRepository repository, MerchandiseRepository itemsRepository)
    {
        super(repository);
        this.merchandisetypeRepository = repository;
        this.itemsRepository = itemsRepository;
    }

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


    public MerchandiseType update(Long id, MerchandiseType merchandisetypeRequest) {
        MerchandiseType existing = merchandisetypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

    // Copier les champs simples
        existing.setName(merchandisetypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MerchandiseType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MerchandiseType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getItems() != null) {
            for (var child : entity.getItems()) {
                // retirer la référence inverse
                child.setProductType(null);
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
    public List<MerchandiseType> saveAll(List<MerchandiseType> merchandisetypeList) {

        return merchandisetypeRepository.saveAll(merchandisetypeList);
    }

}