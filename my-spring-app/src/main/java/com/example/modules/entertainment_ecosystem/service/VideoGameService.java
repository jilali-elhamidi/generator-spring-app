package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class VideoGameService extends BaseService<VideoGame> {

    protected final VideoGameRepository videogameRepository;
    private final GenreRepository genresRepository;
    private final UserProfileRepository playedByRepository;

    public VideoGameService(VideoGameRepository repository,GenreRepository genresRepository,UserProfileRepository playedByRepository)
    {
        super(repository);
        this.videogameRepository = repository;
        this.genresRepository = genresRepository;
        this.playedByRepository = playedByRepository;
    }

    @Override
    public VideoGame save(VideoGame videogame) {

        if (videogame.getReviews() != null) {
            for (Review item : videogame.getReviews()) {
            item.setVideoGame(videogame);
            }
        }

        if (videogame.getAchievements() != null) {
            for (GameAchievement item : videogame.getAchievements()) {
            item.setGame(videogame);
            }
        }

        return videogameRepository.save(videogame);
    }


    public VideoGame update(Long id, VideoGame videogameRequest) {
        VideoGame existing = videogameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("VideoGame not found"));

    // Copier les champs simples
        existing.setTitle(videogameRequest.getTitle());
        existing.setReleaseDate(videogameRequest.getReleaseDate());
        existing.setDeveloper(videogameRequest.getDeveloper());
        existing.setPublisher(videogameRequest.getPublisher());
        existing.setPlatform(videogameRequest.getPlatform());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (videogameRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = videogameRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

        if (videogameRequest.getPlayedBy() != null) {
            existing.getPlayedBy().clear();
            List<UserProfile> playedByList = videogameRequest.getPlayedBy().stream()
                .map(item -> playedByRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getPlayedBy().addAll(playedByList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (videogameRequest.getReviews() != null) {
            for (var item : videogameRequest.getReviews()) {
            item.setVideoGame(existing);
            existing.getReviews().add(item);
            }
        }

        existing.getAchievements().clear();
        if (videogameRequest.getAchievements() != null) {
            for (var item : videogameRequest.getAchievements()) {
            item.setGame(existing);
            existing.getAchievements().add(item);
            }
        }

        return videogameRepository.save(existing);
    }
}