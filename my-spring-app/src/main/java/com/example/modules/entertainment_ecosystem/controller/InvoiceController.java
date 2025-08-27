package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.InvoiceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.InvoiceSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.mapper.InvoiceMapper;
import com.example.modules.entertainment_ecosystem.service.InvoiceService;
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
 * Controller for managing Invoice entities.
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController extends BaseController<Invoice, InvoiceDto, InvoiceSimpleDto> {

    public InvoiceController(InvoiceService invoiceService,
                                    InvoiceMapper invoiceMapper) {
        super(invoiceService, invoiceMapper);
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceDto>> getAllInvoices(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InvoiceDto>> searchInvoices(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Invoice.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(
            @Valid @RequestBody InvoiceDto invoiceDto,
            UriComponentsBuilder uriBuilder) {

        Invoice entity = mapper.toEntity(invoiceDto);
        Invoice saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/invoices/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<InvoiceDto>> createAllInvoices(
            @Valid @RequestBody List<InvoiceDto> invoiceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Invoice> entities = mapper.toEntityList(invoiceDtoList);
        List<Invoice> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/invoices").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto invoiceDto) {

        Invoice entityToUpdate = mapper.toEntity(invoiceDto);
        Invoice updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        return doDelete(id);
    }
}