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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ReviewCommentService extends BaseService<ReviewComment> {

    protected final ReviewCommentRepository reviewcommentRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final ReviewRepository reviewRepository;
    
    protected final ReviewReplyRepository repliesRepository;
    

    public ReviewCommentService(ReviewCommentRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository, ReviewReplyRepository repliesRepository)
    {
        super(repository);
        this.reviewcommentRepository = repository;
        
        this.userRepository = userRepository;
        
        this.reviewRepository = reviewRepository;
        
        this.repliesRepository = repliesRepository;
        
    }

    @Transactional
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
        if (reviewcomment.getUser() != null) {
            if (reviewcomment.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    reviewcomment.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + reviewcomment.getUser().getId()));
                reviewcomment.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(reviewcomment.getUser());
                reviewcomment.setUser(newUser);
            }
        }
        
        if (reviewcomment.getReview() != null) {
            if (reviewcomment.getReview().getId() != null) {
                Review existingReview = reviewRepository.findById(
                    reviewcomment.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("Review not found with id "
                    + reviewcomment.getReview().getId()));
                reviewcomment.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Review newReview = reviewRepository.save(reviewcomment.getReview());
                reviewcomment.setReview(newReview);
            }
        }
        
    // ---------- OneToOne ----------
    return reviewcommentRepository.save(reviewcomment);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<ReviewComment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ReviewComment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ReviewComment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ReviewComment> saveAll(List<ReviewComment> reviewcommentList) {
        return super.saveAll(reviewcommentList);
    }

}