package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.repository.PaymentRepository;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.repository.BookingRepository;
import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.repository.PaymentMethodRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMethodRepository methodRepository;

    public PaymentService(PaymentRepository repository, BookingRepository bookingRepository, PaymentMethodRepository methodRepository)
    {
        super(repository);
        this.paymentRepository = repository;
        this.bookingRepository = bookingRepository;
        this.methodRepository = methodRepository;
    }

    @Override
    public Payment save(Payment payment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (payment.getMethod() != null) {
            if (payment.getMethod().getId() != null) {
                PaymentMethod existingMethod = methodRepository.findById(
                    payment.getMethod().getId()
                ).orElseThrow(() -> new RuntimeException("PaymentMethod not found with id "
                    + payment.getMethod().getId()));
                payment.setMethod(existingMethod);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                PaymentMethod newMethod = methodRepository.save(payment.getMethod());
                payment.setMethod(newMethod);
            }
        }
        
    // ---------- OneToOne ----------
        if (payment.getBooking() != null) {
            if (payment.getBooking().getId() != null) {
                Booking existingBooking = bookingRepository.findById(payment.getBooking().getId())
                    .orElseThrow(() -> new RuntimeException("Booking not found with id "
                        + payment.getBooking().getId()));
                payment.setBooking(existingBooking);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Booking newBooking = bookingRepository.save(payment.getBooking());
                payment.setBooking(newBooking);
            }

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

    // ---------- Relations ManyToOne ----------
        if (paymentRequest.getMethod() != null &&
            paymentRequest.getMethod().getId() != null) {

            PaymentMethod existingMethod = methodRepository.findById(
                paymentRequest.getMethod().getId()
            ).orElseThrow(() -> new RuntimeException("PaymentMethod not found"));

            existing.setMethod(existingMethod);
        } else {
            existing.setMethod(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (paymentRequest.getBooking() != null &&paymentRequest.getBooking().getId() != null) {

        Booking booking = bookingRepository.findById(paymentRequest.getBooking().getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existing.setBooking(booking);
        booking.setPayment(existing);
        
        }
    
    return paymentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Payment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Payment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getBooking() != null) {
            entity.getBooking().setPayment(null);
            entity.setBooking(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getMethod() != null) {
            entity.setMethod(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Payment> saveAll(List<Payment> paymentList) {

        return paymentRepository.saveAll(paymentList);
    }

}