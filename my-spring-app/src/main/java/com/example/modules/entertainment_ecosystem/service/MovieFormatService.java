package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.repository.MovieFormatRepository;

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
public class MovieFormatService extends BaseService<MovieFormat> {

    protected final MovieFormatRepository movieformatRepository;
    
    protected final MovieRepository moviesRepository;
    

    public MovieFormatService(MovieFormatRepository repository, MovieRepository moviesRepository)
    {
        super(repository);
        this.movieformatRepository = repository;
        
        this.moviesRepository = moviesRepository;
        
    }

    @Transactional
    @Override
    public MovieFormat save(MovieFormat movieformat) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (movieformat.getMovies() != null &&
            !movieformat.getMovies().isEmpty()) {

            List<Movie> attachedMovies = new ArrayList<>();
            for (Movie item : movieformat.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedMovies.add(existingItem);
                } else {

                    Movie newItem = moviesRepository.save(item);
                    attachedMovies.add(newItem);
                }
            }

            movieformat.setMovies(attachedMovies);

            // côté propriétaire (Movie → MovieFormat)
            attachedMovies.forEach(it -> it.getFormats().add(movieformat));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return movieformatRepository.save(movieformat);
}

    @Transactional
    @Override
    public MovieFormat update(Long id, MovieFormat movieformatRequest) {
        MovieFormat existing = movieformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieFormat not found"));

    // Copier les champs simples
        existing.setName(movieformatRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (movieformatRequest.getMovies() != null) {
            existing.getMovies().clear();

            List<Movie> moviesList = movieformatRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getMovies().addAll(moviesList);

            // Mettre à jour le côté inverse
            moviesList.forEach(it -> {
                if (!it.getFormats().contains(existing)) {
                    it.getFormats().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return movieformatRepository.save(existing);
}

    // Pagination simple
    public Page<MovieFormat> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MovieFormat> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MovieFormat.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MovieFormat> saveAll(List<MovieFormat> movieformatList) {
        return super.saveAll(movieformatList);
    }

}