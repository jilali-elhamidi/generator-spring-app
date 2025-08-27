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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DigitalAssetService extends BaseService<DigitalAsset> {

    protected final DigitalAssetRepository digitalassetRepository;
    
    protected final DigitalAssetTypeRepository assetTypeRepository;
    
    protected final ArtistRepository artistRepository;
    
    protected final LicenseRepository licenseRepository;
    

    public DigitalAssetService(DigitalAssetRepository repository, DigitalAssetTypeRepository assetTypeRepository, ArtistRepository artistRepository, LicenseRepository licenseRepository)
    {
        super(repository);
        this.digitalassetRepository = repository;
        
        this.assetTypeRepository = assetTypeRepository;
        
        this.artistRepository = artistRepository;
        
        this.licenseRepository = licenseRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<DigitalAsset> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<DigitalAsset> search(Map<String, String> filters, Pageable pageable) {
        return super.search(DigitalAsset.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<DigitalAsset> saveAll(List<DigitalAsset> digitalassetList) {
        return super.saveAll(digitalassetList);
    }

}