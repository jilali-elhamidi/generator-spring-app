package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.repository.ReviewCommentRepository;
import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.repository.MediaFileRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.repository.ReviewRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.repository.ReviewLikeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewService extends BaseService<Review> {

    protected final ReviewRepository reviewRepository;
    private final UserProfileRepository userRepository;
    private final MovieRepository movieRepository;
    private final BookRepository bookRepository;
    private final VideoGameRepository videoGameRepository;
    private final ReviewCommentRepository reviewCommentsRepository;
    private final MediaFileRepository mediaFileRepository;
    private final ReviewRatingRepository ratingsRepository;
    private final ReviewLikeRepository likesRepository;

    public ReviewService(ReviewRepository repository,UserProfileRepository userRepository,MovieRepository movieRepository,BookRepository bookRepository,VideoGameRepository videoGameRepository,ReviewCommentRepository reviewCommentsRepository,MediaFileRepository mediaFileRepository,ReviewRatingRepository ratingsRepository,ReviewLikeRepository likesRepository)
    {
        super(repository);
        this.reviewRepository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.bookRepository = bookRepository;
        this.videoGameRepository = videoGameRepository;
        this.reviewCommentsRepository = reviewCommentsRepository;
        this.mediaFileRepository = mediaFileRepository;
        this.ratingsRepository = ratingsRepository;
        this.likesRepository = likesRepository;
    }

    @Override
    public Review save(Review review) {


    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (review.getReviewComments() != null) {
            List<ReviewComment> managedReviewComments = new ArrayList<>();
            for (ReviewComment item : review.getReviewComments()) {
            if (item.getId() != null) {
            ReviewComment existingItem = reviewCommentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ReviewComment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setReview(review);
            managedReviewComments.add(existingItem);
            } else {
            item.setReview(review);
            managedReviewComments.add(item);
            }
            }
            review.setReviewComments(managedReviewComments);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (review.getRatings() != null) {
            List<ReviewRating> managedRatings = new ArrayList<>();
            for (ReviewRating item : review.getRatings()) {
            if (item.getId() != null) {
            ReviewRating existingItem = ratingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setReview(review);
            managedRatings.add(existingItem);
            } else {
            item.setReview(review);
            managedRatings.add(item);
            }
            }
            review.setRatings(managedRatings);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (review.getLikes() != null) {
            List<ReviewLike> managedLikes = new ArrayList<>();
            for (ReviewLike item : review.getLikes()) {
            if (item.getId() != null) {
            ReviewLike existingItem = likesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setReview(review);
            managedLikes.add(existingItem);
            } else {
            item.setReview(review);
            managedLikes.add(item);
            }
            }
            review.setLikes(managedLikes);
            }
        
    


    

    

    

    

    

    

    

    

    if (review.getUser() != null
        && review.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        review.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        review.setUser(existingUser);
        }
    
    if (review.getMovie() != null
        && review.getMovie().getId() != null) {
        Movie existingMovie = movieRepository.findById(
        review.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));
        review.setMovie(existingMovie);
        }
    
    if (review.getBook() != null
        && review.getBook().getId() != null) {
        Book existingBook = bookRepository.findById(
        review.getBook().getId()
        ).orElseThrow(() -> new RuntimeException("Book not found"));
        review.setBook(existingBook);
        }
    
    if (review.getVideoGame() != null
        && review.getVideoGame().getId() != null) {
        VideoGame existingVideoGame = videoGameRepository.findById(
        review.getVideoGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));
        review.setVideoGame(existingVideoGame);
        }
    
    
    
    
    
        if (review.getMediaFile() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            review.setMediaFile(
            mediaFileRepository.findById(review.getMediaFile().getId())
            .orElseThrow(() -> new RuntimeException("mediaFile not found"))
            );
        
        review.getMediaFile().setReview(review);
        }

        return reviewRepository.save(review);
    }


    public Review update(Long id, Review reviewRequest) {
        Review existing = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));

    // Copier les champs simples
        existing.setRating(reviewRequest.getRating());
        existing.setComment(reviewRequest.getComment());
        existing.setReviewDate(reviewRequest.getReviewDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (reviewRequest.getUser() != null &&
        reviewRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        reviewRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }
        if (reviewRequest.getMovie() != null &&
        reviewRequest.getMovie().getId() != null) {

        Movie existingMovie = movieRepository.findById(
        reviewRequest.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setMovie(existingMovie);
        } else {
        existing.setMovie(null);
        }
        if (reviewRequest.getBook() != null &&
        reviewRequest.getBook().getId() != null) {

        Book existingBook = bookRepository.findById(
        reviewRequest.getBook().getId()
        ).orElseThrow(() -> new RuntimeException("Book not found"));

        existing.setBook(existingBook);
        } else {
        existing.setBook(null);
        }
        if (reviewRequest.getVideoGame() != null &&
        reviewRequest.getVideoGame().getId() != null) {

        VideoGame existingVideoGame = videoGameRepository.findById(
        reviewRequest.getVideoGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

        existing.setVideoGame(existingVideoGame);
        } else {
        existing.setVideoGame(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getReviewComments().clear();

        if (reviewRequest.getReviewComments() != null) {
        for (var item : reviewRequest.getReviewComments()) {
        ReviewComment existingItem;
        if (item.getId() != null) {
        existingItem = reviewCommentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewComment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setReview(existing);

        // Ajouter directement dans la collection existante
        existing.getReviewComments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getRatings().clear();

        if (reviewRequest.getRatings() != null) {
        for (var item : reviewRequest.getRatings()) {
        ReviewRating existingItem;
        if (item.getId() != null) {
        existingItem = ratingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setReview(existing);

        // Ajouter directement dans la collection existante
        existing.getRatings().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getLikes().clear();

        if (reviewRequest.getLikes() != null) {
        for (var item : reviewRequest.getLikes()) {
        ReviewLike existingItem;
        if (item.getId() != null) {
        existingItem = likesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setReview(existing);

        // Ajouter directement dans la collection existante
        existing.getLikes().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

        if (reviewRequest.getMediaFile() != null
        && reviewRequest.getMediaFile().getId() != null) {

        MediaFile mediaFile = mediaFileRepository.findById(
        reviewRequest.getMediaFile().getId()
        ).orElseThrow(() -> new RuntimeException("MediaFile not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setMediaFile(mediaFile);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            mediaFile.setReview(existing);
        
        }

    

    

    


        return reviewRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Review> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Review entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    

    

    
        if (entity.getReviewComments() != null) {
        for (var child : entity.getReviewComments()) {
        
            child.setReview(null); // retirer la référence inverse
        
        }
        entity.getReviewComments().clear();
        }
    

    

    
        if (entity.getRatings() != null) {
        for (var child : entity.getRatings()) {
        
            child.setReview(null); // retirer la référence inverse
        
        }
        entity.getRatings().clear();
        }
    

    
        if (entity.getLikes() != null) {
        for (var child : entity.getLikes()) {
        
            child.setReview(null); // retirer la référence inverse
        
        }
        entity.getLikes().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    

    

    

    

    

    

    



// --- Dissocier OneToOne ---

    

    

    

    

    

    
        if (entity.getMediaFile() != null) {
        // Dissocier côté inverse automatiquement
        entity.getMediaFile().setReview(null);
        // Dissocier côté direct
        entity.setMediaFile(null);
        }
    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getMovie() != null) {
        entity.setMovie(null);
        }
    

    
        if (entity.getBook() != null) {
        entity.setBook(null);
        }
    

    
        if (entity.getVideoGame() != null) {
        entity.setVideoGame(null);
        }
    

    

    

    

    


repository.delete(entity);
return true;
}
}