package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.repository.TicketStatusRepository;

import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TicketStatusService extends BaseService<TicketStatus> {

    protected final TicketStatusRepository ticketstatusRepository;
    
    protected final TicketRepository ticketsRepository;
    

    public TicketStatusService(TicketStatusRepository repository, TicketRepository ticketsRepository)
    {
        super(repository);
        this.ticketstatusRepository = repository;
        
        this.ticketsRepository = ticketsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public TicketStatus update(Long id, TicketStatus ticketstatusRequest) {
        TicketStatus existing = ticketstatusRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TicketStatus not found"));

    // Copier les champs simples
        existing.setName(ticketstatusRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<TicketStatus> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TicketStatus> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TicketStatus.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TicketStatus> saveAll(List<TicketStatus> ticketstatusList) {
        return super.saveAll(ticketstatusList);
    }

}