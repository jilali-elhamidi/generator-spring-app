package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.repository.MovieMerchandiseRepository;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MovieMerchandiseService extends BaseService<MovieMerchandise> {

    protected final MovieMerchandiseRepository moviemerchandiseRepository;
    
    protected final MovieRepository movieRepository;
    

    public MovieMerchandiseService(MovieMerchandiseRepository repository, MovieRepository movieRepository)
    {
        super(repository);
        this.moviemerchandiseRepository = repository;
        
        this.movieRepository = movieRepository;
        
    }

    @Transactional
    @Override
    public MovieMerchandise save(MovieMerchandise moviemerchandise) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (moviemerchandise.getMovie() != null) {
            if (moviemerchandise.getMovie().getId() != null) {
                Movie existingMovie = movieRepository.findById(
                    moviemerchandise.getMovie().getId()
                ).orElseThrow(() -> new RuntimeException("Movie not found with id "
                    + moviemerchandise.getMovie().getId()));
                moviemerchandise.setMovie(existingMovie);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Movie newMovie = movieRepository.save(moviemerchandise.getMovie());
                moviemerchandise.setMovie(newMovie);
            }
        }
        
    // ---------- OneToOne ----------
    return moviemerchandiseRepository.save(moviemerchandise);
}

    @Transactional
    @Override
    public MovieMerchandise update(Long id, MovieMerchandise moviemerchandiseRequest) {
        MovieMerchandise existing = moviemerchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieMerchandise not found"));

    // Copier les champs simples
        existing.setName(moviemerchandiseRequest.getName());
        existing.setPrice(moviemerchandiseRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (moviemerchandiseRequest.getMovie() != null &&
            moviemerchandiseRequest.getMovie().getId() != null) {

            Movie existingMovie = movieRepository.findById(
                moviemerchandiseRequest.getMovie().getId()
            ).orElseThrow(() -> new RuntimeException("Movie not found"));

            existing.setMovie(existingMovie);
        } else {
            existing.setMovie(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return moviemerchandiseRepository.save(existing);
}

    // Pagination simple
    public Page<MovieMerchandise> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MovieMerchandise> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MovieMerchandise.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MovieMerchandise> saveAll(List<MovieMerchandise> moviemerchandiseList) {
        return super.saveAll(moviemerchandiseList);
    }

}