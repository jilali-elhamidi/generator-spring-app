package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.repository.ReviewRatingRepository;

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
public class ReviewRatingService extends BaseService<ReviewRating> {

    protected final ReviewRatingRepository reviewratingRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final ReviewRepository reviewRepository;
    

    public ReviewRatingService(ReviewRatingRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewratingRepository = repository;
        
        this.userRepository = userRepository;
        
        this.reviewRepository = reviewRepository;
        
    }

    @Transactional
    @Override
    public ReviewRating save(ReviewRating reviewrating) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reviewrating.getUser() != null) {
            if (reviewrating.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    reviewrating.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + reviewrating.getUser().getId()));
                reviewrating.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(reviewrating.getUser());
                reviewrating.setUser(newUser);
            }
        }
        
        if (reviewrating.getReview() != null) {
            if (reviewrating.getReview().getId() != null) {
                Review existingReview = reviewRepository.findById(
                    reviewrating.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("Review not found with id "
                    + reviewrating.getReview().getId()));
                reviewrating.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Review newReview = reviewRepository.save(reviewrating.getReview());
                reviewrating.setReview(newReview);
            }
        }
        
    // ---------- OneToOne ----------
    return reviewratingRepository.save(reviewrating);
}

    @Transactional
    @Override
    public ReviewRating update(Long id, ReviewRating reviewratingRequest) {
        ReviewRating existing = reviewratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewRating not found"));

    // Copier les champs simples
        existing.setScore(reviewratingRequest.getScore());
        existing.setRatingDate(reviewratingRequest.getRatingDate());

    // ---------- Relations ManyToOne ----------
        if (reviewratingRequest.getUser() != null &&
            reviewratingRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reviewratingRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (reviewratingRequest.getReview() != null &&
            reviewratingRequest.getReview().getId() != null) {

            Review existingReview = reviewRepository.findById(
                reviewratingRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("Review not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reviewratingRepository.save(existing);
}

    // Pagination simple
    public Page<ReviewRating> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ReviewRating> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ReviewRating.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ReviewRating> saveAll(List<ReviewRating> reviewratingList) {
        return super.saveAll(reviewratingList);
    }

}