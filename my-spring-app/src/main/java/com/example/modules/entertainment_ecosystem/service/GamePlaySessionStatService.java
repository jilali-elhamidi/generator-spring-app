package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionStatRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GamePlaySessionStatService extends BaseService<GamePlaySessionStat> {

    protected final GamePlaySessionStatRepository gameplaysessionstatRepository;
    private final GamePlaySessionRepository gamePlaySessionRepository;

    public GamePlaySessionStatService(GamePlaySessionStatRepository repository,GamePlaySessionRepository gamePlaySessionRepository)
    {
        super(repository);
        this.gameplaysessionstatRepository = repository;
        this.gamePlaySessionRepository = gamePlaySessionRepository;
    }

    @Override
    public GamePlaySessionStat save(GamePlaySessionStat gameplaysessionstat) {


    

    if (gameplaysessionstat.getGamePlaySession() != null
        && gameplaysessionstat.getGamePlaySession().getId() != null) {
        GamePlaySession existingGamePlaySession = gamePlaySessionRepository.findById(
        gameplaysessionstat.getGamePlaySession().getId()
        ).orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
        gameplaysessionstat.setGamePlaySession(existingGamePlaySession);
        }
    

    


        return gameplaysessionstatRepository.save(gameplaysessionstat);
    }


    public GamePlaySessionStat update(Long id, GamePlaySessionStat gameplaysessionstatRequest) {
        GamePlaySessionStat existing = gameplaysessionstatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlaySessionStat not found"));

    // Copier les champs simples
        existing.setStatName(gameplaysessionstatRequest.getStatName());
        existing.setStatValue(gameplaysessionstatRequest.getStatValue());

// Relations ManyToOne : mise à jour conditionnelle
        if (gameplaysessionstatRequest.getGamePlaySession() != null &&
        gameplaysessionstatRequest.getGamePlaySession().getId() != null) {

        GamePlaySession existingGamePlaySession = gamePlaySessionRepository.findById(
        gameplaysessionstatRequest.getGamePlaySession().getId()
        ).orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

        existing.setGamePlaySession(existingGamePlaySession);
        } else {
        existing.setGamePlaySession(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return gameplaysessionstatRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<GamePlaySessionStat> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

GamePlaySessionStat entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getGamePlaySession() != null) {
        entity.setGamePlaySession(null);
        }
    


repository.delete(entity);
return true;
}
}