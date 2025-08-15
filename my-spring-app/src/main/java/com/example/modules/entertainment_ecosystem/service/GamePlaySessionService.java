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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GamePlaySessionService extends BaseService<GamePlaySession> {

    protected final GamePlaySessionRepository gameplaysessionRepository;
    private final UserProfileRepository userRepository;
    private final VideoGameRepository gameRepository;

    public GamePlaySessionService(GamePlaySessionRepository repository,UserProfileRepository userRepository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.gameplaysessionRepository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GamePlaySession save(GamePlaySession gameplaysession) {


    

    

    if (gameplaysession.getUser() != null
        && gameplaysession.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        gameplaysession.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gameplaysession.setUser(existingUser);
        }
    
    if (gameplaysession.getGame() != null
        && gameplaysession.getGame().getId() != null) {
        VideoGame existingGame = gameRepository.findById(
        gameplaysession.getGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gameplaysession.setGame(existingGame);
        }
    

    

    


        return gameplaysessionRepository.save(gameplaysession);
    }


    public GamePlaySession update(Long id, GamePlaySession gameplaysessionRequest) {
        GamePlaySession existing = gameplaysessionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

    // Copier les champs simples
        existing.setStartTime(gameplaysessionRequest.getStartTime());
        existing.setEndTime(gameplaysessionRequest.getEndTime());
        existing.setScore(gameplaysessionRequest.getScore());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return gameplaysessionRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<GamePlaySession> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

GamePlaySession entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getGame() != null) {
        entity.setGame(null);
        }
    


repository.delete(entity);
return true;
}
}