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

    public UserWalletService(UserWalletRepository repository, UserProfileRepository userRepository, TransactionRepository transactionsRepository)
    {
        super(repository);
        this.userwalletRepository = repository;
        this.userRepository = userRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public UserWallet save(UserWallet userwallet) {
    // ---------- OneToMany ----------
        if (userwallet.getTransactions() != null) {
            List<Transaction> managedTransactions = new ArrayList<>();
            for (Transaction item : userwallet.getTransactions()) {
                if (item.getId() != null) {
                    Transaction existingItem = transactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Transaction not found"));

                     existingItem.setWallet(userwallet);
                     managedTransactions.add(existingItem);
                } else {
                    item.setWallet(userwallet);
                    managedTransactions.add(item);
                }
            }
            userwallet.setTransactions(managedTransactions);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (userwallet.getUser() != null) {
            if (userwallet.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(userwallet.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                        + userwallet.getUser().getId()));
                userwallet.setUser(existingUser);
            } else {
                // Nouvel objet → sauvegarde d'abord
                UserProfile newUser = userRepository.save(userwallet.getUser());
                userwallet.setUser(newUser);
            }

            userwallet.getUser().setWallet(userwallet);
        }
        
    return userwalletRepository.save(userwallet);
}


    public UserWallet update(Long id, UserWallet userwalletRequest) {
        UserWallet existing = userwalletRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserWallet not found"));

    // Copier les champs simples
        existing.setBalance(userwalletRequest.getBalance());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getTransactions().clear();

        if (userwalletRequest.getTransactions() != null) {
            for (var item : userwalletRequest.getTransactions()) {
                Transaction existingItem;
                if (item.getId() != null) {
                    existingItem = transactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Transaction not found"));
                } else {
                existingItem = item;
                }

                existingItem.setWallet(existing);
                existing.getTransactions().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (userwalletRequest.getUser() != null &&userwalletRequest.getUser().getId() != null) {

        UserProfile user = userRepository.findById(userwalletRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(user);
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
                // retirer la référence inverse
                child.setWallet(null);
            }
            entity.getTransactions().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getUser() != null) {
            entity.getUser().setWallet(null);
            entity.setUser(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<UserWallet> saveAll(List<UserWallet> userwalletList) {

        return userwalletRepository.saveAll(userwalletList);
    }

}