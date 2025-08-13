package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.repository.GameSessionRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameSessionService extends BaseService<GameSession> {

    protected final GameSessionRepository gamesessionRepository;
    private final UserProfileRepository userRepository;
    private final VideoGameRepository gameRepository;

    public GameSessionService(GameSessionRepository repository,UserProfileRepository userRepository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamesessionRepository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameSession save(GameSession gamesession) {

        if (gamesession.getUser() != null && gamesession.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamesession.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamesession.setUser(user);
        }

        if (gamesession.getGame() != null && gamesession.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamesession.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gamesession.setGame(game);
        }

        return gamesessionRepository.save(gamesession);
    }


    public GameSession update(Long id, GameSession gamesessionRequest) {
        GameSession existing = gamesessionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameSession not found"));

    // Copier les champs simples
        existing.setScore(gamesessionRequest.getScore());
        existing.setDurationMinutes(gamesessionRequest.getDurationMinutes());
        existing.setSessionDate(gamesessionRequest.getSessionDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (gamesessionRequest.getUser() != null && gamesessionRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamesessionRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gamesessionRequest.getGame() != null && gamesessionRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamesessionRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return gamesessionRepository.save(existing);
    }
}