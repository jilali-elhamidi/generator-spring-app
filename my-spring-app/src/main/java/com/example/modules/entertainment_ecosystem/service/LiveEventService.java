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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class LiveEventService extends BaseService<LiveEvent> {

    protected final LiveEventRepository liveeventRepository;
    private final ArtistRepository performersRepository;
    private final TicketRepository ticketsRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventLocationRepository locationRepository;
    private final SponsorRepository sponsorRepository;
    private final EventAudienceRepository audienceRepository;
    private final EventSponsorshipRepository sponsorshipsRepository;
    private final ContentTagRepository tagsRepository;
    private final ConcertVenueRepository venueRepository;
    private final TourRepository tourRepository;

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

            List<Artist> attachedPerformers = liveevent.getPerformers().stream()
            .map(item -> performersRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId())))
            .toList();

            liveevent.setPerformers(attachedPerformers);

            // côté propriétaire (Artist → LiveEvent)
            attachedPerformers.forEach(it -> it.getParticipatedInEvents().add(liveevent));
        }
        
        if (liveevent.getTags() != null &&
            !liveevent.getTags().isEmpty()) {

            List<ContentTag> attachedTags = liveevent.getTags().stream()
            .map(item -> tagsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("ContentTag not found with id " + item.getId())))
            .toList();

            liveevent.setTags(attachedTags);

            // côté propriétaire (ContentTag → LiveEvent)
            attachedTags.forEach(it -> it.getLiveEvents().add(liveevent));
        }
        
    // ---------- ManyToOne ----------
        if (liveevent.getEventType() != null &&
            liveevent.getEventType().getId() != null) {

            EventType existingEventType = eventTypeRepository.findById(
                liveevent.getEventType().getId()
            ).orElseThrow(() -> new RuntimeException("EventType not found"));

            liveevent.setEventType(existingEventType);
        }
        
        if (liveevent.getLocation() != null &&
            liveevent.getLocation().getId() != null) {

            EventLocation existingLocation = locationRepository.findById(
                liveevent.getLocation().getId()
            ).orElseThrow(() -> new RuntimeException("EventLocation not found"));

            liveevent.setLocation(existingLocation);
        }
        
        if (liveevent.getSponsor() != null &&
            liveevent.getSponsor().getId() != null) {

            Sponsor existingSponsor = sponsorRepository.findById(
                liveevent.getSponsor().getId()
            ).orElseThrow(() -> new RuntimeException("Sponsor not found"));

            liveevent.setSponsor(existingSponsor);
        }
        
        if (liveevent.getVenue() != null &&
            liveevent.getVenue().getId() != null) {

            ConcertVenue existingVenue = venueRepository.findById(
                liveevent.getVenue().getId()
            ).orElseThrow(() -> new RuntimeException("ConcertVenue not found"));

            liveevent.setVenue(existingVenue);
        }
        
        if (liveevent.getTour() != null &&
            liveevent.getTour().getId() != null) {

            Tour existingTour = tourRepository.findById(
                liveevent.getTour().getId()
            ).orElseThrow(() -> new RuntimeException("Tour not found"));

            liveevent.setTour(existingTour);
        }
        
    // ---------- OneToOne ----------
        if (liveevent.getAudience() != null) {
            
            
                // Vérifier si l'entité est déjà persistée
            liveevent.setAudience(
                audienceRepository.findById(liveevent.getAudience().getId())
                    .orElseThrow(() -> new RuntimeException("audience not found"))
            );
            
            liveevent.getAudience().setEvent(liveevent);
        }
        

    return liveeventRepository.save(liveevent);
}


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
        
    // ---------- Relations ManyToOne ----------
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
            if (liveeventRequest.getAudience() != null &&
            liveeventRequest.getAudience().getId() != null) {

            EventAudience audience = audienceRepository.findById(
                liveeventRequest.getAudience().getId()
            ).orElseThrow(() -> new RuntimeException("EventAudience not found"));

            existing.setAudience(audience);

            
            audience.setEvent(existing);
            
        }
        

    return liveeventRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<LiveEvent> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        LiveEvent entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getTickets() != null) {
            for (var child : entity.getTickets()) {
                
                child.setEvent(null); // retirer la référence inverse
                
            }
            entity.getTickets().clear();
        }
        
        if (entity.getSponsorships() != null) {
            for (var child : entity.getSponsorships()) {
                
                child.setEvent(null); // retirer la référence inverse
                
            }
            entity.getSponsorships().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getPerformers() != null) {
            for (Artist item : new ArrayList<>(entity.getPerformers())) {
                
                item.getParticipatedInEvents().remove(entity); // retire côté inverse
                
            }
            entity.getPerformers().clear(); // puis vide côté courant
        }
        
        if (entity.getTags() != null) {
            for (ContentTag item : new ArrayList<>(entity.getTags())) {
                
                item.getLiveEvents().remove(entity); // retire côté inverse
                
            }
            entity.getTags().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getAudience() != null) {
            entity.getAudience().setEvent(null);
            entity.setAudience(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getEventType() != null) {
            entity.setEventType(null);
        }
        
        if (entity.getLocation() != null) {
            entity.setLocation(null);
        }
        
        if (entity.getSponsor() != null) {
            entity.setSponsor(null);
        }
        
        if (entity.getVenue() != null) {
            entity.setVenue(null);
        }
        
        if (entity.getTour() != null) {
            entity.setTour(null);
        }
        
        repository.delete(entity);
        return true;
    }
}