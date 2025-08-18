package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.repository.ReviewCommentRepository;
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
public class ReviewCommentService extends BaseService<ReviewComment> {

    protected final ReviewCommentRepository reviewcommentRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewCommentService(ReviewCommentRepository repository,UserProfileRepository userRepository,ReviewRepository reviewRepository)
    {
        super(repository);
        this.reviewcommentRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewComment save(ReviewComment reviewcomment) {


    

    

    if (reviewcomment.getUser() != null
        && reviewcomment.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        reviewcomment.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        reviewcomment.setUser(existingUser);
        }
    
    if (reviewcomment.getReview() != null
        && reviewcomment.getReview().getId() != null) {
        Review existingReview = reviewRepository.findById(
        reviewcomment.getReview().getId()
        ).orElseThrow(() -> new RuntimeException("Review not found"));
        reviewcomment.setReview(existingReview);
        }
    

        return reviewcommentRepository.save(reviewcomment);
    }


    public ReviewComment update(Long id, ReviewComment reviewcommentRequest) {
        ReviewComment existing = reviewcommentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReviewComment not found"));

    // Copier les champs simples
        existing.setCommentText(reviewcommentRequest.getCommentText());
        existing.setCommentDate(reviewcommentRequest.getCommentDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return reviewcommentRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ReviewComment> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ReviewComment entity = entityOpt.get();

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
}