package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.repository.ReviewLikeRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ReviewLikeService extends BaseService<ReviewLike> {

    protected final ReviewLikeRepository reviewlikeRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewLikeService(ReviewLikeRepository repository,UserProfileRepository userRepository,ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewlikeRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewLike save(ReviewLike reviewlike) {

        if (reviewlike.getUser() != null && reviewlike.getUser().getId() != null) {
        UserProfile user = userRepository.findById(reviewlike.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        reviewlike.setUser(user);
        }

        if (reviewlike.getReview() != null && reviewlike.getReview().getId() != null) {
        Review review = reviewRepository.findById(reviewlike.getReview().getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewlike.setReview(review);
        }

        return reviewlikeRepository.save(reviewlike);
    }


    public ReviewLike update(Long id, ReviewLike reviewlikeRequest) {
        ReviewLike existing = reviewlikeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewLike not found"));

    // Copier les champs simples
        existing.setLikeDate(reviewlikeRequest.getLikeDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (reviewlikeRequest.getUser() != null && reviewlikeRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(reviewlikeRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (reviewlikeRequest.getReview() != null && reviewlikeRequest.getReview().getId() != null) {
        Review review = reviewRepository.findById(reviewlikeRequest.getReview().getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        existing.setReview(review);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return reviewlikeRepository.save(existing);
    }
}