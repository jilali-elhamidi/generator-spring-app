package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.repository.AdPlacementRepository;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.repository.AdCampaignRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AdPlacementService extends BaseService<AdPlacement> {

    protected final AdPlacementRepository adplacementRepository;
    private final AdCampaignRepository campaignRepository;

    public AdPlacementService(AdPlacementRepository repository,AdCampaignRepository campaignRepository)
    {
        super(repository);
        this.adplacementRepository = repository;
        this.campaignRepository = campaignRepository;
    }

    @Override
    public AdPlacement save(AdPlacement adplacement) {


    


    

    if (adplacement.getCampaign() != null
        && adplacement.getCampaign().getId() != null) {
        AdCampaign existingCampaign = campaignRepository.findById(
        adplacement.getCampaign().getId()
        ).orElseThrow(() -> new RuntimeException("AdCampaign not found"));
        adplacement.setCampaign(existingCampaign);
        }
    

        return adplacementRepository.save(adplacement);
    }


    public AdPlacement update(Long id, AdPlacement adplacementRequest) {
        AdPlacement existing = adplacementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AdPlacement not found"));

    // Copier les champs simples
        existing.setName(adplacementRequest.getName());
        existing.setLocation(adplacementRequest.getLocation());
        existing.setAdType(adplacementRequest.getAdType());

// Relations ManyToOne : mise à jour conditionnelle
        if (adplacementRequest.getCampaign() != null &&
        adplacementRequest.getCampaign().getId() != null) {

        AdCampaign existingCampaign = campaignRepository.findById(
        adplacementRequest.getCampaign().getId()
        ).orElseThrow(() -> new RuntimeException("AdCampaign not found"));

        existing.setCampaign(existingCampaign);
        } else {
        existing.setCampaign(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return adplacementRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<AdPlacement> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

AdPlacement entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getCampaign() != null) {
        entity.setCampaign(null);
        }
    


repository.delete(entity);
return true;
}
}