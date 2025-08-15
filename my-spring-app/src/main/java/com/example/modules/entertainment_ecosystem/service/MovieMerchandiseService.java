package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.repository.MovieMerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieMerchandiseService extends BaseService<MovieMerchandise> {

    protected final MovieMerchandiseRepository moviemerchandiseRepository;
    private final MovieRepository movieRepository;

    public MovieMerchandiseService(MovieMerchandiseRepository repository,MovieRepository movieRepository)
    {
        super(repository);
        this.moviemerchandiseRepository = repository;
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieMerchandise save(MovieMerchandise moviemerchandise) {


    

    if (moviemerchandise.getMovie() != null
        && moviemerchandise.getMovie().getId() != null) {
        Movie existingMovie = movieRepository.findById(
        moviemerchandise.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));
        moviemerchandise.setMovie(existingMovie);
        }
    

    


        return moviemerchandiseRepository.save(moviemerchandise);
    }


    public MovieMerchandise update(Long id, MovieMerchandise moviemerchandiseRequest) {
        MovieMerchandise existing = moviemerchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieMerchandise not found"));

    // Copier les champs simples
        existing.setName(moviemerchandiseRequest.getName());
        existing.setPrice(moviemerchandiseRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle
        if (moviemerchandiseRequest.getMovie() != null &&
        moviemerchandiseRequest.getMovie().getId() != null) {

        Movie existingMovie = movieRepository.findById(
        moviemerchandiseRequest.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setMovie(existingMovie);
        } else {
        existing.setMovie(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return moviemerchandiseRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MovieMerchandise> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MovieMerchandise entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getMovie() != null) {
        entity.setMovie(null);
        }
    


repository.delete(entity);
return true;
}
}