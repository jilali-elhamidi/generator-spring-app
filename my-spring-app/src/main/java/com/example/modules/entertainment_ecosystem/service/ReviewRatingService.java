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

        if (reviewrating.getUser() != null && reviewrating.getUser().getId() != null) {
        UserProfile user = userRepository.findById(reviewrating.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        reviewrating.setUser(user);
        }

        if (reviewrating.getReview() != null && reviewrating.getReview().getId() != null) {
        Review review = reviewRepository.findById(reviewrating.getReview().getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewrating.setReview(review);
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

        if (reviewratingRequest.getUser() != null && reviewratingRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(reviewratingRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (reviewratingRequest.getReview() != null && reviewratingRequest.getReview().getId() != null) {
        Review review = reviewRepository.findById(reviewratingRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        existing.setReview(review);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return reviewratingRepository.save(existing);
    }
}