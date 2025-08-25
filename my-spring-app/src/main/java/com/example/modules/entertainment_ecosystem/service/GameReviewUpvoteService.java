package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewUpvoteRepository;
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
public class GameReviewUpvoteService extends BaseService<GameReviewUpvote> {

    protected final GameReviewUpvoteRepository gamereviewupvoteRepository;
    private final UserProfileRepository userRepository;
    private final GameReviewRepository reviewRepository;
    private final VideoGameRepository gameRepository;

    public GameReviewUpvoteService(GameReviewUpvoteRepository repository, UserProfileRepository userRepository, GameReviewRepository reviewRepository, VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamereviewupvoteRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameReviewUpvote save(GameReviewUpvote gamereviewupvote) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gamereviewupvote.getUser() != null) {
            if (gamereviewupvote.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    gamereviewupvote.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + gamereviewupvote.getUser().getId()));
                gamereviewupvote.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(gamereviewupvote.getUser());
                gamereviewupvote.setUser(newUser);
            }
        }
        
        if (gamereviewupvote.getReview() != null) {
            if (gamereviewupvote.getReview().getId() != null) {
                GameReview existingReview = reviewRepository.findById(
                    gamereviewupvote.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("GameReview not found with id "
                    + gamereviewupvote.getReview().getId()));
                gamereviewupvote.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GameReview newReview = reviewRepository.save(gamereviewupvote.getReview());
                gamereviewupvote.setReview(newReview);
            }
        }
        
        if (gamereviewupvote.getGame() != null) {
            if (gamereviewupvote.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    gamereviewupvote.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gamereviewupvote.getGame().getId()));
                gamereviewupvote.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(gamereviewupvote.getGame());
                gamereviewupvote.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gamereviewupvoteRepository.save(gamereviewupvote);
}


    public GameReviewUpvote update(Long id, GameReviewUpvote gamereviewupvoteRequest) {
        GameReviewUpvote existing = gamereviewupvoteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

    // Copier les champs simples
        existing.setUpvoteDate(gamereviewupvoteRequest.getUpvoteDate());

    // ---------- Relations ManyToOne ----------
        if (gamereviewupvoteRequest.getUser() != null &&
            gamereviewupvoteRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamereviewupvoteRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gamereviewupvoteRequest.getReview() != null &&
            gamereviewupvoteRequest.getReview().getId() != null) {

            GameReview existingReview = reviewRepository.findById(
                gamereviewupvoteRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("GameReview not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
        if (gamereviewupvoteRequest.getGame() != null &&
            gamereviewupvoteRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gamereviewupvoteRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return gamereviewupvoteRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameReviewUpvote> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameReviewUpvote entity = entityOpt.get();
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
    @Transactional
    public List<GameReviewUpvote> saveAll(List<GameReviewUpvote> gamereviewupvoteList) {

        return gamereviewupvoteRepository.saveAll(gamereviewupvoteList);
    }

}