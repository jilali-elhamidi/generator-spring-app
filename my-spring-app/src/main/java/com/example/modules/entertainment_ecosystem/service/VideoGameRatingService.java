package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRatingRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class VideoGameRatingService extends BaseService<VideoGameRating> {

    protected final VideoGameRatingRepository videogameratingRepository;
    private final VideoGameRepository gameRepository;

    public VideoGameRatingService(VideoGameRatingRepository repository,VideoGameRepository gameRepository)
    {
        super(repository);
        this.videogameratingRepository = repository;
        this.gameRepository = gameRepository;
    }

    @Override
    public VideoGameRating save(VideoGameRating videogamerating) {


    

    if (videogamerating.getGame() != null
        && videogamerating.getGame().getId() != null) {
        VideoGame existingGame = gameRepository.findById(
        videogamerating.getGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));
        videogamerating.setGame(existingGame);
        }
    

    


        return videogameratingRepository.save(videogamerating);
    }


    public VideoGameRating update(Long id, VideoGameRating videogameratingRequest) {
        VideoGameRating existing = videogameratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("VideoGameRating not found"));

    // Copier les champs simples
        existing.setRatingSystem(videogameratingRequest.getRatingSystem());
        existing.setRating(videogameratingRequest.getRating());

// Relations ManyToOne : mise à jour conditionnelle
        if (videogameratingRequest.getGame() != null &&
        videogameratingRequest.getGame().getId() != null) {

        VideoGame existingGame = gameRepository.findById(
        videogameratingRequest.getGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

        existing.setGame(existingGame);
        } else {
        existing.setGame(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return videogameratingRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<VideoGameRating> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

VideoGameRating entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getGame() != null) {
        entity.setGame(null);
        }
    


repository.delete(entity);
return true;
}
}