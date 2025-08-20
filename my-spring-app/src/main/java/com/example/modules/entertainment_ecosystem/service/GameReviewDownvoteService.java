package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewDownvoteRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameReviewDownvoteService extends BaseService<GameReviewDownvote> {

    protected final GameReviewDownvoteRepository gamereviewdownvoteRepository;
    private final UserProfileRepository userRepository;
    private final GameReviewRepository reviewRepository;
    private final VideoGameRepository gameRepository;

    public GameReviewDownvoteService(GameReviewDownvoteRepository repository, UserProfileRepository userRepository, GameReviewRepository reviewRepository, VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamereviewdownvoteRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameReviewDownvote save(GameReviewDownvote gamereviewdownvote) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gamereviewdownvote.getUser() != null &&
            gamereviewdownvote.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamereviewdownvote.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            gamereviewdownvote.setUser(existingUser);
        }
        
        if (gamereviewdownvote.getReview() != null &&
            gamereviewdownvote.getReview().getId() != null) {

            GameReview existingReview = reviewRepository.findById(
                gamereviewdownvote.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("GameReview not found"));

            gamereviewdownvote.setReview(existingReview);
        }
        
        if (gamereviewdownvote.getGame() != null &&
            gamereviewdownvote.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gamereviewdownvote.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            gamereviewdownvote.setGame(existingGame);
        }
        
    // ---------- OneToOne ----------

    return gamereviewdownvoteRepository.save(gamereviewdownvote);
}


    public GameReviewDownvote update(Long id, GameReviewDownvote gamereviewdownvoteRequest) {
        GameReviewDownvote existing = gamereviewdownvoteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

    // Copier les champs simples
        existing.setDownvoteDate(gamereviewdownvoteRequest.getDownvoteDate());

    // ---------- Relations ManyToOne ----------
        if (gamereviewdownvoteRequest.getUser() != null &&
            gamereviewdownvoteRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamereviewdownvoteRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gamereviewdownvoteRequest.getReview() != null &&
            gamereviewdownvoteRequest.getReview().getId() != null) {

            GameReview existingReview = reviewRepository.findById(
                gamereviewdownvoteRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("GameReview not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
        if (gamereviewdownvoteRequest.getGame() != null &&
            gamereviewdownvoteRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gamereviewdownvoteRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return gamereviewdownvoteRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameReviewDownvote> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameReviewDownvote entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getReview() != null) {
            entity.setReview(null);
        }
        
        if (entity.getGame() != null) {
            entity.setGame(null);
        }
        
        repository.delete(entity);
        return true;
    }
}