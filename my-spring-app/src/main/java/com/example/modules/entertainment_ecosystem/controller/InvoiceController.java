package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.InvoiceDto;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.mapper.InvoiceMapper;
import com.example.modules.entertainment_ecosystem.service.InvoiceService;
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

        URI location = uriBuilder
                                .path("/api/invoices/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(invoiceMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<InvoiceDto>> createAllInvoices(
            @Valid @RequestBody List<InvoiceDto> invoiceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Invoice> entities = invoiceMapper.toEntityList(invoiceDtoList);
        List<Invoice> savedEntities = invoiceService.saveAll(entities);

        URI location = uriBuilder.path("/api/invoices").build().toUri();

        return ResponseEntity.created(location).body(invoiceMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto invoiceDto) {


        Invoice entityToUpdate = invoiceMapper.toEntity(invoiceDto);
        Invoice updatedEntity = invoiceService.update(id, entityToUpdate);

        return ResponseEntity.ok(invoiceMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        boolean deleted = invoiceService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}