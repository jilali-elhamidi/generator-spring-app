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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameReviewDownvoteService extends BaseService<GameReviewDownvote> {

    protected final GameReviewDownvoteRepository gamereviewdownvoteRepository;
    private final UserProfileRepository userRepository;
    private final GameReviewRepository reviewRepository;
    private final VideoGameRepository gameRepository;

    public GameReviewDownvoteService(GameReviewDownvoteRepository repository,UserProfileRepository userRepository,GameReviewRepository reviewRepository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamereviewdownvoteRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameReviewDownvote save(GameReviewDownvote gamereviewdownvote) {

        if (gamereviewdownvote.getUser() != null && gamereviewdownvote.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewdownvote.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamereviewdownvote.setUser(user);
        }

        if (gamereviewdownvote.getReview() != null && gamereviewdownvote.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewdownvote.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        gamereviewdownvote.setReview(review);
        }

        if (gamereviewdownvote.getGame() != null && gamereviewdownvote.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereviewdownvote.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gamereviewdownvote.setGame(game);
        }

        return gamereviewdownvoteRepository.save(gamereviewdownvote);
    }


    public GameReviewDownvote update(Long id, GameReviewDownvote gamereviewdownvoteRequest) {
        GameReviewDownvote existing = gamereviewdownvoteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

    // Copier les champs simples
        existing.setDownvoteDate(gamereviewdownvoteRequest.getDownvoteDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (gamereviewdownvoteRequest.getUser() != null && gamereviewdownvoteRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewdownvoteRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gamereviewdownvoteRequest.getReview() != null && gamereviewdownvoteRequest.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewdownvoteRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        existing.setReview(review);
        }

        if (gamereviewdownvoteRequest.getGame() != null && gamereviewdownvoteRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereviewdownvoteRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return gamereviewdownvoteRepository.save(existing);
    }
}