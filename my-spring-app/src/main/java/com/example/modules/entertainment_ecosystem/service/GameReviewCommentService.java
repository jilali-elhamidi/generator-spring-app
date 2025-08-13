package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameReviewCommentService extends BaseService<GameReviewComment> {

    protected final GameReviewCommentRepository gamereviewcommentRepository;
    private final UserProfileRepository userRepository;
    private final GameReviewRepository reviewRepository;
    private final GameReviewCommentRepository parentCommentRepository;

    public GameReviewCommentService(GameReviewCommentRepository repository,UserProfileRepository userRepository,GameReviewRepository reviewRepository,GameReviewCommentRepository parentCommentRepository)
    {
        super(repository);
        this.gamereviewcommentRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.parentCommentRepository = parentCommentRepository;
    }

    @Override
    public GameReviewComment save(GameReviewComment gamereviewcomment) {

        if (gamereviewcomment.getUser() != null && gamereviewcomment.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewcomment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        gamereviewcomment.setUser(user);
        }

        if (gamereviewcomment.getReview() != null && gamereviewcomment.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewcomment.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        gamereviewcomment.setReview(review);
        }

        if (gamereviewcomment.getParentComment() != null && gamereviewcomment.getParentComment().getId() != null) {
        GameReviewComment parentComment = parentCommentRepository.findById(gamereviewcomment.getParentComment().getId())
                .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
        gamereviewcomment.setParentComment(parentComment);
        }

        if (gamereviewcomment.getReplies() != null) {
            for (GameReviewComment item : gamereviewcomment.getReplies()) {
            item.setParentComment(gamereviewcomment);
            }
        }

        return gamereviewcommentRepository.save(gamereviewcomment);
    }


    public GameReviewComment update(Long id, GameReviewComment gamereviewcommentRequest) {
        GameReviewComment existing = gamereviewcommentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

    // Copier les champs simples
        existing.setCommentText(gamereviewcommentRequest.getCommentText());
        existing.setCommentDate(gamereviewcommentRequest.getCommentDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (gamereviewcommentRequest.getUser() != null && gamereviewcommentRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(gamereviewcommentRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (gamereviewcommentRequest.getReview() != null && gamereviewcommentRequest.getReview().getId() != null) {
        GameReview review = reviewRepository.findById(gamereviewcommentRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("GameReview not found"));
        existing.setReview(review);
        }

        if (gamereviewcommentRequest.getParentComment() != null && gamereviewcommentRequest.getParentComment().getId() != null) {
        GameReviewComment parentComment = parentCommentRepository.findById(gamereviewcommentRequest.getParentComment().getId())
                .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
        existing.setParentComment(parentComment);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getReplies().clear();
        if (gamereviewcommentRequest.getReplies() != null) {
            for (var item : gamereviewcommentRequest.getReplies()) {
            item.setParentComment(existing);
            existing.getReplies().add(item);
            }
        }

    

    

    

    


        return gamereviewcommentRepository.save(existing);
    }
}