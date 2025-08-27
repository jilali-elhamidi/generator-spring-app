package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.repository.EventSponsorshipRepository;

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
public class SponsorService extends BaseService<Sponsor> {

    protected final SponsorRepository sponsorRepository;
    
    protected final LiveEventRepository sponsoredEventsRepository;
    
    protected final EventSponsorshipRepository sponsorshipsRepository;
    
    protected final AdCampaignRepository adCampaignsRepository;
    

    public SponsorService(SponsorRepository repository, LiveEventRepository sponsoredEventsRepository, EventSponsorshipRepository sponsorshipsRepository, AdCampaignRepository adCampaignsRepository)
    {
        super(repository);
        this.sponsorRepository = repository;
        
        this.sponsoredEventsRepository = sponsoredEventsRepository;
        
        this.sponsorshipsRepository = sponsorshipsRepository;
        
        this.adCampaignsRepository = adCampaignsRepository;
        
    }

    @Transactional
    @Override
    public Sponsor save(Sponsor sponsor) {
    // ---------- OneToMany ----------
        if (sponsor.getSponsoredEvents() != null) {
            List<LiveEvent> managedSponsoredEvents = new ArrayList<>();
            for (LiveEvent item : sponsor.getSponsoredEvents()) {
                if (item.getId() != null) {
                    LiveEvent existingItem = sponsoredEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

                     existingItem.setSponsor(sponsor);
                     managedSponsoredEvents.add(existingItem);
                } else {
                    item.setSponsor(sponsor);
                    managedSponsoredEvents.add(item);
                }
            }
            sponsor.setSponsoredEvents(managedSponsoredEvents);
        }
    
        if (sponsor.getSponsorships() != null) {
            List<EventSponsorship> managedSponsorships = new ArrayList<>();
            for (EventSponsorship item : sponsor.getSponsorships()) {
                if (item.getId() != null) {
                    EventSponsorship existingItem = sponsorshipsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));

                     existingItem.setSponsor(sponsor);
                     managedSponsorships.add(existingItem);
                } else {
                    item.setSponsor(sponsor);
                    managedSponsorships.add(item);
                }
            }
            sponsor.setSponsorships(managedSponsorships);
        }
    
        if (sponsor.getAdCampaigns() != null) {
            List<AdCampaign> managedAdCampaigns = new ArrayList<>();
            for (AdCampaign item : sponsor.getAdCampaigns()) {
                if (item.getId() != null) {
                    AdCampaign existingItem = adCampaignsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AdCampaign not found"));

                     existingItem.setAdvertiser(sponsor);
                     managedAdCampaigns.add(existingItem);
                } else {
                    item.setAdvertiser(sponsor);
                    managedAdCampaigns.add(item);
                }
            }
            sponsor.setAdCampaigns(managedAdCampaigns);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return sponsorRepository.save(sponsor);
}

    @Transactional
    @Override
    public Sponsor update(Long id, Sponsor sponsorRequest) {
        Sponsor existing = sponsorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sponsor not found"));

    // Copier les champs simples
        existing.setName(sponsorRequest.getName());
        existing.setContactEmail(sponsorRequest.getContactEmail());
        existing.setCompanyType(sponsorRequest.getCompanyType());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getSponsoredEvents().clear();

        if (sponsorRequest.getSponsoredEvents() != null) {
            for (var item : sponsorRequest.getSponsoredEvents()) {
                LiveEvent existingItem;
                if (item.getId() != null) {
                    existingItem = sponsoredEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSponsor(existing);
                existing.getSponsoredEvents().add(existingItem);
            }
        }
        
        existing.getSponsorships().clear();

        if (sponsorRequest.getSponsorships() != null) {
            for (var item : sponsorRequest.getSponsorships()) {
                EventSponsorship existingItem;
                if (item.getId() != null) {
                    existingItem = sponsorshipsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSponsor(existing);
                existing.getSponsorships().add(existingItem);
            }
        }
        
        existing.getAdCampaigns().clear();

        if (sponsorRequest.getAdCampaigns() != null) {
            for (var item : sponsorRequest.getAdCampaigns()) {
                AdCampaign existingItem;
                if (item.getId() != null) {
                    existingItem = adCampaignsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("AdCampaign not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAdvertiser(existing);
                existing.getAdCampaigns().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return sponsorRepository.save(existing);
}

    // Pagination simple
    public Page<Sponsor> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Sponsor> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Sponsor.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Sponsor> saveAll(List<Sponsor> sponsorList) {
        return super.saveAll(sponsorList);
    }

}