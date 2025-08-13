package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TransactionService extends BaseService<Transaction> {

    protected final TransactionRepository transactionRepository;
    private final UserWalletRepository walletRepository;
    private final InvoiceRepository relatedInvoiceRepository;

    public TransactionService(TransactionRepository repository,UserWalletRepository walletRepository,InvoiceRepository relatedInvoiceRepository)
    {
        super(repository);
        this.transactionRepository = repository;
        this.walletRepository = walletRepository;
            this.relatedInvoiceRepository = relatedInvoiceRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {

        if (transaction.getWallet() != null && transaction.getWallet().getId() != null) {
        UserWallet wallet = walletRepository.findById(transaction.getWallet().getId())
                .orElseThrow(() -> new RuntimeException("UserWallet not found"));
        transaction.setWallet(wallet);
        }
        if (transaction.getRelatedInvoice() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            transaction.setRelatedInvoice(
            relatedInvoiceRepository.findById(transaction.getRelatedInvoice().getId())
            .orElseThrow(() -> new RuntimeException("relatedInvoice not found"))
            );
        
        transaction.getRelatedInvoice().setTransaction(transaction);
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

        if (transactionRequest.getWallet() != null && transactionRequest.getWallet().getId() != null) {
        UserWallet wallet = walletRepository.findById(transactionRequest.getWallet().getId())
                .orElseThrow(() -> new RuntimeException("UserWallet not found"));
        existing.setWallet(wallet);
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

    


        return transactionRepository.save(existing);
    }
}