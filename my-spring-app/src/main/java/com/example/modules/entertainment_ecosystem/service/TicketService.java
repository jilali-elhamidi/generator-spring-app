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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TicketService extends BaseService<Ticket> {

    protected final TicketRepository ticketRepository;
    private final UserProfileRepository userRepository;
    private final LiveEventRepository eventRepository;
    private final TicketStatusRepository statusRepository;
    private final BookingRepository bookingRepository;
    private final EventTicketTypeRepository typeRepository;

    public TicketService(TicketRepository repository,UserProfileRepository userRepository,LiveEventRepository eventRepository,TicketStatusRepository statusRepository,BookingRepository bookingRepository,EventTicketTypeRepository typeRepository)
    {
        super(repository);
        this.ticketRepository = repository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.statusRepository = statusRepository;
        this.bookingRepository = bookingRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {

        if (ticket.getUser() != null && ticket.getUser().getId() != null) {
        UserProfile user = userRepository.findById(ticket.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        ticket.setUser(user);
        }

        if (ticket.getEvent() != null && ticket.getEvent().getId() != null) {
        LiveEvent event = eventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        ticket.setEvent(event);
        }

        if (ticket.getStatus() != null && ticket.getStatus().getId() != null) {
        TicketStatus status = statusRepository.findById(ticket.getStatus().getId())
                .orElseThrow(() -> new RuntimeException("TicketStatus not found"));
        ticket.setStatus(status);
        }

        if (ticket.getBooking() != null && ticket.getBooking().getId() != null) {
        Booking booking = bookingRepository.findById(ticket.getBooking().getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        ticket.setBooking(booking);
        }

        if (ticket.getType() != null && ticket.getType().getId() != null) {
        EventTicketType type = typeRepository.findById(ticket.getType().getId())
                .orElseThrow(() -> new RuntimeException("EventTicketType not found"));
        ticket.setType(type);
        }

        return ticketRepository.save(ticket);
    }


    public Ticket update(Long id, Ticket ticketRequest) {
        Ticket existing = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // Copier les champs simples
        existing.setTicketNumber(ticketRequest.getTicketNumber());
        existing.setPrice(ticketRequest.getPrice());
        existing.setSeatInfo(ticketRequest.getSeatInfo());

// Relations ManyToOne : mise à jour conditionnelle

        if (ticketRequest.getUser() != null && ticketRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(ticketRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (ticketRequest.getEvent() != null && ticketRequest.getEvent().getId() != null) {
        LiveEvent event = eventRepository.findById(ticketRequest.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        existing.setEvent(event);
        }

        if (ticketRequest.getStatus() != null && ticketRequest.getStatus().getId() != null) {
        TicketStatus status = statusRepository.findById(ticketRequest.getStatus().getId())
                .orElseThrow(() -> new RuntimeException("TicketStatus not found"));
        existing.setStatus(status);
        }

        if (ticketRequest.getBooking() != null && ticketRequest.getBooking().getId() != null) {
        Booking booking = bookingRepository.findById(ticketRequest.getBooking().getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        existing.setBooking(booking);
        }

        if (ticketRequest.getType() != null && ticketRequest.getType().getId() != null) {
        EventTicketType type = typeRepository.findById(ticketRequest.getType().getId())
                .orElseThrow(() -> new RuntimeException("EventTicketType not found"));
        existing.setType(type);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

    

    


        return ticketRepository.save(existing);
    }
}