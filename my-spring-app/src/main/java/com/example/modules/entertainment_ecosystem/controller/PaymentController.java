package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PaymentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.mapper.PaymentMapper;
import com.example.modules.entertainment_ecosystem.service.PaymentService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Payment entities.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController extends BaseController<Payment, PaymentDto, PaymentSimpleDto> {

    public PaymentController(PaymentService paymentService,
                                    PaymentMapper paymentMapper) {
        super(paymentService, paymentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getAllPayments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PaymentDto>> searchPayments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Payment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(
            @Valid @RequestBody PaymentDto paymentDto,
            UriComponentsBuilder uriBuilder) {

        Payment entity = mapper.toEntity(paymentDto);
        Payment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/payments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PaymentDto>> createAllPayments(
            @Valid @RequestBody List<PaymentDto> paymentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Payment> entities = mapper.toEntityList(paymentDtoList);
        List<Payment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/payments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDto paymentDto) {

        Payment entityToUpdate = mapper.toEntity(paymentDto);
        Payment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        return doDelete(id);
    }
}