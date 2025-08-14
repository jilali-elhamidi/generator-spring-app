package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository repository,BookingRepository bookingRepository)
    {
        super(repository);
        this.paymentRepository = repository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Payment save(Payment payment) {


    

    
        if (payment.getBooking() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            payment.setBooking(
            bookingRepository.findById(payment.getBooking().getId())
            .orElseThrow(() -> new RuntimeException("booking not found"))
            );
        
        payment.getBooking().setPayment(payment);
        }

        return paymentRepository.save(payment);
    }


    public Payment update(Long id, Payment paymentRequest) {
        Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    // Copier les champs simples
        existing.setAmount(paymentRequest.getAmount());
        existing.setPaymentDate(paymentRequest.getPaymentDate());
        existing.setMethod(paymentRequest.getMethod());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (paymentRequest.getBooking() != null
        && paymentRequest.getBooking().getId() != null) {

        Booking booking = bookingRepository.findById(
        paymentRequest.getBooking().getId()
        ).orElseThrow(() -> new RuntimeException("Booking not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setBooking(booking);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            booking.setPayment(existing);
        
        }

    


        return paymentRepository.save(existing);
    }


}