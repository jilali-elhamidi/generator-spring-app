package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.repository.InvoiceRepository;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class InvoiceService extends BaseService<Invoice> {

    protected final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;

    public InvoiceService(InvoiceRepository repository,TransactionRepository transactionRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {


    

    
        if (invoice.getTransaction() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            invoice.setTransaction(
            transactionRepository.findById(invoice.getTransaction().getId())
            .orElseThrow(() -> new RuntimeException("transaction not found"))
            );
        
        invoice.getTransaction().setRelatedInvoice(invoice);
        }

    


        return invoiceRepository.save(invoice);
    }


    public Invoice update(Long id, Invoice invoiceRequest) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

    // Copier les champs simples
        existing.setInvoiceDate(invoiceRequest.getInvoiceDate());
        existing.setAmount(invoiceRequest.getAmount());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (invoiceRequest.getTransaction() != null
        && invoiceRequest.getTransaction().getId() != null) {

        Transaction transaction = transactionRepository.findById(
        invoiceRequest.getTransaction().getId()
        ).orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setTransaction(transaction);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            transaction.setRelatedInvoice(existing);
        
        }

    


        return invoiceRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Invoice> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Invoice entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    
        if (entity.getTransaction() != null) {
        // Dissocier côté inverse automatiquement
        entity.getTransaction().setRelatedInvoice(null);
        // Dissocier côté direct
        entity.setTransaction(null);
        }
    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}