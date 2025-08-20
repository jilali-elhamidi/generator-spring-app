package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.repository.ContentLanguageRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentLanguageService extends BaseService<ContentLanguage> {

    protected final ContentLanguageRepository contentlanguageRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final PodcastRepository podcastsRepository;

    public ContentLanguageService(ContentLanguageRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, PodcastRepository podcastsRepository)
    {
        super(repository);
        this.contentlanguageRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.podcastsRepository = podcastsRepository;
    }

    @Override
    public ContentLanguage save(ContentLanguage contentlanguage) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (contentlanguage.getMovies() != null &&
            !contentlanguage.getMovies().isEmpty()) {

            List<Movie> attachedMovies = contentlanguage.getMovies().stream()
            .map(item -> moviesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId())))
            .toList();

            contentlanguage.setMovies(attachedMovies);

            // côté propriétaire (Movie → ContentLanguage)
            attachedMovies.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
        if (contentlanguage.getTvShows() != null &&
            !contentlanguage.getTvShows().isEmpty()) {

            List<TVShow> attachedTvShows = contentlanguage.getTvShows().stream()
            .map(item -> tvShowsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId())))
            .toList();

            contentlanguage.setTvShows(attachedTvShows);

            // côté propriétaire (TVShow → ContentLanguage)
            attachedTvShows.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
        if (contentlanguage.getPodcasts() != null &&
            !contentlanguage.getPodcasts().isEmpty()) {

            List<Podcast> attachedPodcasts = contentlanguage.getPodcasts().stream()
            .map(item -> podcastsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId())))
            .toList();

            contentlanguage.setPodcasts(attachedPodcasts);

            // côté propriétaire (Podcast → ContentLanguage)
            attachedPodcasts.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return contentlanguageRepository.save(contentlanguage);
}


    public ContentLanguage update(Long id, ContentLanguage contentlanguageRequest) {
        ContentLanguage existing = contentlanguageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentLanguage not found"));

    // Copier les champs simples
        existing.setName(contentlanguageRequest.getName());
        existing.setCode(contentlanguageRequest.getCode());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (contentlanguageRequest.getMovies() != null) {
            existing.getMovies().clear();

            List<Movie> moviesList = contentlanguageRequest.getMovies().stream()
                .map(item -> moviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getMovies().addAll(moviesList);

            // Mettre à jour le côté inverse
            moviesList.forEach(it -> {
                if (!it.getLanguages().contains(existing)) {
                    it.getLanguages().add(existing);
                }
            });
        }
        
        if (contentlanguageRequest.getTvShows() != null) {
            existing.getTvShows().clear();

            List<TVShow> tvShowsList = contentlanguageRequest.getTvShows().stream()
                .map(item -> tvShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());

            existing.getTvShows().addAll(tvShowsList);

            // Mettre à jour le côté inverse
            tvShowsList.forEach(it -> {
                if (!it.getLanguages().contains(existing)) {
                    it.getLanguages().add(existing);
                }
            });
        }
        
        if (contentlanguageRequest.getPodcasts() != null) {
            existing.getPodcasts().clear();

            List<Podcast> podcastsList = contentlanguageRequest.getPodcasts().stream()
                .map(item -> podcastsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Podcast not found")))
                .collect(Collectors.toList());

            existing.getPodcasts().addAll(podcastsList);

            // Mettre à jour le côté inverse
            podcastsList.forEach(it -> {
                if (!it.getLanguages().contains(existing)) {
                    it.getLanguages().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return contentlanguageRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ContentLanguage> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ContentLanguage entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getMovies() != null) {
            for (Movie item : new ArrayList<>(entity.getMovies())) {
                
                item.getLanguages().remove(entity); // retire côté inverse
                
            }
            entity.getMovies().clear(); // puis vide côté courant
        }
        
        if (entity.getTvShows() != null) {
            for (TVShow item : new ArrayList<>(entity.getTvShows())) {
                
                item.getLanguages().remove(entity); // retire côté inverse
                
            }
            entity.getTvShows().clear(); // puis vide côté courant
        }
        
        if (entity.getPodcasts() != null) {
            for (Podcast item : new ArrayList<>(entity.getPodcasts())) {
                
                item.getLanguages().remove(entity); // retire côté inverse
                
            }
            entity.getPodcasts().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}