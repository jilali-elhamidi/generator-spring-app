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
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.repository.ReportedContentRepository;

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
    private final ReportedContentRepository reportedContentRepository;

    public ReviewService(ReviewRepository repository, UserProfileRepository userRepository, MovieRepository movieRepository, BookRepository bookRepository, VideoGameRepository videoGameRepository, ReviewCommentRepository reviewCommentsRepository, MediaFileRepository mediaFileRepository, ReviewRatingRepository ratingsRepository, ReviewLikeRepository likesRepository, ReportedContentRepository reportedContentRepository)
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
        this.reportedContentRepository = reportedContentRepository;
    }

    @Override
    public Review save(Review review) {
    // ---------- OneToMany ----------
        if (review.getReviewComments() != null) {
            List<ReviewComment> managedReviewComments = new ArrayList<>();
            for (ReviewComment item : review.getReviewComments()) {
                if (item.getId() != null) {
                    ReviewComment existingItem = reviewCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewComment not found"));

                     existingItem.setReview(review);
                     managedReviewComments.add(existingItem);
                } else {
                    item.setReview(review);
                    managedReviewComments.add(item);
                }
            }
            review.setReviewComments(managedReviewComments);
        }
    
        if (review.getRatings() != null) {
            List<ReviewRating> managedRatings = new ArrayList<>();
            for (ReviewRating item : review.getRatings()) {
                if (item.getId() != null) {
                    ReviewRating existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));

                     existingItem.setReview(review);
                     managedRatings.add(existingItem);
                } else {
                    item.setReview(review);
                    managedRatings.add(item);
                }
            }
            review.setRatings(managedRatings);
        }
    
        if (review.getLikes() != null) {
            List<ReviewLike> managedLikes = new ArrayList<>();
            for (ReviewLike item : review.getLikes()) {
                if (item.getId() != null) {
                    ReviewLike existingItem = likesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));

                     existingItem.setReview(review);
                     managedLikes.add(existingItem);
                } else {
                    item.setReview(review);
                    managedLikes.add(item);
                }
            }
            review.setLikes(managedLikes);
        }
    
        if (review.getReportedContent() != null) {
            List<ReportedContent> managedReportedContent = new ArrayList<>();
            for (ReportedContent item : review.getReportedContent()) {
                if (item.getId() != null) {
                    ReportedContent existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));

                     existingItem.setReview(review);
                     managedReportedContent.add(existingItem);
                } else {
                    item.setReview(review);
                    managedReportedContent.add(item);
                }
            }
            review.setReportedContent(managedReportedContent);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (review.getUser() != null) {
            if (review.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    review.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + review.getUser().getId()));
                review.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(review.getUser());
                review.setUser(newUser);
            }
        }
        
        if (review.getMovie() != null) {
            if (review.getMovie().getId() != null) {
                Movie existingMovie = movieRepository.findById(
                    review.getMovie().getId()
                ).orElseThrow(() -> new RuntimeException("Movie not found with id "
                    + review.getMovie().getId()));
                review.setMovie(existingMovie);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Movie newMovie = movieRepository.save(review.getMovie());
                review.setMovie(newMovie);
            }
        }
        
        if (review.getBook() != null) {
            if (review.getBook().getId() != null) {
                Book existingBook = bookRepository.findById(
                    review.getBook().getId()
                ).orElseThrow(() -> new RuntimeException("Book not found with id "
                    + review.getBook().getId()));
                review.setBook(existingBook);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Book newBook = bookRepository.save(review.getBook());
                review.setBook(newBook);
            }
        }
        
        if (review.getVideoGame() != null) {
            if (review.getVideoGame().getId() != null) {
                VideoGame existingVideoGame = videoGameRepository.findById(
                    review.getVideoGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + review.getVideoGame().getId()));
                review.setVideoGame(existingVideoGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newVideoGame = videoGameRepository.save(review.getVideoGame());
                review.setVideoGame(newVideoGame);
            }
        }
        
    // ---------- OneToOne ----------
        if (review.getMediaFile() != null) {
            if (review.getMediaFile().getId() != null) {
                MediaFile existingMediaFile = mediaFileRepository.findById(review.getMediaFile().getId())
                    .orElseThrow(() -> new RuntimeException("MediaFile not found with id "
                        + review.getMediaFile().getId()));
                review.setMediaFile(existingMediaFile);
            } else {
                // Nouvel objet → sauvegarde d'abord
                MediaFile newMediaFile = mediaFileRepository.save(review.getMediaFile());
                review.setMediaFile(newMediaFile);
            }

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

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getReviewComments().clear();

        if (reviewRequest.getReviewComments() != null) {
            for (var item : reviewRequest.getReviewComments()) {
                ReviewComment existingItem;
                if (item.getId() != null) {
                    existingItem = reviewCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewComment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getReviewComments().add(existingItem);
            }
        }
        
        existing.getRatings().clear();

        if (reviewRequest.getRatings() != null) {
            for (var item : reviewRequest.getRatings()) {
                ReviewRating existingItem;
                if (item.getId() != null) {
                    existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getRatings().add(existingItem);
            }
        }
        
        existing.getLikes().clear();

        if (reviewRequest.getLikes() != null) {
            for (var item : reviewRequest.getLikes()) {
                ReviewLike existingItem;
                if (item.getId() != null) {
                    existingItem = likesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getLikes().add(existingItem);
            }
        }
        
        existing.getReportedContent().clear();

        if (reviewRequest.getReportedContent() != null) {
            for (var item : reviewRequest.getReportedContent()) {
                ReportedContent existingItem;
                if (item.getId() != null) {
                    existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReview(existing);
                existing.getReportedContent().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (reviewRequest.getMediaFile() != null &&reviewRequest.getMediaFile().getId() != null) {

        MediaFile mediaFile = mediaFileRepository.findById(reviewRequest.getMediaFile().getId())
                .orElseThrow(() -> new RuntimeException("MediaFile not found"));

        existing.setMediaFile(mediaFile);
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
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getReviewComments().clear();
        }
        
        if (entity.getRatings() != null) {
            for (var child : entity.getRatings()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getRatings().clear();
        }
        
        if (entity.getLikes() != null) {
            for (var child : entity.getLikes()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getLikes().clear();
        }
        
        if (entity.getReportedContent() != null) {
            for (var child : entity.getReportedContent()) {
                // retirer la référence inverse
                child.setReview(null);
            }
            entity.getReportedContent().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getMediaFile() != null) {
            entity.getMediaFile().setReview(null);
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