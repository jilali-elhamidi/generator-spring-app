package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameReviewService extends BaseService<GameReview> {

    protected final GameReviewRepository gamereviewRepository;
    private final UserProfileRepository userRepository;
    private final VideoGameRepository gameRepository;

    public GameReviewService(GameReviewRepository repository,UserProfileRepository userRepository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.gamereviewRepository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameReview save(GameReview gamereview) {

        if (gamereview.getUser() != null && gamereview.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereview.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamereview.setUser(user);
        }

        if (gamereview.getGame() != null && gamereview.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereview.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gamereview.setGame(game);
        }

        return gamereviewRepository.save(gamereview);
    }


    public GameReview update(Long id, GameReview gamereviewRequest) {
        GameReview existing = gamereviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReview not found"));

    // Copier les champs simples
        existing.setRating(gamereviewRequest.getRating());
        existing.setComment(gamereviewRequest.getComment());

// Relations ManyToOne : mise à jour conditionnelle

        if (gamereviewRequest.getUser() != null && gamereviewRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gamereviewRequest.getGame() != null && gamereviewRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gamereviewRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return gamereviewRepository.save(existing);
    }
}