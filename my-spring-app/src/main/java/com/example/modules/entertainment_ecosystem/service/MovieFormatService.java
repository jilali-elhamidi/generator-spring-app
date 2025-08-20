package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.repository.MovieFormatRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieFormatService extends BaseService<MovieFormat> {

    protected final MovieFormatRepository movieformatRepository;
    private final MovieRepository moviesRepository;

    public MovieFormatService(MovieFormatRepository repository, MovieRepository moviesRepository)
    {
        super(repository);
        this.movieformatRepository = repository;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public MovieFormat save(MovieFormat movieformat) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (movieformat.getMovies() != null &&
            !movieformat.getMovies().isEmpty()) {

            List<Movie> attachedMovies = movieformat.getMovies().stream()
            .map(item -> moviesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId())))
            .toList();

            movieformat.setMovies(attachedMovies);

            // côté propriétaire (Movie → MovieFormat)
            attachedMovies.forEach(it -> it.getFormats().add(movieformat));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return movieformatRepository.save(movieformat);
}


    public MovieFormat update(Long id, MovieFormat movieformatRequest) {
        MovieFormat existing = movieformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieFormat not found"));

    // Copier les champs simples
        existing.setName(movieformatRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MovieFormat> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MovieFormat entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getMovies() != null) {
            for (Movie item : new ArrayList<>(entity.getMovies())) {
                
                item.getFormats().remove(entity); // retire côté inverse
                
            }
            entity.getMovies().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}