package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.repository.LicenseRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class LicenseService extends BaseService<License> {

    protected final LicenseRepository licenseRepository;
    private final DigitalAssetRepository assetRepository;

    public LicenseService(LicenseRepository repository,DigitalAssetRepository assetRepository)
    {
        super(repository);
        this.licenseRepository = repository;
            this.assetRepository = assetRepository;
    }

    @Override
    public License save(License license) {
        if (license.getAsset() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            license.setAsset(
            assetRepository.findById(license.getAsset().getId())
            .orElseThrow(() -> new RuntimeException("asset not found"))
            );
        
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

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (licenseRequest.getAsset() != null
        && licenseRequest.getAsset().getId() != null) {

        DigitalAsset asset = assetRepository.findById(
        licenseRequest.getAsset().getId()
        ).orElseThrow(() -> new RuntimeException("DigitalAsset not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setAsset(asset);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            asset.setLicense(existing);
        
        }

    


        return licenseRepository.save(existing);
    }
}