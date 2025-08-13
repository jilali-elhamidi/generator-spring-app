package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (gameplaysession.getUser() != null && gameplaysession.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gameplaysession.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gameplaysession.setUser(user);
        }

        if (gameplaysession.getGame() != null && gameplaysession.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gameplaysession.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gameplaysession.setGame(game);
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

        if (gameplaysessionRequest.getUser() != null && gameplaysessionRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gameplaysessionRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gameplaysessionRequest.getGame() != null && gameplaysessionRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gameplaysessionRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return gameplaysessionRepository.save(existing);
    }
}