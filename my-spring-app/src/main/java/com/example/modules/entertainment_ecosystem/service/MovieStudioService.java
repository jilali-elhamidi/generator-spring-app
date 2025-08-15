package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.repository.MovieStudioRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieStudioService extends BaseService<MovieStudio> {

    protected final MovieStudioRepository moviestudioRepository;
    private final MovieRepository moviesRepository;

    public MovieStudioService(MovieStudioRepository repository,MovieRepository moviesRepository)
    {
        super(repository);
        this.moviestudioRepository = repository;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public MovieStudio save(MovieStudio moviestudio) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (moviestudio.getMovies() != null) {
            List<Movie> managedMovies = new ArrayList<>();
            for (Movie item : moviestudio.getMovies()) {
            if (item.getId() != null) {
            Movie existingItem = moviesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMovieStudio(moviestudio);
            managedMovies.add(existingItem);
            } else {
            item.setMovieStudio(moviestudio);
            managedMovies.add(item);
            }
            }
            moviestudio.setMovies(managedMovies);
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
        // Vider la collection existante
        existing.getMovies().clear();

        if (moviestudioRequest.getMovies() != null) {
        for (var item : moviestudioRequest.getMovies()) {
        Movie existingItem;
        if (item.getId() != null) {
        existingItem = moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMovieStudio(existing);

        // Ajouter directement dans la collection existante
        existing.getMovies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return moviestudioRepository.save(existing);
    }


}