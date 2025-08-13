package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ContentRatingService extends BaseService<ContentRating> {

    protected final ContentRatingRepository contentratingRepository;
    private final ContentRatingBoardRepository boardRepository;

    public ContentRatingService(ContentRatingRepository repository,ContentRatingBoardRepository boardRepository)
    {
        super(repository);
        this.contentratingRepository = repository;
        this.boardRepository = boardRepository;
    }

    @Override
    public ContentRating save(ContentRating contentrating) {

        if (contentrating.getBoard() != null && contentrating.getBoard().getId() != null) {
        ContentRatingBoard board = boardRepository.findById(contentrating.getBoard().getId())
                .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        contentrating.setBoard(board);
        }

        if (contentrating.getRatedMovies() != null) {
            for (Movie item : contentrating.getRatedMovies()) {
            item.setContentRating(contentrating);
            }
        }

        if (contentrating.getRatedTvShows() != null) {
            for (TVShow item : contentrating.getRatedTvShows()) {
            item.setContentRating(contentrating);
            }
        }

        if (contentrating.getRatedVideoGames() != null) {
            for (VideoGame item : contentrating.getRatedVideoGames()) {
            item.setContentRating(contentrating);
            }
        }

        return contentratingRepository.save(contentrating);
    }


    public ContentRating update(Long id, ContentRating contentratingRequest) {
        ContentRating existing = contentratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRating not found"));

    // Copier les champs simples
        existing.setName(contentratingRequest.getName());
        existing.setDescription(contentratingRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

        if (contentratingRequest.getBoard() != null && contentratingRequest.getBoard().getId() != null) {
        ContentRatingBoard board = boardRepository.findById(contentratingRequest.getBoard().getId())
                .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        existing.setBoard(board);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getRatedMovies().clear();
        if (contentratingRequest.getRatedMovies() != null) {
            for (var item : contentratingRequest.getRatedMovies()) {
            item.setContentRating(existing);
            existing.getRatedMovies().add(item);
            }
        }

        existing.getRatedTvShows().clear();
        if (contentratingRequest.getRatedTvShows() != null) {
            for (var item : contentratingRequest.getRatedTvShows()) {
            item.setContentRating(existing);
            existing.getRatedTvShows().add(item);
            }
        }

        existing.getRatedVideoGames().clear();
        if (contentratingRequest.getRatedVideoGames() != null) {
            for (var item : contentratingRequest.getRatedVideoGames()) {
            item.setContentRating(existing);
            existing.getRatedVideoGames().add(item);
            }
        }

    

    

    

    


        return contentratingRepository.save(existing);
    }
}