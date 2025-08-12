package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class BookingService extends BaseService<Booking> {

    protected final BookingRepository bookingRepository;

    public BookingService(BookingRepository repository)
    {
        super(repository);
        this.bookingRepository = repository;
    }

    @Override
    public Booking save(Booking booking) {

        if (booking.getTickets() != null) {
            for (Ticket item : booking.getTickets()) {
            item.setBooking(booking);
            }
        }
        if (booking.getPayment() != null) {
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

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getTickets().clear();
        if (bookingRequest.getTickets() != null) {
            for (var item : bookingRequest.getTickets()) {
            item.setBooking(existing);
            existing.getTickets().add(item);
            }
        }

        return bookingRepository.save(existing);
    }
}