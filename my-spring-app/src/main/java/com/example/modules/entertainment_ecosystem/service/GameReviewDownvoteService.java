package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewDownvoteRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;

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

    public GameReviewDownvoteService(GameReviewDownvoteRepository repository,UserProfileRepository userRepository,GameReviewRepository reviewRepository)
    {
        super(repository);
        this.gamereviewdownvoteRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public GameReviewDownvote save(GameReviewDownvote gamereviewdownvote) {


    

    

    if (gamereviewdownvote.getUser() != null
        && gamereviewdownvote.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        gamereviewdownvote.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamereviewdownvote.setUser(existingUser);
        }
    
    if (gamereviewdownvote.getReview() != null
        && gamereviewdownvote.getReview().getId() != null) {
        GameReview existingReview = reviewRepository.findById(
        gamereviewdownvote.getReview().getId()
        ).orElseThrow(() -> new RuntimeException("GameReview not found"));
        gamereviewdownvote.setReview(existingReview);
        }
    

    

    


        return gamereviewdownvoteRepository.save(gamereviewdownvote);
    }


    public GameReviewDownvote update(Long id, GameReviewDownvote gamereviewdownvoteRequest) {
        GameReviewDownvote existing = gamereviewdownvoteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

    // Copier les champs simples
        existing.setDownvoteDate(gamereviewdownvoteRequest.getDownvoteDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


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
    


repository.delete(entity);
return true;
}
}