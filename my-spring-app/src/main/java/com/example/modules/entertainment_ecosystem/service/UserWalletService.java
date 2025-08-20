package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserWalletService extends BaseService<UserWallet> {

    protected final UserWalletRepository userwalletRepository;
    private final UserProfileRepository userRepository;
    private final TransactionRepository transactionsRepository;

    public UserWalletService(UserWalletRepository repository,UserProfileRepository userRepository,TransactionRepository transactionsRepository)
    {
        super(repository);
        this.userwalletRepository = repository;
        this.userRepository = userRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public UserWallet save(UserWallet userwallet) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userwallet.getTransactions() != null) {
            List<Transaction> managedTransactions = new ArrayList<>();
            for (Transaction item : userwallet.getTransactions()) {
            if (item.getId() != null) {
            Transaction existingItem = transactionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setWallet(userwallet);
            managedTransactions.add(existingItem);
            } else {
            item.setWallet(userwallet);
            managedTransactions.add(item);
            }
            }
            userwallet.setTransactions(managedTransactions);
            }
        
    


    

    

    
    
        if (userwallet.getUser() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            userwallet.setUser(
            userRepository.findById(userwallet.getUser().getId())
            .orElseThrow(() -> new RuntimeException("user not found"))
            );
        
        userwallet.getUser().setWallet(userwallet);
        }

        return userwalletRepository.save(userwallet);
    }


    public UserWallet update(Long id, UserWallet userwalletRequest) {
        UserWallet existing = userwalletRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserWallet not found"));

    // Copier les champs simples
        existing.setBalance(userwalletRequest.getBalance());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getTransactions().clear();

        if (userwalletRequest.getTransactions() != null) {
        for (var item : userwalletRequest.getTransactions()) {
        Transaction existingItem;
        if (item.getId() != null) {
        existingItem = transactionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Transaction not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setWallet(existing);

        // Ajouter directement dans la collection existante
        existing.getTransactions().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

        if (userwalletRequest.getUser() != null
        && userwalletRequest.getUser().getId() != null) {

        UserProfile user = userRepository.findById(
        userwalletRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setUser(user);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            user.setWallet(existing);
        
        }

    

    


        return userwalletRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserWallet> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserWallet entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getTransactions() != null) {
        for (var child : entity.getTransactions()) {
        
            child.setWallet(null); // retirer la référence inverse
        
        }
        entity.getTransactions().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    
        if (entity.getUser() != null) {
        // Dissocier côté inverse automatiquement
        entity.getUser().setWallet(null);
        // Dissocier côté direct
        entity.setUser(null);
        }
    

    


// --- Dissocier ManyToOne ---

    

    


repository.delete(entity);
return true;
}
}