package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.repository.ReimbursementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class InvoiceService extends BaseService<Invoice> {

    protected final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;
    private final ReimbursementRepository ReimbursementsRepository;

    public InvoiceService(InvoiceRepository repository, PatientRepository patientRepository, ReimbursementRepository ReimbursementsRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        this.patientRepository = patientRepository;
        this.ReimbursementsRepository = ReimbursementsRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
    // ---------- OneToMany ----------
        if (invoice.getReimbursements() != null) {
            List<Reimbursement> managedReimbursements = new ArrayList<>();
            for (Reimbursement item : invoice.getReimbursements()) {
                if (item.getId() != null) {
                    Reimbursement existingItem = ReimbursementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Reimbursement not found"));

                     existingItem.setInvoice(invoice);
                     managedReimbursements.add(existingItem);
                } else {
                    item.setInvoice(invoice);
                    managedReimbursements.add(item);
                }
            }
            invoice.setReimbursements(managedReimbursements);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (invoice.getPatient() != null &&
            invoice.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                invoice.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            invoice.setPatient(existingPatient);
        }
        
    // ---------- OneToOne ----------
    return invoiceRepository.save(invoice);
}


    public Invoice update(Long id, Invoice invoiceRequest) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

    // Copier les champs simples
        existing.setInvoiceDate(invoiceRequest.getInvoiceDate());
        existing.setTotalAmount(invoiceRequest.getTotalAmount());
        existing.setStatus(invoiceRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (invoiceRequest.getPatient() != null &&
            invoiceRequest.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                invoiceRequest.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            existing.setPatient(existingPatient);
        } else {
            existing.setPatient(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getReimbursements().clear();

        if (invoiceRequest.getReimbursements() != null) {
            for (var item : invoiceRequest.getReimbursements()) {
                Reimbursement existingItem;
                if (item.getId() != null) {
                    existingItem = ReimbursementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Reimbursement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setInvoice(existing);
                existing.getReimbursements().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return invoiceRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Invoice> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Invoice entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getReimbursements() != null) {
            for (var child : entity.getReimbursements()) {
                // retirer la référence inverse
                child.setInvoice(null);
            }
            entity.getReimbursements().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPatient() != null) {
            entity.setPatient(null);
        }
        
        repository.delete(entity);
        return true;
    }
}