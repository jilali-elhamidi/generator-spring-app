package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetTypeRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DigitalAssetTypeService extends BaseService<DigitalAssetType> {

    protected final DigitalAssetTypeRepository digitalassettypeRepository;

    public DigitalAssetTypeService(DigitalAssetTypeRepository repository)
    {
        super(repository);
        this.digitalassettypeRepository = repository;
    }

    @Override
    public DigitalAssetType save(DigitalAssetType digitalassettype) {

        if (digitalassettype.getAssets() != null) {
            for (DigitalAsset item : digitalassettype.getAssets()) {
            item.setAssetType(digitalassettype);
            }
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

        existing.getAssets().clear();
        if (digitalassettypeRequest.getAssets() != null) {
            for (var item : digitalassettypeRequest.getAssets()) {
            item.setAssetType(existing);
            existing.getAssets().add(item);
            }
        }

    


        return digitalassettypeRepository.save(existing);
    }
}