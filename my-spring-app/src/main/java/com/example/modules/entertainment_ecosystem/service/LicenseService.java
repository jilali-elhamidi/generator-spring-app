package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.repository.LicenseRepository;

import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class LicenseService extends BaseService<License> {

    protected final LicenseRepository licenseRepository;
    
    protected final DigitalAssetRepository assetRepository;
    

    public LicenseService(LicenseRepository repository, DigitalAssetRepository assetRepository)
    {
        super(repository);
        this.licenseRepository = repository;
        
        this.assetRepository = assetRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public License update(Long id, License licenseRequest) {
        License existing = licenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("License not found"));

    // Copier les champs simples
        existing.setLicenseKey(licenseRequest.getLicenseKey());
        existing.setStartDate(licenseRequest.getStartDate());
        existing.setEndDate(licenseRequest.getEndDate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<License> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<License> search(Map<String, String> filters, Pageable pageable) {
        return super.search(License.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<License> saveAll(List<License> licenseList) {
        return super.saveAll(licenseList);
    }

}