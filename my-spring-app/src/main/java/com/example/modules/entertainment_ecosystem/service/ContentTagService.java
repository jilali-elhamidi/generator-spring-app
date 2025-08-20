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

    public ContentTagService(ContentTagRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, VideoGameRepository videoGamesRepository, LiveEventRepository liveEventsRepository)
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
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (contenttag.getMovies() != null &&
            !contenttag.getMovies().isEmpty()) {

            List<Movie> attachedMovies = contenttag.getMovies().stream()
            .map(item -> moviesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId())))
            .toList();

            contenttag.setMovies(attachedMovies);

            // côté propriétaire (Movie → ContentTag)
            attachedMovies.forEach(it -> it.getTags().add(contenttag));
        }
        
        if (contenttag.getTvShows() != null &&
            !contenttag.getTvShows().isEmpty()) {

            List<TVShow> attachedTvShows = contenttag.getTvShows().stream()
            .map(item -> tvShowsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId())))
            .toList();

            contenttag.setTvShows(attachedTvShows);

            // côté propriétaire (TVShow → ContentTag)
            attachedTvShows.forEach(it -> it.getTags().add(contenttag));
        }
        
        if (contenttag.getVideoGames() != null &&
            !contenttag.getVideoGames().isEmpty()) {

            List<VideoGame> attachedVideoGames = contenttag.getVideoGames().stream()
            .map(item -> videoGamesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found with id " + item.getId())))
            .toList();

            contenttag.setVideoGames(attachedVideoGames);

            // côté propriétaire (VideoGame → ContentTag)
            attachedVideoGames.forEach(it -> it.getTags().add(contenttag));
        }
        
        if (contenttag.getLiveEvents() != null &&
            !contenttag.getLiveEvents().isEmpty()) {

            List<LiveEvent> attachedLiveEvents = contenttag.getLiveEvents().stream()
            .map(item -> liveEventsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found with id " + item.getId())))
            .toList();

            contenttag.setLiveEvents(attachedLiveEvents);

            // côté propriétaire (LiveEvent → ContentTag)
            attachedLiveEvents.forEach(it -> it.getTags().add(contenttag));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return contenttagRepository.save(contenttag);
}


    public ContentTag update(Long id, ContentTag contenttagRequest) {
        ContentTag existing = contenttagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentTag not found"));

    // Copier les champs simples
        existing.setName(contenttagRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (contenttagRequest.getMovies() != null) {
            existing.getMovies().clear();

            List<Movie> moviesList = contenttagRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getMovies().addAll(moviesList);

            // Mettre à jour le côté inverse
            moviesList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
        if (contenttagRequest.getTvShows() != null) {
            existing.getTvShows().clear();

            List<TVShow> tvShowsList = contenttagRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());

            existing.getTvShows().addAll(tvShowsList);

            // Mettre à jour le côté inverse
            tvShowsList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
        if (contenttagRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();

            List<VideoGame> videoGamesList = contenttagRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());

            existing.getVideoGames().addAll(videoGamesList);

            // Mettre à jour le côté inverse
            videoGamesList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
        if (contenttagRequest.getLiveEvents() != null) {
            existing.getLiveEvents().clear();

            List<LiveEvent> liveEventsList = contenttagRequest.getLiveEvents().stream()
                .map(item -> liveEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found")))
                .collect(Collectors.toList());

            existing.getLiveEvents().addAll(liveEventsList);

            // Mettre à jour le côté inverse
            liveEventsList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

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
            for (Movie item : new ArrayList<>(entity.getMovies())) {
                
                item.getTags().remove(entity); // retire côté inverse
                
            }
            entity.getMovies().clear(); // puis vide côté courant
        }
        
        if (entity.getTvShows() != null) {
            for (TVShow item : new ArrayList<>(entity.getTvShows())) {
                
                item.getTags().remove(entity); // retire côté inverse
                
            }
            entity.getTvShows().clear(); // puis vide côté courant
        }
        
        if (entity.getVideoGames() != null) {
            for (VideoGame item : new ArrayList<>(entity.getVideoGames())) {
                
                item.getTags().remove(entity); // retire côté inverse
                
            }
            entity.getVideoGames().clear(); // puis vide côté courant
        }
        
        if (entity.getLiveEvents() != null) {
            for (LiveEvent item : new ArrayList<>(entity.getLiveEvents())) {
                
                item.getTags().remove(entity); // retire côté inverse
                
            }
            entity.getLiveEvents().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}