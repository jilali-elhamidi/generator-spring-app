package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.repository.LicenseRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class LicenseService extends BaseService<License> {

    protected final LicenseRepository licenseRepository;
    private final DigitalAssetRepository assetRepository;

    public LicenseService(LicenseRepository repository, DigitalAssetRepository assetRepository)
    {
        super(repository);
        this.licenseRepository = repository;
        this.assetRepository = assetRepository;
    }

    @Override
    public License save(License license) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (license.getAsset() != null) {
            if (license.getAsset().getId() != null) {
                DigitalAsset existingAsset = assetRepository.findById(license.getAsset().getId())
                    .orElseThrow(() -> new RuntimeException("DigitalAsset not found with id "
                        + license.getAsset().getId()));
                license.setAsset(existingAsset);
            } else {
                // Nouvel objet → sauvegarde d'abord
                DigitalAsset newAsset = assetRepository.save(license.getAsset());
                license.setAsset(newAsset);
            }

            license.getAsset().setLicense(license);
        }
        
    return licenseRepository.save(license);
}


    public License update(Long id, License licenseRequest) {
        License existing = licenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("License not found"));

    // Copier les champs simples
        existing.setLicenseKey(licenseRequest.getLicenseKey());
        existing.setStartDate(licenseRequest.getStartDate());
        existing.setEndDate(licenseRequest.getEndDate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (licenseRequest.getAsset() != null &&licenseRequest.getAsset().getId() != null) {

        DigitalAsset asset = assetRepository.findById(licenseRequest.getAsset().getId())
                .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));

        existing.setAsset(asset);
        asset.setLicense(existing);
        }
    
    return licenseRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<License> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        License entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getAsset() != null) {
            entity.getAsset().setLicense(null);
            entity.setAsset(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}