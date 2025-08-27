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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class BookingService extends BaseService<Booking> {

    protected final BookingRepository bookingRepository;
    
    protected final TicketRepository ticketsRepository;
    
    protected final PaymentRepository paymentRepository;
    

    public BookingService(BookingRepository repository, TicketRepository ticketsRepository, PaymentRepository paymentRepository)
    {
        super(repository);
        this.bookingRepository = repository;
        
        this.ticketsRepository = ticketsRepository;
        
        this.paymentRepository = paymentRepository;
        
    }

    @Transactional
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
            if (booking.getPayment().getId() != null) {
                Payment existingPayment = paymentRepository.findById(booking.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id "
                        + booking.getPayment().getId()));
                booking.setPayment(existingPayment);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Payment newPayment = paymentRepository.save(booking.getPayment());
                booking.setPayment(newPayment);
            }

            booking.getPayment().setBooking(booking);
        }
        
    return bookingRepository.save(booking);
}

    @Transactional
    @Override
    public Booking update(Long id, Booking bookingRequest) {
        Booking existing = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    // Copier les champs simples
        existing.setBookingDate(bookingRequest.getBookingDate());
        existing.setTotalAmount(bookingRequest.getTotalAmount());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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
        if (bookingRequest.getPayment() != null &&bookingRequest.getPayment().getId() != null) {

        Payment payment = paymentRepository.findById(bookingRequest.getPayment().getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existing.setPayment(payment);
        payment.setBooking(existing);
        }
    
    return bookingRepository.save(existing);
}

    // Pagination simple
    public Page<Booking> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Booking> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Booking.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Booking> saveAll(List<Booking> bookingList) {
        return super.saveAll(bookingList);
    }

}