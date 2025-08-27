package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.repository.GameReviewRepository;

import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;

import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GameReviewCommentService extends BaseService<GameReviewComment> {

    protected final GameReviewCommentRepository gamereviewcommentRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final GameReviewRepository reviewRepository;
    
    protected final GameReviewCommentRepository repliesRepository;
    
    protected final GameReviewCommentRepository parentCommentRepository;
    

    public GameReviewCommentService(GameReviewCommentRepository repository, UserProfileRepository userRepository, GameReviewRepository reviewRepository, GameReviewCommentRepository repliesRepository, GameReviewCommentRepository parentCommentRepository)
    {
        super(repository);
        this.gamereviewcommentRepository = repository;
        
        this.userRepository = userRepository;
        
        this.reviewRepository = reviewRepository;
        
        this.repliesRepository = repliesRepository;
        
        this.parentCommentRepository = parentCommentRepository;
        
    }

    @Transactional
    @Override
    public GameReviewComment save(GameReviewComment gamereviewcomment) {
    // ---------- OneToMany ----------
        if (gamereviewcomment.getReplies() != null) {
            List<GameReviewComment> managedReplies = new ArrayList<>();
            for (GameReviewComment item : gamereviewcomment.getReplies()) {
                if (item.getId() != null) {
                    GameReviewComment existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

                     existingItem.setParentComment(gamereviewcomment);
                     managedReplies.add(existingItem);
                } else {
                    item.setParentComment(gamereviewcomment);
                    managedReplies.add(item);
                }
            }
            gamereviewcomment.setReplies(managedReplies);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gamereviewcomment.getUser() != null) {
            if (gamereviewcomment.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    gamereviewcomment.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + gamereviewcomment.getUser().getId()));
                gamereviewcomment.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(gamereviewcomment.getUser());
                gamereviewcomment.setUser(newUser);
            }
        }
        
        if (gamereviewcomment.getReview() != null) {
            if (gamereviewcomment.getReview().getId() != null) {
                GameReview existingReview = reviewRepository.findById(
                    gamereviewcomment.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("GameReview not found with id "
                    + gamereviewcomment.getReview().getId()));
                gamereviewcomment.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GameReview newReview = reviewRepository.save(gamereviewcomment.getReview());
                gamereviewcomment.setReview(newReview);
            }
        }
        
        if (gamereviewcomment.getParentComment() != null) {
            if (gamereviewcomment.getParentComment().getId() != null) {
                GameReviewComment existingParentComment = parentCommentRepository.findById(
                    gamereviewcomment.getParentComment().getId()
                ).orElseThrow(() -> new RuntimeException("GameReviewComment not found with id "
                    + gamereviewcomment.getParentComment().getId()));
                gamereviewcomment.setParentComment(existingParentComment);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GameReviewComment newParentComment = parentCommentRepository.save(gamereviewcomment.getParentComment());
                gamereviewcomment.setParentComment(newParentComment);
            }
        }
        
    // ---------- OneToOne ----------
    return gamereviewcommentRepository.save(gamereviewcomment);
}

    @Transactional
    @Override
    public GameReviewComment update(Long id, GameReviewComment gamereviewcommentRequest) {
        GameReviewComment existing = gamereviewcommentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

    // Copier les champs simples
        existing.setCommentText(gamereviewcommentRequest.getCommentText());
        existing.setCommentDate(gamereviewcommentRequest.getCommentDate());

    // ---------- Relations ManyToOne ----------
        if (gamereviewcommentRequest.getUser() != null &&
            gamereviewcommentRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                gamereviewcommentRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (gamereviewcommentRequest.getReview() != null &&
            gamereviewcommentRequest.getReview().getId() != null) {

            GameReview existingReview = reviewRepository.findById(
                gamereviewcommentRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("GameReview not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
        if (gamereviewcommentRequest.getParentComment() != null &&
            gamereviewcommentRequest.getParentComment().getId() != null) {

            GameReviewComment existingParentComment = parentCommentRepository.findById(
                gamereviewcommentRequest.getParentComment().getId()
            ).orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

            existing.setParentComment(existingParentComment);
        } else {
            existing.setParentComment(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getReplies().clear();

        if (gamereviewcommentRequest.getReplies() != null) {
            for (var item : gamereviewcommentRequest.getReplies()) {
                GameReviewComment existingItem;
                if (item.getId() != null) {
                    existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setParentComment(existing);
                existing.getReplies().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gamereviewcommentRepository.save(existing);
}

    // Pagination simple
    public Page<GameReviewComment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GameReviewComment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GameReviewComment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GameReviewComment> saveAll(List<GameReviewComment> gamereviewcommentList) {
        return super.saveAll(gamereviewcommentList);
    }

}