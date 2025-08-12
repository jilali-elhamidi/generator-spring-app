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

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ReviewService extends BaseService<Review> {

    protected final ReviewRepository reviewRepository;
    private final UserProfileRepository userRepository;
    private final MovieRepository movieRepository;
    private final BookRepository bookRepository;
    private final VideoGameRepository videoGameRepository;

    public ReviewService(ReviewRepository repository,UserProfileRepository userRepository,MovieRepository movieRepository,BookRepository bookRepository,VideoGameRepository videoGameRepository)
    {
        super(repository);
        this.reviewRepository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.bookRepository = bookRepository;
        this.videoGameRepository = videoGameRepository;
    }

    @Override
    public Review save(Review review) {

        if (review.getUser() != null && review.getUser().getId() != null) {
        UserProfile user = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        review.setUser(user);
        }

        if (review.getMovie() != null && review.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(review.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        review.setMovie(movie);
        }

        if (review.getBook() != null && review.getBook().getId() != null) {
        Book book = bookRepository.findById(review.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        review.setBook(book);
        }

        if (review.getVideoGame() != null && review.getVideoGame().getId() != null) {
        VideoGame videoGame = videoGameRepository.findById(review.getVideoGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        review.setVideoGame(videoGame);
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

        if (reviewRequest.getUser() != null && reviewRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(reviewRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (reviewRequest.getMovie() != null && reviewRequest.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(reviewRequest.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        existing.setMovie(movie);
        }

        if (reviewRequest.getBook() != null && reviewRequest.getBook().getId() != null) {
        Book book = bookRepository.findById(reviewRequest.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existing.setBook(book);
        }

        if (reviewRequest.getVideoGame() != null && reviewRequest.getVideoGame().getId() != null) {
        VideoGame videoGame = videoGameRepository.findById(reviewRequest.getVideoGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setVideoGame(videoGame);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return reviewRepository.save(existing);
    }
}