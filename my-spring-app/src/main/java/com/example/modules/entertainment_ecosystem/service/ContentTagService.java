package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.repository.ContentTagRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentTagService extends BaseService<ContentTag> {

    protected final ContentTagRepository contenttagRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final VideoGameRepository videoGamesRepository;
    private final LiveEventRepository liveEventsRepository;

    public ContentTagService(ContentTagRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,VideoGameRepository videoGamesRepository,LiveEventRepository liveEventsRepository)
    {
        super(repository);
        this.contenttagRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.videoGamesRepository = videoGamesRepository;
        this.liveEventsRepository = liveEventsRepository;
    }

    @Override
    public ContentTag save(ContentTag contenttag) {


    

    

    

    

    
    
    
    

    
        if (contenttag.getMovies() != null) {
        List<Movie> managedMovies = new ArrayList<>();
        for (Movie item : contenttag.getMovies()) {
        if (item.getId() != null) {
        Movie existingItem = moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        managedMovies.add(existingItem);
        } else {
        managedMovies.add(item);
        }
        }
        contenttag.setMovies(managedMovies);
        }
    

    
        if (contenttag.getTvShows() != null) {
        List<TVShow> managedTvShows = new ArrayList<>();
        for (TVShow item : contenttag.getTvShows()) {
        if (item.getId() != null) {
        TVShow existingItem = tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        managedTvShows.add(existingItem);
        } else {
        managedTvShows.add(item);
        }
        }
        contenttag.setTvShows(managedTvShows);
        }
    

    
        if (contenttag.getVideoGames() != null) {
        List<VideoGame> managedVideoGames = new ArrayList<>();
        for (VideoGame item : contenttag.getVideoGames()) {
        if (item.getId() != null) {
        VideoGame existingItem = videoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        managedVideoGames.add(existingItem);
        } else {
        managedVideoGames.add(item);
        }
        }
        contenttag.setVideoGames(managedVideoGames);
        }
    

    
        if (contenttag.getLiveEvents() != null) {
        List<LiveEvent> managedLiveEvents = new ArrayList<>();
        for (LiveEvent item : contenttag.getLiveEvents()) {
        if (item.getId() != null) {
        LiveEvent existingItem = liveEventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        managedLiveEvents.add(existingItem);
        } else {
        managedLiveEvents.add(item);
        }
        }
        contenttag.setLiveEvents(managedLiveEvents);
        }
    


        return contenttagRepository.save(contenttag);
    }


    public ContentTag update(Long id, ContentTag contenttagRequest) {
        ContentTag existing = contenttagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentTag not found"));

    // Copier les champs simples
        existing.setName(contenttagRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (contenttagRequest.getMovies() != null) {
            existing.getMovies().clear();
            List<Movie> moviesList = contenttagRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getMovies().addAll(moviesList);
        }

        if (contenttagRequest.getTvShows() != null) {
            existing.getTvShows().clear();
            List<TVShow> tvShowsList = contenttagRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getTvShows().addAll(tvShowsList);
        }

        if (contenttagRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();
            List<VideoGame> videoGamesList = contenttagRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());
        existing.getVideoGames().addAll(videoGamesList);
        }

        if (contenttagRequest.getLiveEvents() != null) {
            existing.getLiveEvents().clear();
            List<LiveEvent> liveEventsList = contenttagRequest.getLiveEvents().stream()
                .map(item -> liveEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found")))
                .collect(Collectors.toList());
        existing.getLiveEvents().addAll(liveEventsList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    

    


        return contenttagRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ContentTag> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ContentTag entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    

    


// --- Dissocier ManyToMany ---

    
        if (entity.getMovies() != null) {
        entity.getMovies().clear();
        }
    

    
        if (entity.getTvShows() != null) {
        entity.getTvShows().clear();
        }
    

    
        if (entity.getVideoGames() != null) {
        entity.getVideoGames().clear();
        }
    

    
        if (entity.getLiveEvents() != null) {
        entity.getLiveEvents().clear();
        }
    


// --- Dissocier OneToOne ---

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    

    


repository.delete(entity);
return true;
}
}