package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.repository.MovieFormatRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieFormatService extends BaseService<MovieFormat> {

    protected final MovieFormatRepository movieformatRepository;
    private final MovieRepository moviesRepository;

    public MovieFormatService(MovieFormatRepository repository,MovieRepository moviesRepository)
    {
        super(repository);
        this.movieformatRepository = repository;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public MovieFormat save(MovieFormat movieformat) {


    

    

        return movieformatRepository.save(movieformat);
    }


    public MovieFormat update(Long id, MovieFormat movieformatRequest) {
        MovieFormat existing = movieformatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieFormat not found"));

    // Copier les champs simples
        existing.setName(movieformatRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (movieformatRequest.getMovies() != null) {
            existing.getMovies().clear();
            List<Movie> moviesList = movieformatRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getMovies().addAll(moviesList);
        }

// Relations OneToMany : synchronisation sécurisée

    


        return movieformatRepository.save(existing);
    }


}