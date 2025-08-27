package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.repository.MovieFestivalRepository;

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
public class MovieFestivalService extends BaseService<MovieFestival> {

    protected final MovieFestivalRepository moviefestivalRepository;
    
    protected final MovieRepository moviesRepository;
    

    public MovieFestivalService(MovieFestivalRepository repository, MovieRepository moviesRepository)
    {
        super(repository);
        this.moviefestivalRepository = repository;
        
        this.moviesRepository = moviesRepository;
        
    }

    @Transactional
    @Override
    public MovieFestival save(MovieFestival moviefestival) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (moviefestival.getMovies() != null &&
            !moviefestival.getMovies().isEmpty()) {

            List<Movie> attachedMovies = new ArrayList<>();
            for (Movie item : moviefestival.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedMovies.add(existingItem);
                } else {

                    Movie newItem = moviesRepository.save(item);
                    attachedMovies.add(newItem);
                }
            }

            moviefestival.setMovies(attachedMovies);

            // côté propriétaire (Movie → MovieFestival)
            attachedMovies.forEach(it -> it.getFestivals().add(moviefestival));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return moviefestivalRepository.save(moviefestival);
}

    @Transactional
    @Override
    public MovieFestival update(Long id, MovieFestival moviefestivalRequest) {
        MovieFestival existing = moviefestivalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieFestival not found"));

    // Copier les champs simples
        existing.setName(moviefestivalRequest.getName());
        existing.setStartDate(moviefestivalRequest.getStartDate());
        existing.setEndDate(moviefestivalRequest.getEndDate());
        existing.setLocation(moviefestivalRequest.getLocation());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (moviefestivalRequest.getMovies() != null) {
            existing.getMovies().clear();

            List<Movie> moviesList = moviefestivalRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getMovies().addAll(moviesList);

            // Mettre à jour le côté inverse
            moviesList.forEach(it -> {
                if (!it.getFestivals().contains(existing)) {
                    it.getFestivals().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return moviefestivalRepository.save(existing);
}

    // Pagination simple
    public Page<MovieFestival> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MovieFestival> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MovieFestival.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MovieFestival> saveAll(List<MovieFestival> moviefestivalList) {
        return super.saveAll(moviefestivalList);
    }

}