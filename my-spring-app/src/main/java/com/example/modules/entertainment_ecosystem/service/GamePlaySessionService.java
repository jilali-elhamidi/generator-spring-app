package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GamePlaySessionService extends BaseService<GamePlaySession> {

    protected final GamePlaySessionRepository gameplaysessionRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final VideoGameRepository gameRepository;
    

    public GamePlaySessionService(GamePlaySessionRepository repository, UserProfileRepository userRepository, VideoGameRepository gameRepository)
    {
        super(repository);
        this.gameplaysessionRepository = repository;
        
        this.userRepository = userRepository;
        
        this.gameRepository = gameRepository;
        
    }

    @Transactional
    @Override
    public GamePlaySession save(GamePlaySession gameplaysession) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gameplaysession.getUser() != null) {
            if (gameplaysession.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    gameplaysession.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + gameplaysession.getUser().getId()));
                gameplaysession.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(gameplaysession.getUser());
                gameplaysession.setUser(newUser);
            }
        }
        
        if (gameplaysession.getGame() != null) {
            if (gameplaysession.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    gameplaysession.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gameplaysession.getGame().getId()));
                gameplaysession.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(gameplaysession.getGame());
                gameplaysession.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gameplaysessionRepository.save(gameplaysession);
}

    @Transactional
    @Override
    public GamePlaySession update(Long id, GamePlaySession gameplaysessionRequest) {
        GamePlaySession existing = gameplaysessionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

    // Copier les champs simples
        existing.setStartTime(gameplaysessionRequest.getStartTime());
        existing.setEndTime(gameplaysessionRequest.getEndTime());
        existing.setScore(gameplaysessionRequest.getScore());

    // ---------- Relations ManyToOne ----------
        if (gameplaysessionRequest.getUser() != null &&
            gameplaysessionRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gameplaysessionRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gameplaysessionRequest.getGame() != null &&
            gameplaysessionRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gameplaysessionRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return gameplaysessionRepository.save(existing);
}

    // Pagination simple
    public Page<GamePlaySession> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GamePlaySession> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GamePlaySession.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GamePlaySession> saveAll(List<GamePlaySession> gameplaysessionList) {
        return super.saveAll(gameplaysessionList);
    }

}