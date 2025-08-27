package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionStatRepository;

import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GamePlaySessionStatService extends BaseService<GamePlaySessionStat> {

    protected final GamePlaySessionStatRepository gameplaysessionstatRepository;
    
    protected final GamePlaySessionRepository gamePlaySessionRepository;
    

    public GamePlaySessionStatService(GamePlaySessionStatRepository repository, GamePlaySessionRepository gamePlaySessionRepository)
    {
        super(repository);
        this.gameplaysessionstatRepository = repository;
        
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        
    }

    @Transactional
    @Override
    public GamePlaySessionStat save(GamePlaySessionStat gameplaysessionstat) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gameplaysessionstat.getGamePlaySession() != null) {
            if (gameplaysessionstat.getGamePlaySession().getId() != null) {
                GamePlaySession existingGamePlaySession = gamePlaySessionRepository.findById(
                    gameplaysessionstat.getGamePlaySession().getId()
                ).orElseThrow(() -> new RuntimeException("GamePlaySession not found with id "
                    + gameplaysessionstat.getGamePlaySession().getId()));
                gameplaysessionstat.setGamePlaySession(existingGamePlaySession);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GamePlaySession newGamePlaySession = gamePlaySessionRepository.save(gameplaysessionstat.getGamePlaySession());
                gameplaysessionstat.setGamePlaySession(newGamePlaySession);
            }
        }
        
    // ---------- OneToOne ----------
    return gameplaysessionstatRepository.save(gameplaysessionstat);
}

    @Transactional
    @Override
    public GamePlaySessionStat update(Long id, GamePlaySessionStat gameplaysessionstatRequest) {
        GamePlaySessionStat existing = gameplaysessionstatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlaySessionStat not found"));

    // Copier les champs simples
        existing.setStatName(gameplaysessionstatRequest.getStatName());
        existing.setStatValue(gameplaysessionstatRequest.getStatValue());

    // ---------- Relations ManyToOne ----------
        if (gameplaysessionstatRequest.getGamePlaySession() != null &&
            gameplaysessionstatRequest.getGamePlaySession().getId() != null) {

            GamePlaySession existingGamePlaySession = gamePlaySessionRepository.findById(
                gameplaysessionstatRequest.getGamePlaySession().getId()
            ).orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

            existing.setGamePlaySession(existingGamePlaySession);
        } else {
            existing.setGamePlaySession(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return gameplaysessionstatRepository.save(existing);
}

    // Pagination simple
    public Page<GamePlaySessionStat> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GamePlaySessionStat> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GamePlaySessionStat.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GamePlaySessionStat> saveAll(List<GamePlaySessionStat> gameplaysessionstatList) {
        return super.saveAll(gameplaysessionstatList);
    }

}