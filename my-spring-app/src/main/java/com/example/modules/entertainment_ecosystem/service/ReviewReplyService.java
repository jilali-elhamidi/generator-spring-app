package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.repository.ReviewReplyRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.repository.ReviewCommentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewReplyService extends BaseService<ReviewReply> {

    protected final ReviewReplyRepository reviewreplyRepository;
    private final UserProfileRepository userRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewReplyService(ReviewReplyRepository repository, UserProfileRepository userRepository, ReviewCommentRepository reviewCommentRepository)
    {
        super(repository);
        this.reviewreplyRepository = repository;
        this.userRepository = userRepository;
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public ReviewReply save(ReviewReply reviewreply) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reviewreply.getUser() != null) {
            if (reviewreply.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    reviewreply.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + reviewreply.getUser().getId()));
                reviewreply.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(reviewreply.getUser());
                reviewreply.setUser(newUser);
            }
        }
        
        if (reviewreply.getReviewComment() != null) {
            if (reviewreply.getReviewComment().getId() != null) {
                ReviewComment existingReviewComment = reviewCommentRepository.findById(
                    reviewreply.getReviewComment().getId()
                ).orElseThrow(() -> new RuntimeException("ReviewComment not found with id "
                    + reviewreply.getReviewComment().getId()));
                reviewreply.setReviewComment(existingReviewComment);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ReviewComment newReviewComment = reviewCommentRepository.save(reviewreply.getReviewComment());
                reviewreply.setReviewComment(newReviewComment);
            }
        }
        
    // ---------- OneToOne ----------
    return reviewreplyRepository.save(reviewreply);
}


    public ReviewReply update(Long id, ReviewReply reviewreplyRequest) {
        ReviewReply existing = reviewreplyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewReply not found"));

    // Copier les champs simples
        existing.setCommentText(reviewreplyRequest.getCommentText());
        existing.setReplyDate(reviewreplyRequest.getReplyDate());

    // ---------- Relations ManyToOne ----------
        if (reviewreplyRequest.getUser() != null &&
            reviewreplyRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reviewreplyRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (reviewreplyRequest.getReviewComment() != null &&
            reviewreplyRequest.getReviewComment().getId() != null) {

            ReviewComment existingReviewComment = reviewCommentRepository.findById(
                reviewreplyRequest.getReviewComment().getId()
            ).orElseThrow(() -> new RuntimeException("ReviewComment not found"));

            existing.setReviewComment(existingReviewComment);
        } else {
            existing.setReviewComment(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reviewreplyRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ReviewReply> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ReviewReply entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getReviewComment() != null) {
            entity.setReviewComment(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<ReviewReply> saveAll(List<ReviewReply> reviewreplyList) {

        return reviewreplyRepository.saveAll(reviewreplyList);
    }

}