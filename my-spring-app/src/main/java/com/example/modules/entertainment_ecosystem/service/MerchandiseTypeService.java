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

    public MerchandiseTypeService(MerchandiseTypeRepository repository,MerchandiseRepository itemsRepository)
    {
        super(repository);
        this.merchandisetypeRepository = repository;
        this.itemsRepository = itemsRepository;
    }

    @Override
    public MerchandiseType save(MerchandiseType merchandisetype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandisetype.getItems() != null) {
            List<Merchandise> managedItems = new ArrayList<>();
            for (Merchandise item : merchandisetype.getItems()) {
            if (item.getId() != null) {
            Merchandise existingItem = itemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Merchandise not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProductType(merchandisetype);
            managedItems.add(existingItem);
            } else {
            item.setProductType(merchandisetype);
            managedItems.add(item);
            }
            }
            merchandisetype.setItems(managedItems);
            }
        
    

    

        return merchandisetypeRepository.save(merchandisetype);
    }


    public MerchandiseType update(Long id, MerchandiseType merchandisetypeRequest) {
        MerchandiseType existing = merchandisetypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

    // Copier les champs simples
        existing.setName(merchandisetypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getItems().clear();

        if (merchandisetypeRequest.getItems() != null) {
        for (var item : merchandisetypeRequest.getItems()) {
        Merchandise existingItem;
        if (item.getId() != null) {
        existingItem = itemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProductType(existing);

        // Ajouter directement dans la collection existante
        existing.getItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


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
        
            child.setProductType(null); // retirer la référence inverse
        
        }
        entity.getItems().clear();
        }
    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}