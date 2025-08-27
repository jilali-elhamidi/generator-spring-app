package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.repository.MovieStudioRepository;

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
public class MovieStudioService extends BaseService<MovieStudio> {

    protected final MovieStudioRepository moviestudioRepository;
    
    protected final MovieRepository moviesRepository;
    

    public MovieStudioService(MovieStudioRepository repository, MovieRepository moviesRepository)
    {
        super(repository);
        this.moviestudioRepository = repository;
        
        this.moviesRepository = moviesRepository;
        
    }

    @Transactional
    @Override
    public MovieStudio save(MovieStudio moviestudio) {
    // ---------- OneToMany ----------
        if (moviestudio.getMovies() != null) {
            List<Movie> managedMovies = new ArrayList<>();
            for (Movie item : moviestudio.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));

                     existingItem.setMovieStudio(moviestudio);
                     managedMovies.add(existingItem);
                } else {
                    item.setMovieStudio(moviestudio);
                    managedMovies.add(item);
                }
            }
            moviestudio.setMovies(managedMovies);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return moviestudioRepository.save(moviestudio);
}

    @Transactional
    @Override
    public MovieStudio update(Long id, MovieStudio moviestudioRequest) {
        MovieStudio existing = moviestudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieStudio not found"));

    // Copier les champs simples
        existing.setName(moviestudioRequest.getName());
        existing.setLocation(moviestudioRequest.getLocation());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getMovies().clear();

        if (moviestudioRequest.getMovies() != null) {
            for (var item : moviestudioRequest.getMovies()) {
                Movie existingItem;
                if (item.getId() != null) {
                    existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMovieStudio(existing);
                existing.getMovies().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return moviestudioRepository.save(existing);
}

    // Pagination simple
    public Page<MovieStudio> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MovieStudio> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MovieStudio.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MovieStudio> saveAll(List<MovieStudio> moviestudioList) {
        return super.saveAll(moviestudioList);
    }

}