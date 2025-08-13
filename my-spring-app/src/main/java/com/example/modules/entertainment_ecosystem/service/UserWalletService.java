package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Transaction;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserWalletService extends BaseService<UserWallet> {

    protected final UserWalletRepository userwalletRepository;
    private final UserProfileRepository userRepository;

    public UserWalletService(UserWalletRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.userwalletRepository = repository;
            this.userRepository = userRepository;
    }

    @Override
    public UserWallet save(UserWallet userwallet) {

        if (userwallet.getTransactions() != null) {
            for (Transaction item : userwallet.getTransactions()) {
            item.setWallet(userwallet);
            }
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

        existing.getTransactions().clear();
        if (userwalletRequest.getTransactions() != null) {
            for (var item : userwalletRequest.getTransactions()) {
            item.setWallet(existing);
            existing.getTransactions().add(item);
            }
        }

    

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
}