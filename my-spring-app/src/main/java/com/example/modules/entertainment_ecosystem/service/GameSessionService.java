package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.repository.GameSessionRepository;
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
public class GameSessionService extends BaseService<GameSession> {

    protected final GameSessionRepository gamesessionRepository;
    private final UserProfileRepository userRepository;
    private final VideoGameRepository gameRepository;

    public GameSessionService(GameSessionRepository repository, UserProfileRepository userRepository, VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamesessionRepository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameSession save(GameSession gamesession) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gamesession.getUser() != null) {
            if (gamesession.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    gamesession.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + gamesession.getUser().getId()));
                gamesession.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(gamesession.getUser());
                gamesession.setUser(newUser);
            }
        }
        
        if (gamesession.getGame() != null) {
            if (gamesession.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    gamesession.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gamesession.getGame().getId()));
                gamesession.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(gamesession.getGame());
                gamesession.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gamesessionRepository.save(gamesession);
}


    public GameSession update(Long id, GameSession gamesessionRequest) {
        GameSession existing = gamesessionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameSession not found"));

    // Copier les champs simples
        existing.setScore(gamesessionRequest.getScore());
        existing.setDurationMinutes(gamesessionRequest.getDurationMinutes());
        existing.setSessionDate(gamesessionRequest.getSessionDate());

    // ---------- Relations ManyToOne ----------
        if (gamesessionRequest.getUser() != null &&
            gamesessionRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamesessionRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gamesessionRequest.getGame() != null &&
            gamesessionRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gamesessionRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return gamesessionRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameSession> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameSession entity = entityOpt.get();
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
    @Transactional
    public List<GameSession> saveAll(List<GameSession> gamesessionList) {

        return gamesessionRepository.saveAll(gamesessionList);
    }

}