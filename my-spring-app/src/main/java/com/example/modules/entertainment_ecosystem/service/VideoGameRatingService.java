package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRatingRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class VideoGameRatingService extends BaseService<VideoGameRating> {

    protected final VideoGameRatingRepository videogameratingRepository;
    
    protected final VideoGameRepository gameRepository;
    

    public VideoGameRatingService(VideoGameRatingRepository repository, VideoGameRepository gameRepository)
    {
        super(repository);
        this.videogameratingRepository = repository;
        
        this.gameRepository = gameRepository;
        
    }

    @Transactional
    @Override
    public VideoGameRating save(VideoGameRating videogamerating) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (videogamerating.getGame() != null) {
            if (videogamerating.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    videogamerating.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + videogamerating.getGame().getId()));
                videogamerating.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(videogamerating.getGame());
                videogamerating.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return videogameratingRepository.save(videogamerating);
}

    @Transactional
    @Override
    public VideoGameRating update(Long id, VideoGameRating videogameratingRequest) {
        VideoGameRating existing = videogameratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));

    // Copier les champs simples
        existing.setRatingSystem(videogameratingRequest.getRatingSystem());
        existing.setRating(videogameratingRequest.getRating());

    // ---------- Relations ManyToOne ----------
        if (videogameratingRequest.getGame() != null &&
            videogameratingRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                videogameratingRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return videogameratingRepository.save(existing);
}

    // Pagination simple
    public Page<VideoGameRating> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<VideoGameRating> search(Map<String, String> filters, Pageable pageable) {
        return super.search(VideoGameRating.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<VideoGameRating> saveAll(List<VideoGameRating> videogameratingList) {
        return super.saveAll(videogameratingList);
    }

}