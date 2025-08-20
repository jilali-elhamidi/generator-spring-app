package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.repository.GameTransactionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.repository.GameCurrencyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameTransactionService extends BaseService<GameTransaction> {

    protected final GameTransactionRepository gametransactionRepository;
    private final UserProfileRepository userRepository;
    private final GameCurrencyRepository currencyRepository;

    public GameTransactionService(GameTransactionRepository repository,UserProfileRepository userRepository,GameCurrencyRepository currencyRepository)
    {
        super(repository);
        this.gametransactionRepository = repository;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public GameTransaction save(GameTransaction gametransaction) {


    

    


    

    

    if (gametransaction.getUser() != null
        && gametransaction.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        gametransaction.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gametransaction.setUser(existingUser);
        }
    
    if (gametransaction.getCurrency() != null
        && gametransaction.getCurrency().getId() != null) {
        GameCurrency existingCurrency = currencyRepository.findById(
        gametransaction.getCurrency().getId()
        ).orElseThrow(() -> new RuntimeException("GameCurrency not found"));
        gametransaction.setCurrency(existingCurrency);
        }
    

        return gametransactionRepository.save(gametransaction);
    }


    public GameTransaction update(Long id, GameTransaction gametransactionRequest) {
        GameTransaction existing = gametransactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameTransaction not found"));

    // Copier les champs simples
        existing.setAmount(gametransactionRequest.getAmount());
        existing.setTransactionDate(gametransactionRequest.getTransactionDate());
        existing.setDescription(gametransactionRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle
        if (gametransactionRequest.getUser() != null &&
        gametransactionRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        gametransactionRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }
        if (gametransactionRequest.getCurrency() != null &&
        gametransactionRequest.getCurrency().getId() != null) {

        GameCurrency existingCurrency = currencyRepository.findById(
        gametransactionRequest.getCurrency().getId()
        ).orElseThrow(() -> new RuntimeException("GameCurrency not found"));

        existing.setCurrency(existingCurrency);
        } else {
        existing.setCurrency(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return gametransactionRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<GameTransaction> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

GameTransaction entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getCurrency() != null) {
        entity.setCurrency(null);
        }
    


repository.delete(entity);
return true;
}
}