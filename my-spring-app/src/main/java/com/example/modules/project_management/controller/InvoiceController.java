package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.InvoiceDto;
import com.example.modules.project_management.model.Invoice;
import com.example.modules.project_management.mapper.InvoiceMapper;
import com.example.modules.project_management.service.InvoiceService;
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
                @RequestBody InvoiceDto invoiceDto) {

                // Transformer le DTO en entity pour le service
                Invoice entityToUpdate = invoiceMapper.toEntity(invoiceDto);

                // Appel du service update
                Invoice updatedEntity = invoiceService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                InvoiceDto updatedDto = invoiceMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
                    boolean deleted = invoiceService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}