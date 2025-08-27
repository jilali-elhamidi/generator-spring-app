package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.repository.ReimbursementRepository;

import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ReimbursementService extends BaseService<Reimbursement> {

    protected final ReimbursementRepository reimbursementRepository;
    
    protected final InvoiceRepository invoiceRepository;
    

    public ReimbursementService(ReimbursementRepository repository, InvoiceRepository invoiceRepository)
    {
        super(repository);
        this.reimbursementRepository = repository;
        
        this.invoiceRepository = invoiceRepository;
        
    }

    @Transactional
    @Override
    public Reimbursement save(Reimbursement reimbursement) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reimbursement.getInvoice() != null) {
            if (reimbursement.getInvoice().getId() != null) {
                Invoice existingInvoice = invoiceRepository.findById(
                    reimbursement.getInvoice().getId()
                ).orElseThrow(() -> new RuntimeException("Invoice not found with id "
                    + reimbursement.getInvoice().getId()));
                reimbursement.setInvoice(existingInvoice);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Invoice newInvoice = invoiceRepository.save(reimbursement.getInvoice());
                reimbursement.setInvoice(newInvoice);
            }
        }
        
    // ---------- OneToOne ----------
    return reimbursementRepository.save(reimbursement);
}

    @Transactional
    @Override
    public Reimbursement update(Long id, Reimbursement reimbursementRequest) {
        Reimbursement existing = reimbursementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reimbursement not found"));

    // Copier les champs simples
        existing.setReimbursementDate(reimbursementRequest.getReimbursementDate());
        existing.setAmount(reimbursementRequest.getAmount());
        existing.setMethod(reimbursementRequest.getMethod());

    // ---------- Relations ManyToOne ----------
        if (reimbursementRequest.getInvoice() != null &&
            reimbursementRequest.getInvoice().getId() != null) {

            Invoice existingInvoice = invoiceRepository.findById(
                reimbursementRequest.getInvoice().getId()
            ).orElseThrow(() -> new RuntimeException("Invoice not found"));

            existing.setInvoice(existingInvoice);
        } else {
            existing.setInvoice(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reimbursementRepository.save(existing);
}

    // Pagination simple
    public Page<Reimbursement> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Reimbursement> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Reimbursement.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Reimbursement> saveAll(List<Reimbursement> reimbursementList) {
        return super.saveAll(reimbursementList);
    }

}