package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.repository.EventTicketTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventTicketTypeService extends BaseService<EventTicketType> {

    protected final EventTicketTypeRepository eventtickettypeRepository;

    public EventTicketTypeService(EventTicketTypeRepository repository)
    {
        super(repository);
        this.eventtickettypeRepository = repository;
    }

    @Override
    public EventTicketType save(EventTicketType eventtickettype) {

        if (eventtickettype.getTickets() != null) {
            for (Ticket item : eventtickettype.getTickets()) {
            item.setType(eventtickettype);
            }
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

        existing.getTickets().clear();
        if (eventtickettypeRequest.getTickets() != null) {
            for (var item : eventtickettypeRequest.getTickets()) {
            item.setType(existing);
            existing.getTickets().add(item);
            }
        }

    


        return eventtickettypeRepository.save(existing);
    }
}