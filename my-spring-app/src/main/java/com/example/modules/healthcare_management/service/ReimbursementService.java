package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.repository.ReimbursementRepository;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ReimbursementService extends BaseService<Reimbursement> {

    protected final ReimbursementRepository reimbursementRepository;
    private final InvoiceRepository invoiceRepository;

    public ReimbursementService(ReimbursementRepository repository,InvoiceRepository invoiceRepository)
    {
        super(repository);
        this.reimbursementRepository = repository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Reimbursement save(Reimbursement reimbursement) {

        if (reimbursement.getInvoice() != null && reimbursement.getInvoice().getId() != null) {
        Invoice invoice = invoiceRepository.findById(reimbursement.getInvoice().getId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        reimbursement.setInvoice(invoice);
        }

        return reimbursementRepository.save(reimbursement);
    }


    public Reimbursement update(Long id, Reimbursement reimbursementRequest) {
        Reimbursement existing = reimbursementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reimbursement not found"));

    // Copier les champs simples
        existing.setReimbursementDate(reimbursementRequest.getReimbursementDate());
        existing.setAmount(reimbursementRequest.getAmount());
        existing.setMethod(reimbursementRequest.getMethod());

// Relations ManyToOne : mise à jour conditionnelle

        if (reimbursementRequest.getInvoice() != null && reimbursementRequest.getInvoice().getId() != null) {
        Invoice invoice = invoiceRepository.findById(reimbursementRequest.getInvoice().getId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        existing.setInvoice(invoice);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return reimbursementRepository.save(existing);
    }
}