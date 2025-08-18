package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.repository.EventTicketTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;

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

    public EventTicketTypeService(EventTicketTypeRepository repository,TicketRepository ticketsRepository)
    {
        super(repository);
        this.eventtickettypeRepository = repository;
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public EventTicketType save(EventTicketType eventtickettype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (eventtickettype.getTickets() != null) {
            List<Ticket> managedTickets = new ArrayList<>();
            for (Ticket item : eventtickettype.getTickets()) {
            if (item.getId() != null) {
            Ticket existingItem = ticketsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Ticket not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setType(eventtickettype);
            managedTickets.add(existingItem);
            } else {
            item.setType(eventtickettype);
            managedTickets.add(item);
            }
            }
            eventtickettype.setTickets(managedTickets);
            }
        
    

    

        return eventtickettypeRepository.save(eventtickettype);
    }


    public EventTicketType update(Long id, EventTicketType eventtickettypeRequest) {
        EventTicketType existing = eventtickettypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventTicketType not found"));

    // Copier les champs simples
        existing.setName(eventtickettypeRequest.getName());
        existing.setPrice(eventtickettypeRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getTickets().clear();

        if (eventtickettypeRequest.getTickets() != null) {
        for (var item : eventtickettypeRequest.getTickets()) {
        Ticket existingItem;
        if (item.getId() != null) {
        existingItem = ticketsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Ticket not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setType(existing);

        // Ajouter directement dans la collection existante
        existing.getTickets().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


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

    


repository.delete(entity);
return true;
}
}