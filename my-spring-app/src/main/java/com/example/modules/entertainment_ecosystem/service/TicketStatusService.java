package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.repository.TicketStatusRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TicketStatusService extends BaseService<TicketStatus> {

    protected final TicketStatusRepository ticketstatusRepository;
    private final TicketRepository ticketsRepository;

    public TicketStatusService(TicketStatusRepository repository, TicketRepository ticketsRepository)
    {
        super(repository);
        this.ticketstatusRepository = repository;
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public TicketStatus save(TicketStatus ticketstatus) {
    // ---------- OneToMany ----------
        if (ticketstatus.getTickets() != null) {
            List<Ticket> managedTickets = new ArrayList<>();
            for (Ticket item : ticketstatus.getTickets()) {
                if (item.getId() != null) {
                    Ticket existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));

                     existingItem.setStatus(ticketstatus);
                     managedTickets.add(existingItem);
                } else {
                    item.setStatus(ticketstatus);
                    managedTickets.add(item);
                }
            }
            ticketstatus.setTickets(managedTickets);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return ticketstatusRepository.save(ticketstatus);
}


    public TicketStatus update(Long id, TicketStatus ticketstatusRequest) {
        TicketStatus existing = ticketstatusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TicketStatus not found"));

    // Copier les champs simples
        existing.setName(ticketstatusRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getTickets().clear();

        if (ticketstatusRequest.getTickets() != null) {
            for (var item : ticketstatusRequest.getTickets()) {
                Ticket existingItem;
                if (item.getId() != null) {
                    existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));
                } else {
                existingItem = item;
                }

                existingItem.setStatus(existing);
                existing.getTickets().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return ticketstatusRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<TicketStatus> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        TicketStatus entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getTickets() != null) {
            for (var child : entity.getTickets()) {
                
                child.setStatus(null); // retirer la référence inverse
                
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