package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PaymentMethodDto;
import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.mapper.PaymentMethodMapper;
import com.example.modules.entertainment_ecosystem.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/paymentmethods")
public class PaymentMethodController {

    private final PaymentMethodService paymentmethodService;
    private final PaymentMethodMapper paymentmethodMapper;

    public PaymentMethodController(PaymentMethodService paymentmethodService,
                                    PaymentMethodMapper paymentmethodMapper) {
        this.paymentmethodService = paymentmethodService;
        this.paymentmethodMapper = paymentmethodMapper;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDto>> getAllPaymentMethods() {
        List<PaymentMethod> entities = paymentmethodService.findAll();
        return ResponseEntity.ok(paymentmethodMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable Long id) {
        return paymentmethodService.findById(id)
                .map(paymentmethodMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDto> createPaymentMethod(
            @Valid @RequestBody PaymentMethodDto paymentmethodDto,
            UriComponentsBuilder uriBuilder) {

        PaymentMethod entity = paymentmethodMapper.toEntity(paymentmethodDto);
        PaymentMethod saved = paymentmethodService.save(entity);

        URI location = uriBuilder
                                .path("/api/paymentmethods/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(paymentmethodMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PaymentMethodDto>> createAllPaymentMethods(
            @Valid @RequestBody List<PaymentMethodDto> paymentmethodDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PaymentMethod> entities = paymentmethodMapper.toEntityList(paymentmethodDtoList);
        List<PaymentMethod> savedEntities = paymentmethodService.saveAll(entities);

        URI location = uriBuilder.path("/api/paymentmethods").build().toUri();

        return ResponseEntity.created(location).body(paymentmethodMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> updatePaymentMethod(
            @PathVariable Long id,
            @Valid @RequestBody PaymentMethodDto paymentmethodDto) {


        PaymentMethod entityToUpdate = paymentmethodMapper.toEntity(paymentmethodDto);
        PaymentMethod updatedEntity = paymentmethodService.update(id, entityToUpdate);

        return ResponseEntity.ok(paymentmethodMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        boolean deleted = paymentmethodService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}