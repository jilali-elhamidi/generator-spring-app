package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.Reimbursement;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class InvoiceService extends BaseService<Invoice> {

    protected final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;

    public InvoiceService(InvoiceRepository repository,PatientRepository patientRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {

        if (invoice.getPatient() != null && invoice.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(invoice.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        invoice.setPatient(patient);
        }

        if (invoice.getReimbursements() != null) {
            for (Reimbursement item : invoice.getReimbursements()) {
            item.setInvoice(invoice);
            }
        }

        return invoiceRepository.save(invoice);
    }


    public Invoice update(Long id, Invoice invoiceRequest) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

    // Copier les champs simples
        existing.setInvoiceDate(invoiceRequest.getInvoiceDate());
        existing.setTotalAmount(invoiceRequest.getTotalAmount());
        existing.setStatus(invoiceRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle

        if (invoiceRequest.getPatient() != null && invoiceRequest.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(invoiceRequest.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        existing.setPatient(patient);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getReimbursements().clear();
        if (invoiceRequest.getReimbursements() != null) {
            for (var item : invoiceRequest.getReimbursements()) {
            item.setInvoice(existing);
            existing.getReimbursements().add(item);
            }
        }

        return invoiceRepository.save(existing);
    }
}