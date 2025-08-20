package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.repository.TicketRepository;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookingService extends BaseService<Booking> {

    protected final BookingRepository bookingRepository;
    private final TicketRepository ticketsRepository;
    private final PaymentRepository paymentRepository;

    public BookingService(BookingRepository repository, TicketRepository ticketsRepository, PaymentRepository paymentRepository)
    {
        super(repository);
        this.bookingRepository = repository;
        this.ticketsRepository = ticketsRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Booking save(Booking booking) {
    // ---------- OneToMany ----------
        if (booking.getTickets() != null) {
            List<Ticket> managedTickets = new ArrayList<>();
            for (Ticket item : booking.getTickets()) {
                if (item.getId() != null) {
                    Ticket existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));

                     existingItem.setBooking(booking);
                     managedTickets.add(existingItem);
                } else {
                    item.setBooking(booking);
                    managedTickets.add(item);
                }
            }
            booking.setTickets(managedTickets);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (booking.getPayment() != null) {
            
            
                // Vérifier si l'entité est déjà persistée
            booking.setPayment(
                paymentRepository.findById(booking.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("payment not found"))
            );
            
            booking.getPayment().setBooking(booking);
        }
        

    return bookingRepository.save(booking);
}


    public Booking update(Long id, Booking bookingRequest) {
        Booking existing = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    // Copier les champs simples
        existing.setBookingDate(bookingRequest.getBookingDate());
        existing.setTotalAmount(bookingRequest.getTotalAmount());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getTickets().clear();

        if (bookingRequest.getTickets() != null) {
            for (var item : bookingRequest.getTickets()) {
                Ticket existingItem;
                if (item.getId() != null) {
                    existingItem = ticketsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Ticket not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBooking(existing);
                existing.getTickets().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
            if (bookingRequest.getPayment() != null &&
            bookingRequest.getPayment().getId() != null) {

            Payment payment = paymentRepository.findById(
                bookingRequest.getPayment().getId()
            ).orElseThrow(() -> new RuntimeException("Payment not found"));

            existing.setPayment(payment);

            
            payment.setBooking(existing);
            
        }
        

    return bookingRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Booking> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Booking entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getTickets() != null) {
            for (var child : entity.getTickets()) {
                
                child.setBooking(null); // retirer la référence inverse
                
            }
            entity.getTickets().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getPayment() != null) {
            entity.getPayment().setBooking(null);
            entity.setPayment(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}