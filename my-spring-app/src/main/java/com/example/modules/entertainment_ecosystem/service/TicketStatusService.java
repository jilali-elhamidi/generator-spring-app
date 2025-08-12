package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.repository.TicketStatusRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TicketStatusService extends BaseService<TicketStatus> {

    protected final TicketStatusRepository ticketstatusRepository;

    public TicketStatusService(TicketStatusRepository repository)
    {
        super(repository);
        this.ticketstatusRepository = repository;
    }

    @Override
    public TicketStatus save(TicketStatus ticketstatus) {

        if (ticketstatus.getTickets() != null) {
            for (Ticket item : ticketstatus.getTickets()) {
            item.setStatus(ticketstatus);
            }
        }

        return ticketstatusRepository.save(ticketstatus);
    }


    public TicketStatus update(Long id, TicketStatus ticketstatusRequest) {
        TicketStatus existing = ticketstatusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TicketStatus not found"));

    // Copier les champs simples
        existing.setName(ticketstatusRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getTickets().clear();
        if (ticketstatusRequest.getTickets() != null) {
            for (var item : ticketstatusRequest.getTickets()) {
            item.setStatus(existing);
            existing.getTickets().add(item);
            }
        }

        return ticketstatusRepository.save(existing);
    }
}