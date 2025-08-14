package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.repository.ReviewRatingRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewRatingService extends BaseService<ReviewRating> {

    protected final ReviewRatingRepository reviewratingRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewRatingService(ReviewRatingRepository repository,UserProfileRepository userRepository,ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewratingRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewRating save(ReviewRating reviewrating) {


    

    

    if (reviewrating.getUser() != null
        && reviewrating.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        reviewrating.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        reviewrating.setUser(existingUser);
        }
    
    if (reviewrating.getReview() != null
        && reviewrating.getReview().getId() != null) {
        Review existingReview = reviewRepository.findById(
        reviewrating.getReview().getId()
        ).orElseThrow(() -> new RuntimeException("Review not found"));
        reviewrating.setReview(existingReview);
        }
    

        return reviewratingRepository.save(reviewrating);
    }


    public ReviewRating update(Long id, ReviewRating reviewratingRequest) {
        ReviewRating existing = reviewratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewRating not found"));

    // Copier les champs simples
        existing.setScore(reviewratingRequest.getScore());
        existing.setRatingDate(reviewratingRequest.getRatingDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return reviewratingRepository.save(existing);
    }


}