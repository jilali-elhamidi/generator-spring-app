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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AdCampaignService extends BaseService<AdCampaign> {

    protected final AdCampaignRepository adcampaignRepository;
    private final SponsorRepository advertiserRepository;
    private final StreamingPlatformRepository displayedOnPlatformsRepository;
    private final AdPlacementRepository adPlacementsRepository;

    public AdCampaignService(AdCampaignRepository repository, SponsorRepository advertiserRepository, StreamingPlatformRepository displayedOnPlatformsRepository, AdPlacementRepository adPlacementsRepository)
    {
        super(repository);
        this.adcampaignRepository = repository;
        this.advertiserRepository = advertiserRepository;
        this.displayedOnPlatformsRepository = displayedOnPlatformsRepository;
        this.adPlacementsRepository = adPlacementsRepository;
    }

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
        
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<AdCampaign> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        AdCampaign entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getAdPlacements() != null) {
            for (var child : entity.getAdPlacements()) {
                // retirer la référence inverse
                child.setCampaign(null);
            }
            entity.getAdPlacements().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getDisplayedOnPlatforms() != null) {
            for (StreamingPlatform item : new ArrayList<>(entity.getDisplayedOnPlatforms())) {
                
                item.getAdCampaigns().remove(entity); // retire côté inverse
            }
            entity.getDisplayedOnPlatforms().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAdvertiser() != null) {
            entity.setAdvertiser(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<AdCampaign> saveAll(List<AdCampaign> adcampaignList) {

        return adcampaignRepository.saveAll(adcampaignList);
    }

}