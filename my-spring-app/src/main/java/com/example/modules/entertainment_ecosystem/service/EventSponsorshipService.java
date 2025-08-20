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
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventSponsorshipService extends BaseService<EventSponsorship> {

    protected final EventSponsorshipRepository eventsponsorshipRepository;
    private final LiveEventRepository eventRepository;
    private final SponsorRepository sponsorRepository;
    private final ContractRepository contractRepository;

    public EventSponsorshipService(EventSponsorshipRepository repository, LiveEventRepository eventRepository, SponsorRepository sponsorRepository, ContractRepository contractRepository)
    {
        super(repository);
        this.eventsponsorshipRepository = repository;
        this.eventRepository = eventRepository;
        this.sponsorRepository = sponsorRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public EventSponsorship save(EventSponsorship eventsponsorship) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (eventsponsorship.getEvent() != null &&
            eventsponsorship.getEvent().getId() != null) {

            LiveEvent existingEvent = eventRepository.findById(
                eventsponsorship.getEvent().getId()
            ).orElseThrow(() -> new RuntimeException("LiveEvent not found"));

            eventsponsorship.setEvent(existingEvent);
        }
        
        if (eventsponsorship.getSponsor() != null &&
            eventsponsorship.getSponsor().getId() != null) {

            Sponsor existingSponsor = sponsorRepository.findById(
                eventsponsorship.getSponsor().getId()
            ).orElseThrow(() -> new RuntimeException("Sponsor not found"));

            eventsponsorship.setSponsor(existingSponsor);
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
        if (eventsponsorshipRequest.getEvent() != null &&
            eventsponsorshipRequest.getEvent().getId() != null) {

            LiveEvent existingEvent = eventRepository.findById(
                eventsponsorshipRequest.getEvent().getId()
            ).orElseThrow(() -> new RuntimeException("LiveEvent not found"));

            existing.setEvent(existingEvent);
        } else {
            existing.setEvent(null);
        }
        
        if (eventsponsorshipRequest.getSponsor() != null &&
            eventsponsorshipRequest.getSponsor().getId() != null) {

            Sponsor existingSponsor = sponsorRepository.findById(
                eventsponsorshipRequest.getSponsor().getId()
            ).orElseThrow(() -> new RuntimeException("Sponsor not found"));

            existing.setSponsor(existingSponsor);
        } else {
            existing.setSponsor(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
            if (eventsponsorshipRequest.getContract() != null &&
            eventsponsorshipRequest.getContract().getId() != null) {

            Contract contract = contractRepository.findById(
                eventsponsorshipRequest.getContract().getId()
            ).orElseThrow(() -> new RuntimeException("Contract not found"));

            existing.setContract(contract);

            
            contract.setSponsorship(existing);
            
        }
        

    return eventsponsorshipRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EventSponsorship> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EventSponsorship entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getContract() != null) {
            entity.getContract().setSponsorship(null);
            entity.setContract(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getEvent() != null) {
            entity.setEvent(null);
        }
        
        if (entity.getSponsor() != null) {
            entity.setSponsor(null);
        }
        
        repository.delete(entity);
        return true;
    }
}