package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.repository.EventSponsorshipRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;
import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.repository.ContractRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventSponsorshipService extends BaseService<EventSponsorship> {

    protected final EventSponsorshipRepository eventsponsorshipRepository;
    private final LiveEventRepository eventRepository;
    private final SponsorRepository sponsorRepository;
    private final ContractRepository contractRepository;

    public EventSponsorshipService(EventSponsorshipRepository repository,LiveEventRepository eventRepository,SponsorRepository sponsorRepository,ContractRepository contractRepository)
    {
        super(repository);
        this.eventsponsorshipRepository = repository;
        this.eventRepository = eventRepository;
        this.sponsorRepository = sponsorRepository;
            this.contractRepository = contractRepository;
    }

    @Override
    public EventSponsorship save(EventSponsorship eventsponsorship) {

        if (eventsponsorship.getEvent() != null && eventsponsorship.getEvent().getId() != null) {
        LiveEvent event = eventRepository.findById(eventsponsorship.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        eventsponsorship.setEvent(event);
        }

        if (eventsponsorship.getSponsor() != null && eventsponsorship.getSponsor().getId() != null) {
        Sponsor sponsor = sponsorRepository.findById(eventsponsorship.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        eventsponsorship.setSponsor(sponsor);
        }
        if (eventsponsorship.getContract() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            eventsponsorship.setContract(
            contractRepository.findById(eventsponsorship.getContract().getId())
            .orElseThrow(() -> new RuntimeException("contract not found"))
            );
        
        eventsponsorship.getContract().setSponsorship(eventsponsorship);
        }

        return eventsponsorshipRepository.save(eventsponsorship);
    }


    public EventSponsorship update(Long id, EventSponsorship eventsponsorshipRequest) {
        EventSponsorship existing = eventsponsorshipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));

    // Copier les champs simples
        existing.setAmount(eventsponsorshipRequest.getAmount());
        existing.setStartDate(eventsponsorshipRequest.getStartDate());
        existing.setEndDate(eventsponsorshipRequest.getEndDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (eventsponsorshipRequest.getEvent() != null && eventsponsorshipRequest.getEvent().getId() != null) {
        LiveEvent event = eventRepository.findById(eventsponsorshipRequest.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        existing.setEvent(event);
        }

        if (eventsponsorshipRequest.getSponsor() != null && eventsponsorshipRequest.getSponsor().getId() != null) {
        Sponsor sponsor = sponsorRepository.findById(eventsponsorshipRequest.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        existing.setSponsor(sponsor);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

        if (eventsponsorshipRequest.getContract() != null
        && eventsponsorshipRequest.getContract().getId() != null) {

        Contract contract = contractRepository.findById(
        eventsponsorshipRequest.getContract().getId()
        ).orElseThrow(() -> new RuntimeException("Contract not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setContract(contract);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            contract.setSponsorship(existing);
        
        }

    


        return eventsponsorshipRepository.save(existing);
    }
}