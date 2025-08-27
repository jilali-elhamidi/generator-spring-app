package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingAgeGroupRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ContentRatingService extends BaseService<ContentRating> {

    protected final ContentRatingRepository contentratingRepository;
    
    protected final MovieRepository ratedMoviesRepository;
    
    protected final TVShowRepository ratedTvShowsRepository;
    
    protected final VideoGameRepository ratedVideoGamesRepository;
    
    protected final ContentRatingBoardRepository boardRepository;
    
    protected final ContentRatingAgeGroupRepository ageGroupRepository;
    

    public ContentRatingService(ContentRatingRepository repository, MovieRepository ratedMoviesRepository, TVShowRepository ratedTvShowsRepository, VideoGameRepository ratedVideoGamesRepository, ContentRatingBoardRepository boardRepository, ContentRatingAgeGroupRepository ageGroupRepository)
    {
        super(repository);
        this.contentratingRepository = repository;
        
        this.ratedMoviesRepository = ratedMoviesRepository;
        
        this.ratedTvShowsRepository = ratedTvShowsRepository;
        
        this.ratedVideoGamesRepository = ratedVideoGamesRepository;
        
        this.boardRepository = boardRepository;
        
        this.ageGroupRepository = ageGroupRepository;
        
    }

    @Transactional
    @Override
    public ContentRating save(ContentRating contentrating) {
    // ---------- OneToMany ----------
        if (contentrating.getRatedMovies() != null) {
            List<Movie> managedRatedMovies = new ArrayList<>();
            for (Movie item : contentrating.getRatedMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = ratedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));

                     existingItem.setContentRating(contentrating);
                     managedRatedMovies.add(existingItem);
                } else {
                    item.setContentRating(contentrating);
                    managedRatedMovies.add(item);
                }
            }
            contentrating.setRatedMovies(managedRatedMovies);
        }
    
        if (contentrating.getRatedTvShows() != null) {
            List<TVShow> managedRatedTvShows = new ArrayList<>();
            for (TVShow item : contentrating.getRatedTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = ratedTvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));

                     existingItem.setContentRating(contentrating);
                     managedRatedTvShows.add(existingItem);
                } else {
                    item.setContentRating(contentrating);
                    managedRatedTvShows.add(item);
                }
            }
            contentrating.setRatedTvShows(managedRatedTvShows);
        }
    
        if (contentrating.getRatedVideoGames() != null) {
            List<VideoGame> managedRatedVideoGames = new ArrayList<>();
            for (VideoGame item : contentrating.getRatedVideoGames()) {
                if (item.getId() != null) {
                    VideoGame existingItem = ratedVideoGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));

                     existingItem.setContentRating(contentrating);
                     managedRatedVideoGames.add(existingItem);
                } else {
                    item.setContentRating(contentrating);
                    managedRatedVideoGames.add(item);
                }
            }
            contentrating.setRatedVideoGames(managedRatedVideoGames);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (contentrating.getBoard() != null) {
            if (contentrating.getBoard().getId() != null) {
                ContentRatingBoard existingBoard = boardRepository.findById(
                    contentrating.getBoard().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found with id "
                    + contentrating.getBoard().getId()));
                contentrating.setBoard(existingBoard);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRatingBoard newBoard = boardRepository.save(contentrating.getBoard());
                contentrating.setBoard(newBoard);
            }
        }
        
        if (contentrating.getAgeGroup() != null) {
            if (contentrating.getAgeGroup().getId() != null) {
                ContentRatingAgeGroup existingAgeGroup = ageGroupRepository.findById(
                    contentrating.getAgeGroup().getId()
                ).orElseThrow(() -> new RuntimeException("ContentRatingAgeGroup not found with id "
                    + contentrating.getAgeGroup().getId()));
                contentrating.setAgeGroup(existingAgeGroup);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ContentRatingAgeGroup newAgeGroup = ageGroupRepository.save(contentrating.getAgeGroup());
                contentrating.setAgeGroup(newAgeGroup);
            }
        }
        
    // ---------- OneToOne ----------
    return contentratingRepository.save(contentrating);
}

    @Transactional
    @Override
    public ContentRating update(Long id, ContentRating contentratingRequest) {
        ContentRating existing = contentratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRating not found"));

    // Copier les champs simples
        existing.setName(contentratingRequest.getName());
        existing.setDescription(contentratingRequest.getDescription());

    // ---------- Relations ManyToOne ----------
        if (contentratingRequest.getBoard() != null &&
            contentratingRequest.getBoard().getId() != null) {

            ContentRatingBoard existingBoard = boardRepository.findById(
                contentratingRequest.getBoard().getId()
            ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

            existing.setBoard(existingBoard);
        } else {
            existing.setBoard(null);
        }
        
        if (contentratingRequest.getAgeGroup() != null &&
            contentratingRequest.getAgeGroup().getId() != null) {

            ContentRatingAgeGroup existingAgeGroup = ageGroupRepository.findById(
                contentratingRequest.getAgeGroup().getId()
            ).orElseThrow(() -> new RuntimeException("ContentRatingAgeGroup not found"));

            existing.setAgeGroup(existingAgeGroup);
        } else {
            existing.setAgeGroup(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getRatedMovies().clear();

        if (contentratingRequest.getRatedMovies() != null) {
            for (var item : contentratingRequest.getRatedMovies()) {
                Movie existingItem;
                if (item.getId() != null) {
                    existingItem = ratedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
                } else {
                existingItem = item;
                }

                existingItem.setContentRating(existing);
                existing.getRatedMovies().add(existingItem);
            }
        }
        
        existing.getRatedTvShows().clear();

        if (contentratingRequest.getRatedTvShows() != null) {
            for (var item : contentratingRequest.getRatedTvShows()) {
                TVShow existingItem;
                if (item.getId() != null) {
                    existingItem = ratedTvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));
                } else {
                existingItem = item;
                }

                existingItem.setContentRating(existing);
                existing.getRatedTvShows().add(existingItem);
            }
        }
        
        existing.getRatedVideoGames().clear();

        if (contentratingRequest.getRatedVideoGames() != null) {
            for (var item : contentratingRequest.getRatedVideoGames()) {
                VideoGame existingItem;
                if (item.getId() != null) {
                    existingItem = ratedVideoGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
                } else {
                existingItem = item;
                }

                existingItem.setContentRating(existing);
                existing.getRatedVideoGames().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return contentratingRepository.save(existing);
}

    // Pagination simple
    public Page<ContentRating> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ContentRating> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ContentRating.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ContentRating> saveAll(List<ContentRating> contentratingList) {
        return super.saveAll(contentratingList);
    }

}