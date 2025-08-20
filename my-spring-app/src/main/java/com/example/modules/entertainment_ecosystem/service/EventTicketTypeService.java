package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.repository.EventTicketTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;
import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.repository.SocialMediaPlatformRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventTicketTypeService extends BaseService<EventTicketType> {

    protected final EventTicketTypeRepository eventtickettypeRepository;
    private final TicketRepository ticketsRepository;
    private final SocialMediaPlatformRepository platformRepository;

    public EventTicketTypeService(EventTicketTypeRepository repository, TicketRepository ticketsRepository, SocialMediaPlatformRepository platformRepository)
    {
        super(repository);
        this.eventtickettypeRepository = repository;
        this.ticketsRepository = ticketsRepository;
        this.platformRepository = platformRepository;
    }

    @Override
    public EventTicketType save(EventTicketType eventtickettype) {
    // ---------- OneToMany ----------
        if (eventtickettype.getTickets() != null) {
            List<Ticket> managedTickets = new ArrayList<>();
            for (Ticket item : eventtickettype.getTickets()) {
                if (item.getId() != null) {
                    Ticket existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));

                     existingItem.setType(eventtickettype);
                     managedTickets.add(existingItem);
                } else {
                    item.setType(eventtickettype);
                    managedTickets.add(item);
                }
            }
            eventtickettype.setTickets(managedTickets);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (eventtickettype.getPlatform() != null &&
            eventtickettype.getPlatform().getId() != null) {

            SocialMediaPlatform existingPlatform = platformRepository.findById(
                eventtickettype.getPlatform().getId()
            ).orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found"));

            eventtickettype.setPlatform(existingPlatform);
        }
        
    // ---------- OneToOne ----------

    return eventtickettypeRepository.save(eventtickettype);
}


    public EventTicketType update(Long id, EventTicketType eventtickettypeRequest) {
        EventTicketType existing = eventtickettypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventTicketType not found"));

    // Copier les champs simples
        existing.setName(eventtickettypeRequest.getName());
        existing.setPrice(eventtickettypeRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (eventtickettypeRequest.getPlatform() != null &&
            eventtickettypeRequest.getPlatform().getId() != null) {

            SocialMediaPlatform existingPlatform = platformRepository.findById(
                eventtickettypeRequest.getPlatform().getId()
            ).orElseThrow(() -> new RuntimeException("SocialMediaPlatform not found"));

            existing.setPlatform(existingPlatform);
        } else {
            existing.setPlatform(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getTickets().clear();

        if (eventtickettypeRequest.getTickets() != null) {
            for (var item : eventtickettypeRequest.getTickets()) {
                Ticket existingItem;
                if (item.getId() != null) {
                    existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getTickets().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return eventtickettypeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EventTicketType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EventTicketType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getTickets() != null) {
            for (var child : entity.getTickets()) {
                
                child.setType(null); // retirer la référence inverse
                
            }
            entity.getTickets().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPlatform() != null) {
            entity.setPlatform(null);
        }
        
        repository.delete(entity);
        return true;
    }
}