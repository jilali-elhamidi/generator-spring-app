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
        URI location = uriBuilder.path("/api/paymentmethods/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(paymentmethodMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<PaymentMethodDto> updatePaymentMethod(
                @PathVariable Long id,
                @RequestBody PaymentMethodDto paymentmethodDto) {

                // Transformer le DTO en entity pour le service
                PaymentMethod entityToUpdate = paymentmethodMapper.toEntity(paymentmethodDto);

                // Appel du service update
                PaymentMethod updatedEntity = paymentmethodService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                PaymentMethodDto updatedDto = paymentmethodMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
                    boolean deleted = paymentmethodService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}