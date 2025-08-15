package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.repository.InvoiceRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TransactionService extends BaseService<Transaction> {

    protected final TransactionRepository transactionRepository;
    private final UserWalletRepository walletRepository;
    private final InvoiceRepository relatedInvoiceRepository;
    private final DigitalPurchaseRepository digitalPurchaseRepository;

    public TransactionService(TransactionRepository repository,UserWalletRepository walletRepository,InvoiceRepository relatedInvoiceRepository,DigitalPurchaseRepository digitalPurchaseRepository)
    {
        super(repository);
        this.transactionRepository = repository;
        this.walletRepository = walletRepository;
        this.relatedInvoiceRepository = relatedInvoiceRepository;
        this.digitalPurchaseRepository = digitalPurchaseRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {


    

    

    

    if (transaction.getWallet() != null
        && transaction.getWallet().getId() != null) {
        UserWallet existingWallet = walletRepository.findById(
        transaction.getWallet().getId()
        ).orElseThrow(() -> new RuntimeException("UserWallet not found"));
        transaction.setWallet(existingWallet);
        }
    
    
    
        if (transaction.getRelatedInvoice() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            transaction.setRelatedInvoice(
            relatedInvoiceRepository.findById(transaction.getRelatedInvoice().getId())
            .orElseThrow(() -> new RuntimeException("relatedInvoice not found"))
            );
        
        transaction.getRelatedInvoice().setTransaction(transaction);
        }
        if (transaction.getDigitalPurchase() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            transaction.setDigitalPurchase(
            digitalPurchaseRepository.findById(transaction.getDigitalPurchase().getId())
            .orElseThrow(() -> new RuntimeException("digitalPurchase not found"))
            );
        
        transaction.getDigitalPurchase().setTransaction(transaction);
        }

        return transactionRepository.save(transaction);
    }


    public Transaction update(Long id, Transaction transactionRequest) {
        Transaction existing = transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));

    // Copier les champs simples
        existing.setAmount(transactionRequest.getAmount());
        existing.setTransactionDate(transactionRequest.getTransactionDate());
        existing.setType(transactionRequest.getType());

// Relations ManyToOne : mise à jour conditionnelle
        if (transactionRequest.getWallet() != null &&
        transactionRequest.getWallet().getId() != null) {

        UserWallet existingWallet = walletRepository.findById(
        transactionRequest.getWallet().getId()
        ).orElseThrow(() -> new RuntimeException("UserWallet not found"));

        existing.setWallet(existingWallet);
        } else {
        existing.setWallet(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

        if (transactionRequest.getRelatedInvoice() != null
        && transactionRequest.getRelatedInvoice().getId() != null) {

        Invoice relatedInvoice = relatedInvoiceRepository.findById(
        transactionRequest.getRelatedInvoice().getId()
        ).orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setRelatedInvoice(relatedInvoice);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            relatedInvoice.setTransaction(existing);
        
        }

    

    

        if (transactionRequest.getDigitalPurchase() != null
        && transactionRequest.getDigitalPurchase().getId() != null) {

        DigitalPurchase digitalPurchase = digitalPurchaseRepository.findById(
        transactionRequest.getDigitalPurchase().getId()
        ).orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setDigitalPurchase(digitalPurchase);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            digitalPurchase.setTransaction(existing);
        
        }

    


        return transactionRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Transaction> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Transaction entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    


// --- Dissocier OneToOne ---

    

    
        if (entity.getRelatedInvoice() != null) {
        // Dissocier côté inverse automatiquement
        entity.getRelatedInvoice().setTransaction(null);
        // Dissocier côté direct
        entity.setRelatedInvoice(null);
        }
    

    
        if (entity.getDigitalPurchase() != null) {
        // Dissocier côté inverse automatiquement
        entity.getDigitalPurchase().setTransaction(null);
        // Dissocier côté direct
        entity.setDigitalPurchase(null);
        }
    


// --- Dissocier ManyToOne ---

    
        if (entity.getWallet() != null) {
        entity.setWallet(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}