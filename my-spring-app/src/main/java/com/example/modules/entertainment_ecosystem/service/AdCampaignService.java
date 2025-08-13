package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.repository.AdCampaignRepository;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.repository.StreamingPlatformRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class AdCampaignService extends BaseService<AdCampaign> {

    protected final AdCampaignRepository adcampaignRepository;
    private final SponsorRepository advertiserRepository;
    private final StreamingPlatformRepository displayedOnPlatformsRepository;

    public AdCampaignService(AdCampaignRepository repository,SponsorRepository advertiserRepository,StreamingPlatformRepository displayedOnPlatformsRepository)
    {
        super(repository);
        this.adcampaignRepository = repository;
        this.advertiserRepository = advertiserRepository;
        this.displayedOnPlatformsRepository = displayedOnPlatformsRepository;
    }

    @Override
    public AdCampaign save(AdCampaign adcampaign) {

        if (adcampaign.getAdvertiser() != null && adcampaign.getAdvertiser().getId() != null) {
        Sponsor advertiser = advertiserRepository.findById(adcampaign.getAdvertiser().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        adcampaign.setAdvertiser(advertiser);
        }

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

// Relations ManyToOne : mise à jour conditionnelle

        if (adcampaignRequest.getAdvertiser() != null && adcampaignRequest.getAdvertiser().getId() != null) {
        Sponsor advertiser = advertiserRepository.findById(adcampaignRequest.getAdvertiser().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        existing.setAdvertiser(advertiser);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (adcampaignRequest.getDisplayedOnPlatforms() != null) {
            existing.getDisplayedOnPlatforms().clear();
            List<StreamingPlatform> displayedOnPlatformsList = adcampaignRequest.getDisplayedOnPlatforms().stream()
                .map(item -> displayedOnPlatformsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("StreamingPlatform not found")))
                .collect(Collectors.toList());
        existing.getDisplayedOnPlatforms().addAll(displayedOnPlatformsList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    


        return adcampaignRepository.save(existing);
    }
}