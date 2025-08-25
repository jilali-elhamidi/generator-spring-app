package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.repository.ReviewLikeRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewLikeService extends BaseService<ReviewLike> {

    protected final ReviewLikeRepository reviewlikeRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewLikeService(ReviewLikeRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewlikeRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewLike save(ReviewLike reviewlike) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reviewlike.getUser() != null) {
            if (reviewlike.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    reviewlike.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + reviewlike.getUser().getId()));
                reviewlike.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(reviewlike.getUser());
                reviewlike.setUser(newUser);
            }
        }
        
        if (reviewlike.getReview() != null) {
            if (reviewlike.getReview().getId() != null) {
                Review existingReview = reviewRepository.findById(
                    reviewlike.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("Review not found with id "
                    + reviewlike.getReview().getId()));
                reviewlike.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Review newReview = reviewRepository.save(reviewlike.getReview());
                reviewlike.setReview(newReview);
            }
        }
        
    // ---------- OneToOne ----------
    return reviewlikeRepository.save(reviewlike);
}


    public ReviewLike update(Long id, ReviewLike reviewlikeRequest) {
        ReviewLike existing = reviewlikeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewLike not found"));

    // Copier les champs simples
        existing.setLikeDate(reviewlikeRequest.getLikeDate());

    // ---------- Relations ManyToOne ----------
        if (reviewlikeRequest.getUser() != null &&
            reviewlikeRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reviewlikeRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (reviewlikeRequest.getReview() != null &&
            reviewlikeRequest.getReview().getId() != null) {

            Review existingReview = reviewRepository.findById(
                reviewlikeRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("Review not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reviewlikeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ReviewLike> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ReviewLike entity = entityOpt.get();
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
    @Transactional
    public List<ReviewLike> saveAll(List<ReviewLike> reviewlikeList) {

        return reviewlikeRepository.saveAll(reviewlikeList);
    }

}