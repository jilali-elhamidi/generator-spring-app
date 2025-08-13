package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SponsorService extends BaseService<Sponsor> {

    protected final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository repository)
    {
        super(repository);
        this.sponsorRepository = repository;
    }

    @Override
    public Sponsor save(Sponsor sponsor) {

        if (sponsor.getSponsoredEvents() != null) {
            for (LiveEvent item : sponsor.getSponsoredEvents()) {
            item.setSponsor(sponsor);
            }
        }

        if (sponsor.getSponsorships() != null) {
            for (EventSponsorship item : sponsor.getSponsorships()) {
            item.setSponsor(sponsor);
            }
        }

        if (sponsor.getAdCampaigns() != null) {
            for (AdCampaign item : sponsor.getAdCampaigns()) {
            item.setAdvertiser(sponsor);
            }
        }

        return sponsorRepository.save(sponsor);
    }


    public Sponsor update(Long id, Sponsor sponsorRequest) {
        Sponsor existing = sponsorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sponsor not found"));

    // Copier les champs simples
        existing.setName(sponsorRequest.getName());
        existing.setContactEmail(sponsorRequest.getContactEmail());
        existing.setCompanyType(sponsorRequest.getCompanyType());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getSponsoredEvents().clear();
        if (sponsorRequest.getSponsoredEvents() != null) {
            for (var item : sponsorRequest.getSponsoredEvents()) {
            item.setSponsor(existing);
            existing.getSponsoredEvents().add(item);
            }
        }

        existing.getSponsorships().clear();
        if (sponsorRequest.getSponsorships() != null) {
            for (var item : sponsorRequest.getSponsorships()) {
            item.setSponsor(existing);
            existing.getSponsorships().add(item);
            }
        }

        existing.getAdCampaigns().clear();
        if (sponsorRequest.getAdCampaigns() != null) {
            for (var item : sponsorRequest.getAdCampaigns()) {
            item.setAdvertiser(existing);
            existing.getAdCampaigns().add(item);
            }
        }

    

    

    


        return sponsorRepository.save(existing);
    }
}