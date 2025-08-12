package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.PaymentDto;
import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.mapper.PaymentMapper;
import com.example.modules.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentService paymentService,
                                    PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<Payment> entities = paymentService.findAll();
        return ResponseEntity.ok(paymentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(paymentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(
            @Valid @RequestBody PaymentDto paymentDto,
            UriComponentsBuilder uriBuilder) {

        Payment entity = paymentMapper.toEntity(paymentDto);
        Payment saved = paymentService.save(entity);
        URI location = uriBuilder.path("/api/payments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(paymentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDto paymentDto) {

        try {
            Payment updatedEntity = paymentService.update(
                    id,
                    paymentMapper.toEntity(paymentDto)
            );
            return ResponseEntity.ok(paymentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}