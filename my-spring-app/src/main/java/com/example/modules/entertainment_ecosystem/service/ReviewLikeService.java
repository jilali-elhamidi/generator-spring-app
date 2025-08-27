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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ReviewLikeService extends BaseService<ReviewLike> {

    protected final ReviewLikeRepository reviewlikeRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final ReviewRepository reviewRepository;
    

    public ReviewLikeService(ReviewLikeRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewlikeRepository = repository;
        
        this.userRepository = userRepository;
        
        this.reviewRepository = reviewRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reviewlikeRepository.save(existing);
}

    // Pagination simple
    public Page<ReviewLike> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ReviewLike> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ReviewLike.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ReviewLike> saveAll(List<ReviewLike> reviewlikeList) {
        return super.saveAll(reviewlikeList);
    }

}