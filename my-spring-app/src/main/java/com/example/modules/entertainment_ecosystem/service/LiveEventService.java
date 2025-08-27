package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;

import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;

import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;

import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;

import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.repository.EventAudienceRepository;

import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.repository.EventSponsorshipRepository;

import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;

import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.repository.ConcertVenueRepository;

import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.repository.TourRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class LiveEventService extends BaseService<LiveEvent> {

    protected final LiveEventRepository liveeventRepository;
    
    protected final ArtistRepository performersRepository;
    
    protected final TicketRepository ticketsRepository;
    
    protected final EventTypeRepository eventTypeRepository;
    
    protected final EventLocationRepository locationRepository;
    
    protected final SponsorRepository sponsorRepository;
    
    protected final EventAudienceRepository audienceRepository;
    
    protected final EventSponsorshipRepository sponsorshipsRepository;
    
    protected final ContentTagRepository tagsRepository;
    
    protected final ConcertVenueRepository venueRepository;
    
    protected final TourRepository tourRepository;
    

    public LiveEventService(LiveEventRepository repository, ArtistRepository performersRepository, TicketRepository ticketsRepository, EventTypeRepository eventTypeRepository, EventLocationRepository locationRepository, SponsorRepository sponsorRepository, EventAudienceRepository audienceRepository, EventSponsorshipRepository sponsorshipsRepository, ContentTagRepository tagsRepository, ConcertVenueRepository venueRepository, TourRepository tourRepository)
    {
        super(repository);
        this.liveeventRepository = repository;
        
        this.performersRepository = performersRepository;
        
        this.ticketsRepository = ticketsRepository;
        
        this.eventTypeRepository = eventTypeRepository;
        
        this.locationRepository = locationRepository;
        
        this.sponsorRepository = sponsorRepository;
        
        this.audienceRepository = audienceRepository;
        
        this.sponsorshipsRepository = sponsorshipsRepository;
        
        this.tagsRepository = tagsRepository;
        
        this.venueRepository = venueRepository;
        
        this.tourRepository = tourRepository;
        
    }

    @Transactional
    @Override
    public LiveEvent save(LiveEvent liveevent) {
    // ---------- OneToMany ----------
        if (liveevent.getTickets() != null) {
            List<Ticket> managedTickets = new ArrayList<>();
            for (Ticket item : liveevent.getTickets()) {
                if (item.getId() != null) {
                    Ticket existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));

                     existingItem.setEvent(liveevent);
                     managedTickets.add(existingItem);
                } else {
                    item.setEvent(liveevent);
                    managedTickets.add(item);
                }
            }
            liveevent.setTickets(managedTickets);
        }
    
        if (liveevent.getSponsorships() != null) {
            List<EventSponsorship> managedSponsorships = new ArrayList<>();
            for (EventSponsorship item : liveevent.getSponsorships()) {
                if (item.getId() != null) {
                    EventSponsorship existingItem = sponsorshipsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));

                     existingItem.setEvent(liveevent);
                     managedSponsorships.add(existingItem);
                } else {
                    item.setEvent(liveevent);
                    managedSponsorships.add(item);
                }
            }
            liveevent.setSponsorships(managedSponsorships);
        }
    
    // ---------- ManyToMany ----------
        if (liveevent.getPerformers() != null &&
            !liveevent.getPerformers().isEmpty()) {

            List<Artist> attachedPerformers = new ArrayList<>();
            for (Artist item : liveevent.getPerformers()) {
                if (item.getId() != null) {
                    Artist existingItem = performersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId()));
                    attachedPerformers.add(existingItem);
                } else {

                    Artist newItem = performersRepository.save(item);
                    attachedPerformers.add(newItem);
                }
            }

            liveevent.setPerformers(attachedPerformers);

            // côté propriétaire (Artist → LiveEvent)
            attachedPerformers.forEach(it -> it.getParticipatedInEvents().add(liveevent));
        }
        
        if (liveevent.getTags() != null &&
            !liveevent.getTags().isEmpty()) {

            List<ContentTag> attachedTags = new ArrayList<>();
            for (ContentTag item : liveevent.getTags()) {
                if (item.getId() != null) {
                    ContentTag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentTag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    ContentTag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            liveevent.setTags(attachedTags);

            // côté propriétaire (ContentTag → LiveEvent)
            attachedTags.forEach(it -> it.getLiveEvents().add(liveevent));
        }
        
    // ---------- ManyToOne ----------
        if (liveevent.getEventType() != null) {
            if (liveevent.getEventType().getId() != null) {
                EventType existingEventType = eventTypeRepository.findById(
                    liveevent.getEventType().getId()
                ).orElseThrow(() -> new RuntimeException("EventType not found with id "
                    + liveevent.getEventType().getId()));
                liveevent.setEventType(existingEventType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                EventType newEventType = eventTypeRepository.save(liveevent.getEventType());
                liveevent.setEventType(newEventType);
            }
        }
        
        if (liveevent.getLocation() != null) {
            if (liveevent.getLocation().getId() != null) {
                EventLocation existingLocation = locationRepository.findById(
                    liveevent.getLocation().getId()
                ).orElseThrow(() -> new RuntimeException("EventLocation not found with id "
                    + liveevent.getLocation().getId()));
                liveevent.setLocation(existingLocation);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                EventLocation newLocation = locationRepository.save(liveevent.getLocation());
                liveevent.setLocation(newLocation);
            }
        }
        
        if (liveevent.getSponsor() != null) {
            if (liveevent.getSponsor().getId() != null) {
                Sponsor existingSponsor = sponsorRepository.findById(
                    liveevent.getSponsor().getId()
                ).orElseThrow(() -> new RuntimeException("Sponsor not found with id "
                    + liveevent.getSponsor().getId()));
                liveevent.setSponsor(existingSponsor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Sponsor newSponsor = sponsorRepository.save(liveevent.getSponsor());
                liveevent.setSponsor(newSponsor);
            }
        }
        
        if (liveevent.getVenue() != null) {
            if (liveevent.getVenue().getId() != null) {
                ConcertVenue existingVenue = venueRepository.findById(
                    liveevent.getVenue().getId()
                ).orElseThrow(() -> new RuntimeException("ConcertVenue not found with id "
                    + liveevent.getVenue().getId()));
                liveevent.setVenue(existingVenue);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ConcertVenue newVenue = venueRepository.save(liveevent.getVenue());
                liveevent.setVenue(newVenue);
            }
        }
        
        if (liveevent.getTour() != null) {
            if (liveevent.getTour().getId() != null) {
                Tour existingTour = tourRepository.findById(
                    liveevent.getTour().getId()
                ).orElseThrow(() -> new RuntimeException("Tour not found with id "
                    + liveevent.getTour().getId()));
                liveevent.setTour(existingTour);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Tour newTour = tourRepository.save(liveevent.getTour());
                liveevent.setTour(newTour);
            }
        }
        
    // ---------- OneToOne ----------
        if (liveevent.getAudience() != null) {
            if (liveevent.getAudience().getId() != null) {
                EventAudience existingAudience = audienceRepository.findById(liveevent.getAudience().getId())
                    .orElseThrow(() -> new RuntimeException("EventAudience not found with id "
                        + liveevent.getAudience().getId()));
                liveevent.setAudience(existingAudience);
            } else {
                // Nouvel objet → sauvegarde d'abord
                EventAudience newAudience = audienceRepository.save(liveevent.getAudience());
                liveevent.setAudience(newAudience);
            }

            liveevent.getAudience().setEvent(liveevent);
        }
        
    return liveeventRepository.save(liveevent);
}

    @Transactional
    @Override
    public LiveEvent update(Long id, LiveEvent liveeventRequest) {
        LiveEvent existing = liveeventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

    // Copier les champs simples
        existing.setName(liveeventRequest.getName());
        existing.setEventDate(liveeventRequest.getEventDate());
        existing.setDescription(liveeventRequest.getDescription());

    // ---------- Relations ManyToOne ----------
        if (liveeventRequest.getEventType() != null &&
            liveeventRequest.getEventType().getId() != null) {

            EventType existingEventType = eventTypeRepository.findById(
                liveeventRequest.getEventType().getId()
            ).orElseThrow(() -> new RuntimeException("EventType not found"));

            existing.setEventType(existingEventType);
        } else {
            existing.setEventType(null);
        }
        
        if (liveeventRequest.getLocation() != null &&
            liveeventRequest.getLocation().getId() != null) {

            EventLocation existingLocation = locationRepository.findById(
                liveeventRequest.getLocation().getId()
            ).orElseThrow(() -> new RuntimeException("EventLocation not found"));

            existing.setLocation(existingLocation);
        } else {
            existing.setLocation(null);
        }
        
        if (liveeventRequest.getSponsor() != null &&
            liveeventRequest.getSponsor().getId() != null) {

            Sponsor existingSponsor = sponsorRepository.findById(
                liveeventRequest.getSponsor().getId()
            ).orElseThrow(() -> new RuntimeException("Sponsor not found"));

            existing.setSponsor(existingSponsor);
        } else {
            existing.setSponsor(null);
        }
        
        if (liveeventRequest.getVenue() != null &&
            liveeventRequest.getVenue().getId() != null) {

            ConcertVenue existingVenue = venueRepository.findById(
                liveeventRequest.getVenue().getId()
            ).orElseThrow(() -> new RuntimeException("ConcertVenue not found"));

            existing.setVenue(existingVenue);
        } else {
            existing.setVenue(null);
        }
        
        if (liveeventRequest.getTour() != null &&
            liveeventRequest.getTour().getId() != null) {

            Tour existingTour = tourRepository.findById(
                liveeventRequest.getTour().getId()
            ).orElseThrow(() -> new RuntimeException("Tour not found"));

            existing.setTour(existingTour);
        } else {
            existing.setTour(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (liveeventRequest.getPerformers() != null) {
            existing.getPerformers().clear();

            List<Artist> performersList = liveeventRequest.getPerformers().stream()
                .map(item -> performersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());

            existing.getPerformers().addAll(performersList);

            // Mettre à jour le côté inverse
            performersList.forEach(it -> {
                if (!it.getParticipatedInEvents().contains(existing)) {
                    it.getParticipatedInEvents().add(existing);
                }
            });
        }
        
        if (liveeventRequest.getTags() != null) {
            existing.getTags().clear();

            List<ContentTag> tagsList = liveeventRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ContentTag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getLiveEvents().contains(existing)) {
                    it.getLiveEvents().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getTickets().clear();

        if (liveeventRequest.getTickets() != null) {
            for (var item : liveeventRequest.getTickets()) {
                Ticket existingItem;
                if (item.getId() != null) {
                    existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));
                } else {
                existingItem = item;
                }

                existingItem.setEvent(existing);
                existing.getTickets().add(existingItem);
            }
        }
        
        existing.getSponsorships().clear();

        if (liveeventRequest.getSponsorships() != null) {
            for (var item : liveeventRequest.getSponsorships()) {
                EventSponsorship existingItem;
                if (item.getId() != null) {
                    existingItem = sponsorshipsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EventSponsorship not found"));
                } else {
                existingItem = item;
                }

                existingItem.setEvent(existing);
                existing.getSponsorships().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (liveeventRequest.getAudience() != null &&liveeventRequest.getAudience().getId() != null) {

        EventAudience audience = audienceRepository.findById(liveeventRequest.getAudience().getId())
                .orElseThrow(() -> new RuntimeException("EventAudience not found"));

        existing.setAudience(audience);
        audience.setEvent(existing);
        }
    
    return liveeventRepository.save(existing);
}

    // Pagination simple
    public Page<LiveEvent> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<LiveEvent> search(Map<String, String> filters, Pageable pageable) {
        return super.search(LiveEvent.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<LiveEvent> saveAll(List<LiveEvent> liveeventList) {
        return super.saveAll(liveeventList);
    }

}