package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.repository.MovieStudioRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MovieStudioService extends BaseService<MovieStudio> {

    protected final MovieStudioRepository moviestudioRepository;

    public MovieStudioService(MovieStudioRepository repository)
    {
        super(repository);
        this.moviestudioRepository = repository;
    }

    @Override
    public MovieStudio save(MovieStudio moviestudio) {

        if (moviestudio.getMovies() != null) {
            for (Movie item : moviestudio.getMovies()) {
            item.setMovieStudio(moviestudio);
            }
        }

        return moviestudioRepository.save(moviestudio);
    }


    public MovieStudio update(Long id, MovieStudio moviestudioRequest) {
        MovieStudio existing = moviestudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieStudio not found"));

    // Copier les champs simples
        existing.setName(moviestudioRequest.getName());
        existing.setLocation(moviestudioRequest.getLocation());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getMovies().clear();
        if (moviestudioRequest.getMovies() != null) {
            for (var item : moviestudioRequest.getMovies()) {
            item.setMovieStudio(existing);
            existing.getMovies().add(item);
            }
        }

    


        return moviestudioRepository.save(existing);
    }
}