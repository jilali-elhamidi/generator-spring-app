package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.repository.AdCampaignRepository;

import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;

import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;

import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.repository.AdPlacementRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AdCampaignService extends BaseService<AdCampaign> {

    protected final AdCampaignRepository adcampaignRepository;
    
    protected final SponsorRepository advertiserRepository;
    
    protected final StreamingPlatformRepository displayedOnPlatformsRepository;
    
    protected final AdPlacementRepository adPlacementsRepository;
    

    public AdCampaignService(AdCampaignRepository repository, SponsorRepository advertiserRepository, StreamingPlatformRepository displayedOnPlatformsRepository, AdPlacementRepository adPlacementsRepository)
    {
        super(repository);
        this.adcampaignRepository = repository;
        
        this.advertiserRepository = advertiserRepository;
        
        this.displayedOnPlatformsRepository = displayedOnPlatformsRepository;
        
        this.adPlacementsRepository = adPlacementsRepository;
        
    }

    @Transactional
    @Override
    public AdCampaign save(AdCampaign adcampaign) {
    // ---------- OneToMany ----------
        if (adcampaign.getAdPlacements() != null) {
            List<AdPlacement> managedAdPlacements = new ArrayList<>();
            for (AdPlacement item : adcampaign.getAdPlacements()) {
                if (item.getId() != null) {
                    AdPlacement existingItem = adPlacementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AdPlacement not found"));

                     existingItem.setCampaign(adcampaign);
                     managedAdPlacements.add(existingItem);
                } else {
                    item.setCampaign(adcampaign);
                    managedAdPlacements.add(item);
                }
            }
            adcampaign.setAdPlacements(managedAdPlacements);
        }
    
    // ---------- ManyToMany ----------
        if (adcampaign.getDisplayedOnPlatforms() != null &&
            !adcampaign.getDisplayedOnPlatforms().isEmpty()) {

            List<StreamingPlatform> attachedDisplayedOnPlatforms = new ArrayList<>();
            for (StreamingPlatform item : adcampaign.getDisplayedOnPlatforms()) {
                if (item.getId() != null) {
                    StreamingPlatform existingItem = displayedOnPlatformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingPlatform not found with id " + item.getId()));
                    attachedDisplayedOnPlatforms.add(existingItem);
                } else {

                    StreamingPlatform newItem = displayedOnPlatformsRepository.save(item);
                    attachedDisplayedOnPlatforms.add(newItem);
                }
            }

            adcampaign.setDisplayedOnPlatforms(attachedDisplayedOnPlatforms);

            // côté propriétaire (StreamingPlatform → AdCampaign)
            attachedDisplayedOnPlatforms.forEach(it -> it.getAdCampaigns().add(adcampaign));
        }
        
    // ---------- ManyToOne ----------
        if (adcampaign.getAdvertiser() != null) {
            if (adcampaign.getAdvertiser().getId() != null) {
                Sponsor existingAdvertiser = advertiserRepository.findById(
                    adcampaign.getAdvertiser().getId()
                ).orElseThrow(() -> new RuntimeException("Sponsor not found with id "
                    + adcampaign.getAdvertiser().getId()));
                adcampaign.setAdvertiser(existingAdvertiser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Sponsor newAdvertiser = advertiserRepository.save(adcampaign.getAdvertiser());
                adcampaign.setAdvertiser(newAdvertiser);
            }
        }
        
    // ---------- OneToOne ----------
    return adcampaignRepository.save(adcampaign);
}

    @Transactional
    @Override
    public AdCampaign update(Long id, AdCampaign adcampaignRequest) {
        AdCampaign existing = adcampaignRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AdCampaign not found"));

    // Copier les champs simples
        existing.setName(adcampaignRequest.getName());
        existing.setStartDate(adcampaignRequest.getStartDate());
        existing.setEndDate(adcampaignRequest.getEndDate());
        existing.setBudget(adcampaignRequest.getBudget());

    // ---------- Relations ManyToOne ----------
        if (adcampaignRequest.getAdvertiser() != null &&
            adcampaignRequest.getAdvertiser().getId() != null) {

            Sponsor existingAdvertiser = advertiserRepository.findById(
                adcampaignRequest.getAdvertiser().getId()
            ).orElseThrow(() -> new RuntimeException("Sponsor not found"));

            existing.setAdvertiser(existingAdvertiser);
        } else {
            existing.setAdvertiser(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (adcampaignRequest.getDisplayedOnPlatforms() != null) {
            existing.getDisplayedOnPlatforms().clear();

            List<StreamingPlatform> displayedOnPlatformsList = adcampaignRequest.getDisplayedOnPlatforms().stream()
                .map(item -> displayedOnPlatformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("StreamingPlatform not found")))
                .collect(Collectors.toList());

            existing.getDisplayedOnPlatforms().addAll(displayedOnPlatformsList);

            // Mettre à jour le côté inverse
            displayedOnPlatformsList.forEach(it -> {
                if (!it.getAdCampaigns().contains(existing)) {
                    it.getAdCampaigns().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getAdPlacements().clear();

        if (adcampaignRequest.getAdPlacements() != null) {
            for (var item : adcampaignRequest.getAdPlacements()) {
                AdPlacement existingItem;
                if (item.getId() != null) {
                    existingItem = adPlacementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AdPlacement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCampaign(existing);
                existing.getAdPlacements().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return adcampaignRepository.save(existing);
}

    // Pagination simple
    public Page<AdCampaign> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<AdCampaign> search(Map<String, String> filters, Pageable pageable) {
        return super.search(AdCampaign.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<AdCampaign> saveAll(List<AdCampaign> adcampaignList) {
        return super.saveAll(adcampaignList);
    }

}