package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.repository.ReviewCommentRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.repository.ReviewReplyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewCommentService extends BaseService<ReviewComment> {

    protected final ReviewCommentRepository reviewcommentRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository repliesRepository;

    public ReviewCommentService(ReviewCommentRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository, ReviewReplyRepository repliesRepository)
    {
        super(repository);
        this.reviewcommentRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.repliesRepository = repliesRepository;
    }

    @Override
    public ReviewComment save(ReviewComment reviewcomment) {
    // ---------- OneToMany ----------
        if (reviewcomment.getReplies() != null) {
            List<ReviewReply> managedReplies = new ArrayList<>();
            for (ReviewReply item : reviewcomment.getReplies()) {
                if (item.getId() != null) {
                    ReviewReply existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewReply not found"));

                     existingItem.setReviewComment(reviewcomment);
                     managedReplies.add(existingItem);
                } else {
                    item.setReviewComment(reviewcomment);
                    managedReplies.add(item);
                }
            }
            reviewcomment.setReplies(managedReplies);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reviewcomment.getUser() != null &&
            reviewcomment.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reviewcomment.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            reviewcomment.setUser(existingUser);
        }
        
        if (reviewcomment.getReview() != null &&
            reviewcomment.getReview().getId() != null) {

            Review existingReview = reviewRepository.findById(
                reviewcomment.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("Review not found"));

            reviewcomment.setReview(existingReview);
        }
        
    // ---------- OneToOne ----------

    return reviewcommentRepository.save(reviewcomment);
}


    public ReviewComment update(Long id, ReviewComment reviewcommentRequest) {
        ReviewComment existing = reviewcommentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewComment not found"));

    // Copier les champs simples
        existing.setCommentText(reviewcommentRequest.getCommentText());
        existing.setCommentDate(reviewcommentRequest.getCommentDate());

    // ---------- Relations ManyToOne ----------
        if (reviewcommentRequest.getUser() != null &&
            reviewcommentRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reviewcommentRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (reviewcommentRequest.getReview() != null &&
            reviewcommentRequest.getReview().getId() != null) {

            Review existingReview = reviewRepository.findById(
                reviewcommentRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("Review not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getReplies().clear();

        if (reviewcommentRequest.getReplies() != null) {
            for (var item : reviewcommentRequest.getReplies()) {
                ReviewReply existingItem;
                if (item.getId() != null) {
                    existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewReply not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReviewComment(existing);
                existing.getReplies().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return reviewcommentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ReviewComment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ReviewComment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getReplies() != null) {
            for (var child : entity.getReplies()) {
                
                child.setReviewComment(null); // retirer la référence inverse
                
            }
            entity.getReplies().clear();
        }
        
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