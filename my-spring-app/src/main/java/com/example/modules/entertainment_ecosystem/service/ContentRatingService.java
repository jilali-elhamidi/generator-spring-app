package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentRatingService extends BaseService<ContentRating> {

    protected final ContentRatingRepository contentratingRepository;
    private final MovieRepository ratedMoviesRepository;
    private final TVShowRepository ratedTvShowsRepository;
    private final VideoGameRepository ratedVideoGamesRepository;
    private final ContentRatingBoardRepository boardRepository;

    public ContentRatingService(ContentRatingRepository repository,MovieRepository ratedMoviesRepository,TVShowRepository ratedTvShowsRepository,VideoGameRepository ratedVideoGamesRepository,ContentRatingBoardRepository boardRepository)
    {
        super(repository);
        this.contentratingRepository = repository;
        this.ratedMoviesRepository = ratedMoviesRepository;
        this.ratedTvShowsRepository = ratedTvShowsRepository;
        this.ratedVideoGamesRepository = ratedVideoGamesRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public ContentRating save(ContentRating contentrating) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentrating.getRatedMovies() != null) {
            List<Movie> managedRatedMovies = new ArrayList<>();
            for (Movie item : contentrating.getRatedMovies()) {
            if (item.getId() != null) {
            Movie existingItem = ratedMoviesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setContentRating(contentrating);
            managedRatedMovies.add(existingItem);
            } else {
            item.setContentRating(contentrating);
            managedRatedMovies.add(item);
            }
            }
            contentrating.setRatedMovies(managedRatedMovies);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentrating.getRatedTvShows() != null) {
            List<TVShow> managedRatedTvShows = new ArrayList<>();
            for (TVShow item : contentrating.getRatedTvShows()) {
            if (item.getId() != null) {
            TVShow existingItem = ratedTvShowsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TVShow not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setContentRating(contentrating);
            managedRatedTvShows.add(existingItem);
            } else {
            item.setContentRating(contentrating);
            managedRatedTvShows.add(item);
            }
            }
            contentrating.setRatedTvShows(managedRatedTvShows);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentrating.getRatedVideoGames() != null) {
            List<VideoGame> managedRatedVideoGames = new ArrayList<>();
            for (VideoGame item : contentrating.getRatedVideoGames()) {
            if (item.getId() != null) {
            VideoGame existingItem = ratedVideoGamesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("VideoGame not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setContentRating(contentrating);
            managedRatedVideoGames.add(existingItem);
            } else {
            item.setContentRating(contentrating);
            managedRatedVideoGames.add(item);
            }
            }
            contentrating.setRatedVideoGames(managedRatedVideoGames);
            }
        
    

    

    
    
    
    if (contentrating.getBoard() != null
        && contentrating.getBoard().getId() != null) {
        ContentRatingBoard existingBoard = boardRepository.findById(
        contentrating.getBoard().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));
        contentrating.setBoard(existingBoard);
        }
    

        return contentratingRepository.save(contentrating);
    }


    public ContentRating update(Long id, ContentRating contentratingRequest) {
        ContentRating existing = contentratingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRating not found"));

    // Copier les champs simples
        existing.setName(contentratingRequest.getName());
        existing.setDescription(contentratingRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle
        if (contentratingRequest.getBoard() != null &&
        contentratingRequest.getBoard().getId() != null) {

        ContentRatingBoard existingBoard = boardRepository.findById(
        contentratingRequest.getBoard().getId()
        ).orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

        existing.setBoard(existingBoard);
        } else {
        existing.setBoard(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getRatedMovies().clear();

        if (contentratingRequest.getRatedMovies() != null) {
        for (var item : contentratingRequest.getRatedMovies()) {
        Movie existingItem;
        if (item.getId() != null) {
        existingItem = ratedMoviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setContentRating(existing);

        // Ajouter directement dans la collection existante
        existing.getRatedMovies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getRatedTvShows().clear();

        if (contentratingRequest.getRatedTvShows() != null) {
        for (var item : contentratingRequest.getRatedTvShows()) {
        TVShow existingItem;
        if (item.getId() != null) {
        existingItem = ratedTvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setContentRating(existing);

        // Ajouter directement dans la collection existante
        existing.getRatedTvShows().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getRatedVideoGames().clear();

        if (contentratingRequest.getRatedVideoGames() != null) {
        for (var item : contentratingRequest.getRatedVideoGames()) {
        VideoGame existingItem;
        if (item.getId() != null) {
        existingItem = ratedVideoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setContentRating(existing);

        // Ajouter directement dans la collection existante
        existing.getRatedVideoGames().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    


        return contentratingRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ContentRating> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ContentRating entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getRatedMovies() != null) {
        for (var child : entity.getRatedMovies()) {
        
            child.setContentRating(null); // retirer la référence inverse
        
        }
        entity.getRatedMovies().clear();
        }
    

    
        if (entity.getRatedTvShows() != null) {
        for (var child : entity.getRatedTvShows()) {
        
            child.setContentRating(null); // retirer la référence inverse
        
        }
        entity.getRatedTvShows().clear();
        }
    

    
        if (entity.getRatedVideoGames() != null) {
        for (var child : entity.getRatedVideoGames()) {
        
            child.setContentRating(null); // retirer la référence inverse
        
        }
        entity.getRatedVideoGames().clear();
        }
    

    


// --- Dissocier ManyToMany ---

    

    

    

    



// --- Dissocier OneToOne ---

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    

    
        if (entity.getBoard() != null) {
        entity.setBoard(null);
        }
    


repository.delete(entity);
return true;
}
}