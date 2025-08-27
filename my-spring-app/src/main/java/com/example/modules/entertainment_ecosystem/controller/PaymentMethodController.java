package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PaymentMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentMethodSimpleDto;
import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.mapper.PaymentMethodMapper;
import com.example.modules.entertainment_ecosystem.service.PaymentMethodService;
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
 * Controller for managing PaymentMethod entities.
 */
@RestController
@RequestMapping("/api/paymentmethods")
public class PaymentMethodController extends BaseController<PaymentMethod, PaymentMethodDto, PaymentMethodSimpleDto> {

    public PaymentMethodController(PaymentMethodService paymentmethodService,
                                    PaymentMethodMapper paymentmethodMapper) {
        super(paymentmethodService, paymentmethodMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethodDto>> getAllPaymentMethods(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PaymentMethodDto>> searchPaymentMethods(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(PaymentMethod.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDto> createPaymentMethod(
            @Valid @RequestBody PaymentMethodDto paymentmethodDto,
            UriComponentsBuilder uriBuilder) {

        PaymentMethod entity = mapper.toEntity(paymentmethodDto);
        PaymentMethod saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/paymentmethods/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PaymentMethodDto>> createAllPaymentMethods(
            @Valid @RequestBody List<PaymentMethodDto> paymentmethodDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PaymentMethod> entities = mapper.toEntityList(paymentmethodDtoList);
        List<PaymentMethod> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/paymentmethods").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> updatePaymentMethod(
            @PathVariable Long id,
            @Valid @RequestBody PaymentMethodDto paymentmethodDto) {

        PaymentMethod entityToUpdate = mapper.toEntity(paymentmethodDto);
        PaymentMethod updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        return doDelete(id);
    }
}