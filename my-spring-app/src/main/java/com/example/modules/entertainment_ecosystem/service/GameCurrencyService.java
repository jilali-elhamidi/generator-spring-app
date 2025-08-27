package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.repository.GameCurrencyRepository;

import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.repository.GameTransactionRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GameCurrencyService extends BaseService<GameCurrency> {

    protected final GameCurrencyRepository gamecurrencyRepository;
    
    protected final GameTransactionRepository transactionsRepository;
    

    public GameCurrencyService(GameCurrencyRepository repository, GameTransactionRepository transactionsRepository)
    {
        super(repository);
        this.gamecurrencyRepository = repository;
        
        this.transactionsRepository = transactionsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public GameCurrency update(Long id, GameCurrency gamecurrencyRequest) {
        GameCurrency existing = gamecurrencyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameCurrency not found"));

    // Copier les champs simples
        existing.setName(gamecurrencyRequest.getName());
        existing.setSymbol(gamecurrencyRequest.getSymbol());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<GameCurrency> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GameCurrency> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GameCurrency.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GameCurrency> saveAll(List<GameCurrency> gamecurrencyList) {
        return super.saveAll(gamecurrencyList);
    }

}