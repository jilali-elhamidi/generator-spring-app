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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MovieSoundtrackService extends BaseService<MovieSoundtrack> {

    protected final MovieSoundtrackRepository moviesoundtrackRepository;
    private final MovieRepository movieRepository;
    private final MusicTrackRepository musicTracksRepository;

    public MovieSoundtrackService(MovieSoundtrackRepository repository,MovieRepository movieRepository,MusicTrackRepository musicTracksRepository)
    {
        super(repository);
        this.moviesoundtrackRepository = repository;
        this.movieRepository = movieRepository;
        this.musicTracksRepository = musicTracksRepository;
    }

    @Override
    public MovieSoundtrack save(MovieSoundtrack moviesoundtrack) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (moviesoundtrack.getMusicTracks() != null) {
            List<MusicTrack> managedMusicTracks = new ArrayList<>();
            for (MusicTrack item : moviesoundtrack.getMusicTracks()) {
            if (item.getId() != null) {
            MusicTrack existingItem = musicTracksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSoundtrack(moviesoundtrack);
            managedMusicTracks.add(existingItem);
            } else {
            item.setSoundtrack(moviesoundtrack);
            managedMusicTracks.add(item);
            }
            }
            moviesoundtrack.setMusicTracks(managedMusicTracks);
            }
        
    


    

    

    
    
        if (moviesoundtrack.getMovie() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            moviesoundtrack.setMovie(
            movieRepository.findById(moviesoundtrack.getMovie().getId())
            .orElseThrow(() -> new RuntimeException("movie not found"))
            );
        
        moviesoundtrack.getMovie().setSoundtrack(moviesoundtrack);
        }

        return moviesoundtrackRepository.save(moviesoundtrack);
    }


    public MovieSoundtrack update(Long id, MovieSoundtrack moviesoundtrackRequest) {
        MovieSoundtrack existing = moviesoundtrackRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieSoundtrack not found"));

    // Copier les champs simples
        existing.setTitle(moviesoundtrackRequest.getTitle());
        existing.setReleaseDate(moviesoundtrackRequest.getReleaseDate());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getMusicTracks().clear();

        if (moviesoundtrackRequest.getMusicTracks() != null) {
        for (var item : moviesoundtrackRequest.getMusicTracks()) {
        MusicTrack existingItem;
        if (item.getId() != null) {
        existingItem = musicTracksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSoundtrack(existing);

        // Ajouter directement dans la collection existante
        existing.getMusicTracks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

        if (moviesoundtrackRequest.getMovie() != null
        && moviesoundtrackRequest.getMovie().getId() != null) {

        Movie movie = movieRepository.findById(
        moviesoundtrackRequest.getMovie().getId()
        ).orElseThrow(() -> new RuntimeException("Movie not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setMovie(movie);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            movie.setSoundtrack(existing);
        
        }

    

    


        return moviesoundtrackRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MovieSoundtrack> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MovieSoundtrack entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getMusicTracks() != null) {
        for (var child : entity.getMusicTracks()) {
        
            child.setSoundtrack(null); // retirer la référence inverse
        
        }
        entity.getMusicTracks().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    
        if (entity.getMovie() != null) {
        // Dissocier côté inverse automatiquement
        entity.getMovie().setSoundtrack(null);
        // Dissocier côté direct
        entity.setMovie(null);
        }
    

    


// --- Dissocier ManyToOne ---

    

    


repository.delete(entity);
return true;
}
}