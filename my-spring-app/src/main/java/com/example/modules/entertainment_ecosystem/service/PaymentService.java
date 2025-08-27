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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PaymentService extends BaseService<Payment> {

    protected final PaymentRepository paymentRepository;
    
    protected final BookingRepository bookingRepository;
    
    protected final PaymentMethodRepository methodRepository;
    

    public PaymentService(PaymentRepository repository, BookingRepository bookingRepository, PaymentMethodRepository methodRepository)
    {
        super(repository);
        this.paymentRepository = repository;
        
        this.bookingRepository = bookingRepository;
        
        this.methodRepository = methodRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Payment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Payment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Payment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Payment> saveAll(List<Payment> paymentList) {
        return super.saveAll(paymentList);
    }

}