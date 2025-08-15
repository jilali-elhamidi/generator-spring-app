package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetTypeRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DigitalAssetTypeService extends BaseService<DigitalAssetType> {

    protected final DigitalAssetTypeRepository digitalassettypeRepository;
    private final DigitalAssetRepository assetsRepository;

    public DigitalAssetTypeService(DigitalAssetTypeRepository repository,DigitalAssetRepository assetsRepository)
    {
        super(repository);
        this.digitalassettypeRepository = repository;
        this.assetsRepository = assetsRepository;
    }

    @Override
    public DigitalAssetType save(DigitalAssetType digitalassettype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (digitalassettype.getAssets() != null) {
            List<DigitalAsset> managedAssets = new ArrayList<>();
            for (DigitalAsset item : digitalassettype.getAssets()) {
            if (item.getId() != null) {
            DigitalAsset existingItem = assetsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAssetType(digitalassettype);
            managedAssets.add(existingItem);
            } else {
            item.setAssetType(digitalassettype);
            managedAssets.add(item);
            }
            }
            digitalassettype.setAssets(managedAssets);
            }
        
    

    

        return digitalassettypeRepository.save(digitalassettype);
    }


    public DigitalAssetType update(Long id, DigitalAssetType digitalassettypeRequest) {
        DigitalAssetType existing = digitalassettypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DigitalAssetType not found"));

    // Copier les champs simples
        existing.setName(digitalassettypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getAssets().clear();

        if (digitalassettypeRequest.getAssets() != null) {
        for (var item : digitalassettypeRequest.getAssets()) {
        DigitalAsset existingItem;
        if (item.getId() != null) {
        existingItem = assetsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAssetType(existing);

        // Ajouter directement dans la collection existante
        existing.getAssets().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return digitalassettypeRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<DigitalAssetType> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

DigitalAssetType entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getAssets() != null) {
        for (var child : entity.getAssets()) {
        
            child.setAssetType(null); // retirer la référence inverse
        
        }
        entity.getAssets().clear();
        }
    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}