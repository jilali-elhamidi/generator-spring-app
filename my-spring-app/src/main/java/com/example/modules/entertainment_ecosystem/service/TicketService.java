package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.repository.TicketStatusRepository;

import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;

import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.repository.EventTicketTypeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TicketService extends BaseService<Ticket> {

    protected final TicketRepository ticketRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final LiveEventRepository eventRepository;
    
    protected final TicketStatusRepository statusRepository;
    
    protected final BookingRepository bookingRepository;
    
    protected final EventTicketTypeRepository typeRepository;
    

    public TicketService(TicketRepository repository, UserProfileRepository userRepository, LiveEventRepository eventRepository, TicketStatusRepository statusRepository, BookingRepository bookingRepository, EventTicketTypeRepository typeRepository)
    {
        super(repository);
        this.ticketRepository = repository;
        
        this.userRepository = userRepository;
        
        this.eventRepository = eventRepository;
        
        this.statusRepository = statusRepository;
        
        this.bookingRepository = bookingRepository;
        
        this.typeRepository = typeRepository;
        
    }

    @Transactional
    @Override
    public Ticket save(Ticket ticket) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (ticket.getUser() != null) {
            if (ticket.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    ticket.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + ticket.getUser().getId()));
                ticket.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(ticket.getUser());
                ticket.setUser(newUser);
            }
        }
        
        if (ticket.getEvent() != null) {
            if (ticket.getEvent().getId() != null) {
                LiveEvent existingEvent = eventRepository.findById(
                    ticket.getEvent().getId()
                ).orElseThrow(() -> new RuntimeException("LiveEvent not found with id "
                    + ticket.getEvent().getId()));
                ticket.setEvent(existingEvent);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                LiveEvent newEvent = eventRepository.save(ticket.getEvent());
                ticket.setEvent(newEvent);
            }
        }
        
        if (ticket.getStatus() != null) {
            if (ticket.getStatus().getId() != null) {
                TicketStatus existingStatus = statusRepository.findById(
                    ticket.getStatus().getId()
                ).orElseThrow(() -> new RuntimeException("TicketStatus not found with id "
                    + ticket.getStatus().getId()));
                ticket.setStatus(existingStatus);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TicketStatus newStatus = statusRepository.save(ticket.getStatus());
                ticket.setStatus(newStatus);
            }
        }
        
        if (ticket.getBooking() != null) {
            if (ticket.getBooking().getId() != null) {
                Booking existingBooking = bookingRepository.findById(
                    ticket.getBooking().getId()
                ).orElseThrow(() -> new RuntimeException("Booking not found with id "
                    + ticket.getBooking().getId()));
                ticket.setBooking(existingBooking);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Booking newBooking = bookingRepository.save(ticket.getBooking());
                ticket.setBooking(newBooking);
            }
        }
        
        if (ticket.getType() != null) {
            if (ticket.getType().getId() != null) {
                EventTicketType existingType = typeRepository.findById(
                    ticket.getType().getId()
                ).orElseThrow(() -> new RuntimeException("EventTicketType not found with id "
                    + ticket.getType().getId()));
                ticket.setType(existingType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                EventTicketType newType = typeRepository.save(ticket.getType());
                ticket.setType(newType);
            }
        }
        
    // ---------- OneToOne ----------
    return ticketRepository.save(ticket);
}

    @Transactional
    @Override
    public Ticket update(Long id, Ticket ticketRequest) {
        Ticket existing = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // Copier les champs simples
        existing.setTicketNumber(ticketRequest.getTicketNumber());
        existing.setPrice(ticketRequest.getPrice());
        existing.setSeatInfo(ticketRequest.getSeatInfo());

    // ---------- Relations ManyToOne ----------
        if (ticketRequest.getUser() != null &&
            ticketRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                ticketRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (ticketRequest.getEvent() != null &&
            ticketRequest.getEvent().getId() != null) {

            LiveEvent existingEvent = eventRepository.findById(
                ticketRequest.getEvent().getId()
            ).orElseThrow(() -> new RuntimeException("LiveEvent not found"));

            existing.setEvent(existingEvent);
        } else {
            existing.setEvent(null);
        }
        
        if (ticketRequest.getStatus() != null &&
            ticketRequest.getStatus().getId() != null) {

            TicketStatus existingStatus = statusRepository.findById(
                ticketRequest.getStatus().getId()
            ).orElseThrow(() -> new RuntimeException("TicketStatus not found"));

            existing.setStatus(existingStatus);
        } else {
            existing.setStatus(null);
        }
        
        if (ticketRequest.getBooking() != null &&
            ticketRequest.getBooking().getId() != null) {

            Booking existingBooking = bookingRepository.findById(
                ticketRequest.getBooking().getId()
            ).orElseThrow(() -> new RuntimeException("Booking not found"));

            existing.setBooking(existingBooking);
        } else {
            existing.setBooking(null);
        }
        
        if (ticketRequest.getType() != null &&
            ticketRequest.getType().getId() != null) {

            EventTicketType existingType = typeRepository.findById(
                ticketRequest.getType().getId()
            ).orElseThrow(() -> new RuntimeException("EventTicketType not found"));

            existing.setType(existingType);
        } else {
            existing.setType(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return ticketRepository.save(existing);
}

    // Pagination simple
    public Page<Ticket> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Ticket> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Ticket.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Ticket> saveAll(List<Ticket> ticketList) {
        return super.saveAll(ticketList);
    }

}