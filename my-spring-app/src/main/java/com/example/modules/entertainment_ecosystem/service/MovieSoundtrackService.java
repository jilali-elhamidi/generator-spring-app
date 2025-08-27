package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.repository.MovieSoundtrackRepository;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MovieSoundtrackService extends BaseService<MovieSoundtrack> {

    protected final MovieSoundtrackRepository moviesoundtrackRepository;
    
    protected final MovieRepository movieRepository;
    
    protected final MusicTrackRepository musicTracksRepository;
    

    public MovieSoundtrackService(MovieSoundtrackRepository repository, MovieRepository movieRepository, MusicTrackRepository musicTracksRepository)
    {
        super(repository);
        this.moviesoundtrackRepository = repository;
        
        this.movieRepository = movieRepository;
        
        this.musicTracksRepository = musicTracksRepository;
        
    }

    @Transactional
    @Override
    public MovieSoundtrack save(MovieSoundtrack moviesoundtrack) {
    // ---------- OneToMany ----------
        if (moviesoundtrack.getMusicTracks() != null) {
            List<MusicTrack> managedMusicTracks = new ArrayList<>();
            for (MusicTrack item : moviesoundtrack.getMusicTracks()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = musicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

                     existingItem.setSoundtrack(moviesoundtrack);
                     managedMusicTracks.add(existingItem);
                } else {
                    item.setSoundtrack(moviesoundtrack);
                    managedMusicTracks.add(item);
                }
            }
            moviesoundtrack.setMusicTracks(managedMusicTracks);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (moviesoundtrack.getMovie() != null) {
            if (moviesoundtrack.getMovie().getId() != null) {
                Movie existingMovie = movieRepository.findById(moviesoundtrack.getMovie().getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found with id "
                        + moviesoundtrack.getMovie().getId()));
                moviesoundtrack.setMovie(existingMovie);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Movie newMovie = movieRepository.save(moviesoundtrack.getMovie());
                moviesoundtrack.setMovie(newMovie);
            }

            moviesoundtrack.getMovie().setSoundtrack(moviesoundtrack);
        }
        
    return moviesoundtrackRepository.save(moviesoundtrack);
}

    @Transactional
    @Override
    public MovieSoundtrack update(Long id, MovieSoundtrack moviesoundtrackRequest) {
        MovieSoundtrack existing = moviesoundtrackRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieSoundtrack not found"));

    // Copier les champs simples
        existing.setTitle(moviesoundtrackRequest.getTitle());
        existing.setReleaseDate(moviesoundtrackRequest.getReleaseDate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getMusicTracks().clear();

        if (moviesoundtrackRequest.getMusicTracks() != null) {
            for (var item : moviesoundtrackRequest.getMusicTracks()) {
                MusicTrack existingItem;
                if (item.getId() != null) {
                    existingItem = musicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSoundtrack(existing);
                existing.getMusicTracks().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (moviesoundtrackRequest.getMovie() != null &&moviesoundtrackRequest.getMovie().getId() != null) {

        Movie movie = movieRepository.findById(moviesoundtrackRequest.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setMovie(movie);
        movie.setSoundtrack(existing);
        }
    
    return moviesoundtrackRepository.save(existing);
}

    // Pagination simple
    public Page<MovieSoundtrack> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MovieSoundtrack> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MovieSoundtrack.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MovieSoundtrack> saveAll(List<MovieSoundtrack> moviesoundtrackList) {
        return super.saveAll(moviesoundtrackList);
    }

}