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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameReviewUpvoteService extends BaseService<GameReviewUpvote> {

    protected final GameReviewUpvoteRepository gamereviewupvoteRepository;
    private final UserProfileRepository userRepository;
    private final GameReviewRepository reviewRepository;
    private final VideoGameRepository gameRepository;

    public GameReviewUpvoteService(GameReviewUpvoteRepository repository,UserProfileRepository userRepository,GameReviewRepository reviewRepository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamereviewupvoteRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameReviewUpvote save(GameReviewUpvote gamereviewupvote) {

        if (gamereviewupvote.getUser() != null && gamereviewupvote.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewupvote.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamereviewupvote.setUser(user);
        }

        if (gamereviewupvote.getReview() != null && gamereviewupvote.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewupvote.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        gamereviewupvote.setReview(review);
        }

        if (gamereviewupvote.getGame() != null && gamereviewupvote.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereviewupvote.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gamereviewupvote.setGame(game);
        }

        return gamereviewupvoteRepository.save(gamereviewupvote);
    }


    public GameReviewUpvote update(Long id, GameReviewUpvote gamereviewupvoteRequest) {
        GameReviewUpvote existing = gamereviewupvoteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

    // Copier les champs simples
        existing.setUpvoteDate(gamereviewupvoteRequest.getUpvoteDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (gamereviewupvoteRequest.getUser() != null && gamereviewupvoteRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewupvoteRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gamereviewupvoteRequest.getReview() != null && gamereviewupvoteRequest.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewupvoteRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        existing.setReview(review);
        }

        if (gamereviewupvoteRequest.getGame() != null && gamereviewupvoteRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereviewupvoteRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return gamereviewupvoteRepository.save(existing);
    }
}