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

    public DigitalAssetService(DigitalAssetRepository repository, DigitalAssetTypeRepository assetTypeRepository, ArtistRepository artistRepository, LicenseRepository licenseRepository)
    {
        super(repository);
        this.digitalassetRepository = repository;
        this.assetTypeRepository = assetTypeRepository;
        this.artistRepository = artistRepository;
        this.licenseRepository = licenseRepository;
    }

    @Override
    public DigitalAsset save(DigitalAsset digitalasset) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (digitalasset.getAssetType() != null) {
            if (digitalasset.getAssetType().getId() != null) {
                DigitalAssetType existingAssetType = assetTypeRepository.findById(
                    digitalasset.getAssetType().getId()
                ).orElseThrow(() -> new RuntimeException("DigitalAssetType not found with id "
                    + digitalasset.getAssetType().getId()));
                digitalasset.setAssetType(existingAssetType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                DigitalAssetType newAssetType = assetTypeRepository.save(digitalasset.getAssetType());
                digitalasset.setAssetType(newAssetType);
            }
        }
        
        if (digitalasset.getArtist() != null) {
            if (digitalasset.getArtist().getId() != null) {
                Artist existingArtist = artistRepository.findById(
                    digitalasset.getArtist().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + digitalasset.getArtist().getId()));
                digitalasset.setArtist(existingArtist);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newArtist = artistRepository.save(digitalasset.getArtist());
                digitalasset.setArtist(newArtist);
            }
        }
        
    // ---------- OneToOne ----------
        if (digitalasset.getLicense() != null) {
            if (digitalasset.getLicense().getId() != null) {
                License existingLicense = licenseRepository.findById(digitalasset.getLicense().getId())
                    .orElseThrow(() -> new RuntimeException("License not found with id "
                        + digitalasset.getLicense().getId()));
                digitalasset.setLicense(existingLicense);
            } else {
                // Nouvel objet → sauvegarde d'abord
                License newLicense = licenseRepository.save(digitalasset.getLicense());
                digitalasset.setLicense(newLicense);
            }

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

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (digitalassetRequest.getLicense() != null &&digitalassetRequest.getLicense().getId() != null) {

        License license = licenseRepository.findById(digitalassetRequest.getLicense().getId())
                .orElseThrow(() -> new RuntimeException("License not found"));

        existing.setLicense(license);
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
            entity.getLicense().setAsset(null);
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
    @Transactional
    public List<DigitalAsset> saveAll(List<DigitalAsset> digitalassetList) {

        return digitalassetRepository.saveAll(digitalassetList);
    }

}