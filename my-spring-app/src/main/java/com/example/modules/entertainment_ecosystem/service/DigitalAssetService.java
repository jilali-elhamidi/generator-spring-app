package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.repository.LicenseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DigitalAssetService extends BaseService<DigitalAsset> {

    protected final DigitalAssetRepository digitalassetRepository;
    private final DigitalAssetTypeRepository assetTypeRepository;
    private final ArtistRepository artistRepository;
    private final LicenseRepository licenseRepository;

    public DigitalAssetService(DigitalAssetRepository repository,DigitalAssetTypeRepository assetTypeRepository,ArtistRepository artistRepository,LicenseRepository licenseRepository)
    {
        super(repository);
        this.digitalassetRepository = repository;
        this.assetTypeRepository = assetTypeRepository;
        this.artistRepository = artistRepository;
        this.licenseRepository = licenseRepository;
    }

    @Override
    public DigitalAsset save(DigitalAsset digitalasset) {


    

    

    

    if (digitalasset.getAssetType() != null
        && digitalasset.getAssetType().getId() != null) {
        DigitalAssetType existingAssetType = assetTypeRepository.findById(
        digitalasset.getAssetType().getId()
        ).orElseThrow(() -> new RuntimeException("DigitalAssetType not found"));
        digitalasset.setAssetType(existingAssetType);
        }
    
    if (digitalasset.getArtist() != null
        && digitalasset.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        digitalasset.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        digitalasset.setArtist(existingArtist);
        }
    
    
        if (digitalasset.getLicense() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            digitalasset.setLicense(
            licenseRepository.findById(digitalasset.getLicense().getId())
            .orElseThrow(() -> new RuntimeException("license not found"))
            );
        
        digitalasset.getLicense().setAsset(digitalasset);
        }

        return digitalassetRepository.save(digitalasset);
    }


    public DigitalAsset update(Long id, DigitalAsset digitalassetRequest) {
        DigitalAsset existing = digitalassetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));

    // Copier les champs simples
        existing.setName(digitalassetRequest.getName());
        existing.setUrl(digitalassetRequest.getUrl());

// Relations ManyToOne : mise à jour conditionnelle
        if (digitalassetRequest.getAssetType() != null &&
        digitalassetRequest.getAssetType().getId() != null) {

        DigitalAssetType existingAssetType = assetTypeRepository.findById(
        digitalassetRequest.getAssetType().getId()
        ).orElseThrow(() -> new RuntimeException("DigitalAssetType not found"));

        existing.setAssetType(existingAssetType);
        } else {
        existing.setAssetType(null);
        }
        if (digitalassetRequest.getArtist() != null &&
        digitalassetRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        digitalassetRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

        if (digitalassetRequest.getLicense() != null
        && digitalassetRequest.getLicense().getId() != null) {

        License license = licenseRepository.findById(
        digitalassetRequest.getLicense().getId()
        ).orElseThrow(() -> new RuntimeException("License not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setLicense(license);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            license.setAsset(existing);
        
        }

    


        return digitalassetRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<DigitalAsset> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

DigitalAsset entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    



// --- Dissocier OneToOne ---

    

    

    
        if (entity.getLicense() != null) {
        // Dissocier côté inverse automatiquement
        entity.getLicense().setAsset(null);
        // Dissocier côté direct
        entity.setLicense(null);
        }
    


// --- Dissocier ManyToOne ---

    
        if (entity.getAssetType() != null) {
        entity.setAssetType(null);
        }
    

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    

    


repository.delete(entity);
return true;
}
}