package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.repository.AdPlacementRepository;

import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.repository.AdCampaignRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AdPlacementService extends BaseService<AdPlacement> {

    protected final AdPlacementRepository adplacementRepository;
    
    protected final AdCampaignRepository campaignRepository;
    

    public AdPlacementService(AdPlacementRepository repository, AdCampaignRepository campaignRepository)
    {
        super(repository);
        this.adplacementRepository = repository;
        
        this.campaignRepository = campaignRepository;
        
    }

    @Transactional
    @Override
    public AdPlacement save(AdPlacement adplacement) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (adplacement.getCampaign() != null) {
            if (adplacement.getCampaign().getId() != null) {
                AdCampaign existingCampaign = campaignRepository.findById(
                    adplacement.getCampaign().getId()
                ).orElseThrow(() -> new RuntimeException("AdCampaign not found with id "
                    + adplacement.getCampaign().getId()));
                adplacement.setCampaign(existingCampaign);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                AdCampaign newCampaign = campaignRepository.save(adplacement.getCampaign());
                adplacement.setCampaign(newCampaign);
            }
        }
        
    // ---------- OneToOne ----------
    return adplacementRepository.save(adplacement);
}

    @Transactional
    @Override
    public AdPlacement update(Long id, AdPlacement adplacementRequest) {
        AdPlacement existing = adplacementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AdPlacement not found"));

    // Copier les champs simples
        existing.setName(adplacementRequest.getName());
        existing.setLocation(adplacementRequest.getLocation());
        existing.setAdType(adplacementRequest.getAdType());

    // ---------- Relations ManyToOne ----------
        if (adplacementRequest.getCampaign() != null &&
            adplacementRequest.getCampaign().getId() != null) {

            AdCampaign existingCampaign = campaignRepository.findById(
                adplacementRequest.getCampaign().getId()
            ).orElseThrow(() -> new RuntimeException("AdCampaign not found"));

            existing.setCampaign(existingCampaign);
        } else {
            existing.setCampaign(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return adplacementRepository.save(existing);
}

    // Pagination simple
    public Page<AdPlacement> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<AdPlacement> search(Map<String, String> filters, Pageable pageable) {
        return super.search(AdPlacement.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<AdPlacement> saveAll(List<AdPlacement> adplacementList) {
        return super.saveAll(adplacementList);
    }

}