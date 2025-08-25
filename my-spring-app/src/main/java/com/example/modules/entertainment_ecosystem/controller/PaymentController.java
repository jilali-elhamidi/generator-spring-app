package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PaymentDto;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.mapper.PaymentMapper;
import com.example.modules.entertainment_ecosystem.service.PaymentService;
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

        URI location = uriBuilder
                                .path("/api/payments/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(paymentMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PaymentDto>> createAllPayments(
            @Valid @RequestBody List<PaymentDto> paymentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Payment> entities = paymentMapper.toEntityList(paymentDtoList);
        List<Payment> savedEntities = paymentService.saveAll(entities);

        URI location = uriBuilder.path("/api/payments").build().toUri();

        return ResponseEntity.created(location).body(paymentMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDto paymentDto) {


        Payment entityToUpdate = paymentMapper.toEntity(paymentDto);
        Payment updatedEntity = paymentService.update(id, entityToUpdate);

        return ResponseEntity.ok(paymentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        boolean deleted = paymentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}