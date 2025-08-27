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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EventSponsorshipService extends BaseService<EventSponsorship> {

    protected final EventSponsorshipRepository eventsponsorshipRepository;
    
    protected final LiveEventRepository eventRepository;
    
    protected final SponsorRepository sponsorRepository;
    
    protected final ContractRepository contractRepository;
    

    public EventSponsorshipService(EventSponsorshipRepository repository, LiveEventRepository eventRepository, SponsorRepository sponsorRepository, ContractRepository contractRepository)
    {
        super(repository);
        this.eventsponsorshipRepository = repository;
        
        this.eventRepository = eventRepository;
        
        this.sponsorRepository = sponsorRepository;
        
        this.contractRepository = contractRepository;
        
    }

    @Transactional
    @Override
    public EventSponsorship save(EventSponsorship eventsponsorship) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (eventsponsorship.getEvent() != null) {
            if (eventsponsorship.getEvent().getId() != null) {
                LiveEvent existingEvent = eventRepository.findById(
                    eventsponsorship.getEvent().getId()
                ).orElseThrow(() -> new RuntimeException("LiveEvent not found with id "
                    + eventsponsorship.getEvent().getId()));
                eventsponsorship.setEvent(existingEvent);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                LiveEvent newEvent = eventRepository.save(eventsponsorship.getEvent());
                eventsponsorship.setEvent(newEvent);
            }
        }
        
        if (eventsponsorship.getSponsor() != null) {
            if (eventsponsorship.getSponsor().getId() != null) {
                Sponsor existingSponsor = sponsorRepository.findById(
                    eventsponsorship.getSponsor().getId()
                ).orElseThrow(() -> new RuntimeException("Sponsor not found with id "
                    + eventsponsorship.getSponsor().getId()));
                eventsponsorship.setSponsor(existingSponsor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Sponsor newSponsor = sponsorRepository.save(eventsponsorship.getSponsor());
                eventsponsorship.setSponsor(newSponsor);
            }
        }
        
    // ---------- OneToOne ----------
        if (eventsponsorship.getContract() != null) {
            if (eventsponsorship.getContract().getId() != null) {
                Contract existingContract = contractRepository.findById(eventsponsorship.getContract().getId())
                    .orElseThrow(() -> new RuntimeException("Contract not found with id "
                        + eventsponsorship.getContract().getId()));
                eventsponsorship.setContract(existingContract);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Contract newContract = contractRepository.save(eventsponsorship.getContract());
                eventsponsorship.setContract(newContract);
            }

            eventsponsorship.getContract().setSponsorship(eventsponsorship);
        }
        
    return eventsponsorshipRepository.save(eventsponsorship);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (eventsponsorshipRequest.getContract() != null &&eventsponsorshipRequest.getContract().getId() != null) {

        Contract contract = contractRepository.findById(eventsponsorshipRequest.getContract().getId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        existing.setContract(contract);
        contract.setSponsorship(existing);
        }
    
    return eventsponsorshipRepository.save(existing);
}

    // Pagination simple
    public Page<EventSponsorship> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<EventSponsorship> search(Map<String, String> filters, Pageable pageable) {
        return super.search(EventSponsorship.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<EventSponsorship> saveAll(List<EventSponsorship> eventsponsorshipList) {
        return super.saveAll(eventsponsorshipList);
    }

}