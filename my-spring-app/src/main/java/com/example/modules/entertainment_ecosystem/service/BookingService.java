package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class BookingService extends BaseService<Booking> {

    protected final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public BookingService(BookingRepository repository,PaymentRepository paymentRepository)
    {
        super(repository);
        this.bookingRepository = repository;
            this.paymentRepository = paymentRepository;
    }

    @Override
    public Booking save(Booking booking) {

        if (booking.getTickets() != null) {
            for (Ticket item : booking.getTickets()) {
            item.setBooking(booking);
            }
        }
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

    

    

        if (bookingRequest.getPayment() != null
        && bookingRequest.getPayment().getId() != null) {

        Payment payment = paymentRepository.findById(
        bookingRequest.getPayment().getId()
        ).orElseThrow(() -> new RuntimeException("Payment not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setPayment(payment);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            payment.setBooking(existing);
        
        }

    


        return bookingRepository.save(existing);
    }
}