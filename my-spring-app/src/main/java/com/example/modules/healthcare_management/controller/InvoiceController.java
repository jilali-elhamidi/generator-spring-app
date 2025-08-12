package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.InvoiceDto;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.mapper.InvoiceMapper;
import com.example.modules.healthcare_management.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceController(InvoiceService invoiceService,
                                    InvoiceMapper invoiceMapper) {
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<Invoice> entities = invoiceService.findAll();
        return ResponseEntity.ok(invoiceMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return invoiceService.findById(id)
                .map(invoiceMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(
            @Valid @RequestBody InvoiceDto invoiceDto,
            UriComponentsBuilder uriBuilder) {

        Invoice entity = invoiceMapper.toEntity(invoiceDto);
        Invoice saved = invoiceService.save(entity);
        URI location = uriBuilder.path("/api/invoices/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(invoiceMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto invoiceDto) {

        try {
            Invoice updatedEntity = invoiceService.update(
                    id,
                    invoiceMapper.toEntity(invoiceDto)
            );
            return ResponseEntity.ok(invoiceMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}