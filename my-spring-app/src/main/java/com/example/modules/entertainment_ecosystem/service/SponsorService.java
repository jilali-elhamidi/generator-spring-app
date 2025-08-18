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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SponsorService extends BaseService<Sponsor> {

    protected final SponsorRepository sponsorRepository;
    private final LiveEventRepository sponsoredEventsRepository;
    private final EventSponsorshipRepository sponsorshipsRepository;
    private final AdCampaignRepository adCampaignsRepository;

    public SponsorService(SponsorRepository repository,LiveEventRepository sponsoredEventsRepository,EventSponsorshipRepository sponsorshipsRepository,AdCampaignRepository adCampaignsRepository)
    {
        super(repository);
        this.sponsorRepository = repository;
        this.sponsoredEventsRepository = sponsoredEventsRepository;
        this.sponsorshipsRepository = sponsorshipsRepository;
        this.adCampaignsRepository = adCampaignsRepository;
    }

    @Override
    public Sponsor save(Sponsor sponsor) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (sponsor.getSponsoredEvents() != null) {
            List<LiveEvent> managedSponsoredEvents = new ArrayList<>();
            for (LiveEvent item : sponsor.getSponsoredEvents()) {
            if (item.getId() != null) {
            LiveEvent existingItem = sponsoredEventsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSponsor(sponsor);
            managedSponsoredEvents.add(existingItem);
            } else {
            item.setSponsor(sponsor);
            managedSponsoredEvents.add(item);
            }
            }
            sponsor.setSponsoredEvents(managedSponsoredEvents);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (sponsor.getSponsorships() != null) {
            List<EventSponsorship> managedSponsorships = new ArrayList<>();
            for (EventSponsorship item : sponsor.getSponsorships()) {
            if (item.getId() != null) {
            EventSponsorship existingItem = sponsorshipsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSponsor(sponsor);
            managedSponsorships.add(existingItem);
            } else {
            item.setSponsor(sponsor);
            managedSponsorships.add(item);
            }
            }
            sponsor.setSponsorships(managedSponsorships);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (sponsor.getAdCampaigns() != null) {
            List<AdCampaign> managedAdCampaigns = new ArrayList<>();
            for (AdCampaign item : sponsor.getAdCampaigns()) {
            if (item.getId() != null) {
            AdCampaign existingItem = adCampaignsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("AdCampaign not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAdvertiser(sponsor);
            managedAdCampaigns.add(existingItem);
            } else {
            item.setAdvertiser(sponsor);
            managedAdCampaigns.add(item);
            }
            }
            sponsor.setAdCampaigns(managedAdCampaigns);
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
        // Vider la collection existante
        existing.getSponsoredEvents().clear();

        if (sponsorRequest.getSponsoredEvents() != null) {
        for (var item : sponsorRequest.getSponsoredEvents()) {
        LiveEvent existingItem;
        if (item.getId() != null) {
        existingItem = sponsoredEventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSponsor(existing);

        // Ajouter directement dans la collection existante
        existing.getSponsoredEvents().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSponsorships().clear();

        if (sponsorRequest.getSponsorships() != null) {
        for (var item : sponsorRequest.getSponsorships()) {
        EventSponsorship existingItem;
        if (item.getId() != null) {
        existingItem = sponsorshipsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSponsor(existing);

        // Ajouter directement dans la collection existante
        existing.getSponsorships().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getAdCampaigns().clear();

        if (sponsorRequest.getAdCampaigns() != null) {
        for (var item : sponsorRequest.getAdCampaigns()) {
        AdCampaign existingItem;
        if (item.getId() != null) {
        existingItem = adCampaignsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("AdCampaign not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAdvertiser(existing);

        // Ajouter directement dans la collection existante
        existing.getAdCampaigns().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


        return sponsorRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Sponsor> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Sponsor entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getSponsoredEvents() != null) {
        for (var child : entity.getSponsoredEvents()) {
        
            child.setSponsor(null); // retirer la référence inverse
        
        }
        entity.getSponsoredEvents().clear();
        }
    

    
        if (entity.getSponsorships() != null) {
        for (var child : entity.getSponsorships()) {
        
            child.setSponsor(null); // retirer la référence inverse
        
        }
        entity.getSponsorships().clear();
        }
    

    
        if (entity.getAdCampaigns() != null) {
        for (var child : entity.getAdCampaigns()) {
        
            child.setAdvertiser(null); // retirer la référence inverse
        
        }
        entity.getAdCampaigns().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    

    

    


repository.delete(entity);
return true;
}
}