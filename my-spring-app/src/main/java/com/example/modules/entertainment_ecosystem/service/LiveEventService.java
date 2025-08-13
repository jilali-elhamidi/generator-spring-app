package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.repository.SponsorRepository;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.repository.EventAudienceRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class LiveEventService extends BaseService<LiveEvent> {

    protected final LiveEventRepository liveeventRepository;
    private final ArtistRepository performersRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventLocationRepository locationRepository;
    private final SponsorRepository sponsorRepository;
    private final EventAudienceRepository audienceRepository;

    public LiveEventService(LiveEventRepository repository,ArtistRepository performersRepository,EventTypeRepository eventTypeRepository,EventLocationRepository locationRepository,SponsorRepository sponsorRepository,EventAudienceRepository audienceRepository)
    {
        super(repository);
        this.liveeventRepository = repository;
        this.performersRepository = performersRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.locationRepository = locationRepository;
        this.sponsorRepository = sponsorRepository;
            this.audienceRepository = audienceRepository;
    }

    @Override
    public LiveEvent save(LiveEvent liveevent) {

        if (liveevent.getEventType() != null && liveevent.getEventType().getId() != null) {
        EventType eventType = eventTypeRepository.findById(liveevent.getEventType().getId())
                .orElseThrow(() -> new RuntimeException("EventType not found"));
        liveevent.setEventType(eventType);
        }

        if (liveevent.getLocation() != null && liveevent.getLocation().getId() != null) {
        EventLocation location = locationRepository.findById(liveevent.getLocation().getId())
                .orElseThrow(() -> new RuntimeException("EventLocation not found"));
        liveevent.setLocation(location);
        }

        if (liveevent.getSponsor() != null && liveevent.getSponsor().getId() != null) {
        Sponsor sponsor = sponsorRepository.findById(liveevent.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        liveevent.setSponsor(sponsor);
        }

        if (liveevent.getTickets() != null) {
            for (Ticket item : liveevent.getTickets()) {
            item.setEvent(liveevent);
            }
        }
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

// Relations ManyToOne : mise à jour conditionnelle

        if (liveeventRequest.getEventType() != null && liveeventRequest.getEventType().getId() != null) {
        EventType eventType = eventTypeRepository.findById(liveeventRequest.getEventType().getId())
                .orElseThrow(() -> new RuntimeException("EventType not found"));
        existing.setEventType(eventType);
        }

        if (liveeventRequest.getLocation() != null && liveeventRequest.getLocation().getId() != null) {
        EventLocation location = locationRepository.findById(liveeventRequest.getLocation().getId())
                .orElseThrow(() -> new RuntimeException("EventLocation not found"));
        existing.setLocation(location);
        }

        if (liveeventRequest.getSponsor() != null && liveeventRequest.getSponsor().getId() != null) {
        Sponsor sponsor = sponsorRepository.findById(liveeventRequest.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Sponsor not found"));
        existing.setSponsor(sponsor);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (liveeventRequest.getPerformers() != null) {
            existing.getPerformers().clear();
            List<Artist> performersList = liveeventRequest.getPerformers().stream()
                .map(item -> performersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());
        existing.getPerformers().addAll(performersList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getTickets().clear();
        if (liveeventRequest.getTickets() != null) {
            for (var item : liveeventRequest.getTickets()) {
            item.setEvent(existing);
            existing.getTickets().add(item);
            }
        }

    

    

    

    

    

    

        if (liveeventRequest.getAudience() != null
        && liveeventRequest.getAudience().getId() != null) {

        EventAudience audience = audienceRepository.findById(
        liveeventRequest.getAudience().getId()
        ).orElseThrow(() -> new RuntimeException("EventAudience not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setAudience(audience);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            audience.setEvent(existing);
        
        }

    


        return liveeventRepository.save(existing);
    }
}