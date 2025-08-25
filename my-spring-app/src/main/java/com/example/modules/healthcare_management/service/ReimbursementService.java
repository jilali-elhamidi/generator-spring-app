package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.repository.ReimbursementRepository;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReimbursementService extends BaseService<Reimbursement> {

    protected final ReimbursementRepository reimbursementRepository;
    private final InvoiceRepository invoiceRepository;

    public ReimbursementService(ReimbursementRepository repository, InvoiceRepository invoiceRepository)
    {
        super(repository);
        this.reimbursementRepository = repository;
        this.invoiceRepository = invoiceRepository;
    }

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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reimbursementRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Reimbursement> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Reimbursement entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getInvoice() != null) {
            entity.setInvoice(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Reimbursement> saveAll(List<Reimbursement> reimbursementList) {

        return reimbursementRepository.saveAll(reimbursementList);
    }

}