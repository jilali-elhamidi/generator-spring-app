package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.repository.InvoiceRepository;

import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class InvoiceService extends BaseService<Invoice> {

    protected final InvoiceRepository invoiceRepository;
    
    protected final TransactionRepository transactionRepository;
    

    public InvoiceService(InvoiceRepository repository, TransactionRepository transactionRepository)
    {
        super(repository);
        this.invoiceRepository = repository;
        
        this.transactionRepository = transactionRepository;
        
    }

    @Transactional
    @Override
    public Invoice save(Invoice invoice) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (invoice.getTransaction() != null) {
            if (invoice.getTransaction().getId() != null) {
                Transaction existingTransaction = transactionRepository.findById(invoice.getTransaction().getId())
                    .orElseThrow(() -> new RuntimeException("Transaction not found with id "
                        + invoice.getTransaction().getId()));
                invoice.setTransaction(existingTransaction);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Transaction newTransaction = transactionRepository.save(invoice.getTransaction());
                invoice.setTransaction(newTransaction);
            }

            invoice.getTransaction().setRelatedInvoice(invoice);
        }
        
    return invoiceRepository.save(invoice);
}

    @Transactional
    @Override
    public Invoice update(Long id, Invoice invoiceRequest) {
        Invoice existing = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

    // Copier les champs simples
        existing.setInvoiceDate(invoiceRequest.getInvoiceDate());
        existing.setAmount(invoiceRequest.getAmount());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (invoiceRequest.getTransaction() != null &&invoiceRequest.getTransaction().getId() != null) {

        Transaction transaction = transactionRepository.findById(invoiceRequest.getTransaction().getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setTransaction(transaction);
        transaction.setRelatedInvoice(existing);
        }
    
    return invoiceRepository.save(existing);
}

    // Pagination simple
    public Page<Invoice> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Invoice> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Invoice.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Invoice> saveAll(List<Invoice> invoiceList) {
        return super.saveAll(invoiceList);
    }

}