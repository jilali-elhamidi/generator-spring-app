package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.repository.GameCurrencyRepository;
import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.repository.GameTransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameCurrencyService extends BaseService<GameCurrency> {

    protected final GameCurrencyRepository gamecurrencyRepository;
    private final GameTransactionRepository transactionsRepository;

    public GameCurrencyService(GameCurrencyRepository repository, GameTransactionRepository transactionsRepository)
    {
        super(repository);
        this.gamecurrencyRepository = repository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public GameCurrency save(GameCurrency gamecurrency) {
    // ---------- OneToMany ----------
        if (gamecurrency.getTransactions() != null) {
            List<GameTransaction> managedTransactions = new ArrayList<>();
            for (GameTransaction item : gamecurrency.getTransactions()) {
                if (item.getId() != null) {
                    GameTransaction existingItem = transactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameTransaction not found"));

                     existingItem.setCurrency(gamecurrency);
                     managedTransactions.add(existingItem);
                } else {
                    item.setCurrency(gamecurrency);
                    managedTransactions.add(item);
                }
            }
            gamecurrency.setTransactions(managedTransactions);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return gamecurrencyRepository.save(gamecurrency);
}


    public GameCurrency update(Long id, GameCurrency gamecurrencyRequest) {
        GameCurrency existing = gamecurrencyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameCurrency not found"));

    // Copier les champs simples
        existing.setName(gamecurrencyRequest.getName());
        existing.setSymbol(gamecurrencyRequest.getSymbol());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getTransactions().clear();

        if (gamecurrencyRequest.getTransactions() != null) {
            for (var item : gamecurrencyRequest.getTransactions()) {
                GameTransaction existingItem;
                if (item.getId() != null) {
                    existingItem = transactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameTransaction not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCurrency(existing);
                existing.getTransactions().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gamecurrencyRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameCurrency> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameCurrency entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getTransactions() != null) {
            for (var child : entity.getTransactions()) {
                // retirer la référence inverse
                child.setCurrency(null);
            }
            entity.getTransactions().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}