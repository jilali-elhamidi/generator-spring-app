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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ContentLanguageService extends BaseService<ContentLanguage> {

    protected final ContentLanguageRepository contentlanguageRepository;
    
    protected final MovieRepository moviesRepository;
    
    protected final TVShowRepository tvShowsRepository;
    
    protected final PodcastRepository podcastsRepository;
    

    public ContentLanguageService(ContentLanguageRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, PodcastRepository podcastsRepository)
    {
        super(repository);
        this.contentlanguageRepository = repository;
        
        this.moviesRepository = moviesRepository;
        
        this.tvShowsRepository = tvShowsRepository;
        
        this.podcastsRepository = podcastsRepository;
        
    }

    @Transactional
    @Override
    public ContentLanguage save(ContentLanguage contentlanguage) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (contentlanguage.getMovies() != null &&
            !contentlanguage.getMovies().isEmpty()) {

            List<Movie> attachedMovies = new ArrayList<>();
            for (Movie item : contentlanguage.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedMovies.add(existingItem);
                } else {

                    Movie newItem = moviesRepository.save(item);
                    attachedMovies.add(newItem);
                }
            }

            contentlanguage.setMovies(attachedMovies);

            // côté propriétaire (Movie → ContentLanguage)
            attachedMovies.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
        if (contentlanguage.getTvShows() != null &&
            !contentlanguage.getTvShows().isEmpty()) {

            List<TVShow> attachedTvShows = new ArrayList<>();
            for (TVShow item : contentlanguage.getTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId()));
                    attachedTvShows.add(existingItem);
                } else {

                    TVShow newItem = tvShowsRepository.save(item);
                    attachedTvShows.add(newItem);
                }
            }

            contentlanguage.setTvShows(attachedTvShows);

            // côté propriétaire (TVShow → ContentLanguage)
            attachedTvShows.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
        if (contentlanguage.getPodcasts() != null &&
            !contentlanguage.getPodcasts().isEmpty()) {

            List<Podcast> attachedPodcasts = new ArrayList<>();
            for (Podcast item : contentlanguage.getPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = podcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId()));
                    attachedPodcasts.add(existingItem);
                } else {

                    Podcast newItem = podcastsRepository.save(item);
                    attachedPodcasts.add(newItem);
                }
            }

            contentlanguage.setPodcasts(attachedPodcasts);

            // côté propriétaire (Podcast → ContentLanguage)
            attachedPodcasts.forEach(it -> it.getLanguages().add(contentlanguage));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return contentlanguageRepository.save(contentlanguage);
}

    @Transactional
    @Override
    public ContentLanguage update(Long id, ContentLanguage contentlanguageRequest) {
        ContentLanguage existing = contentlanguageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentLanguage not found"));

    // Copier les champs simples
        existing.setName(contentlanguageRequest.getName());
        existing.setCode(contentlanguageRequest.getCode());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<ContentLanguage> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ContentLanguage> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ContentLanguage.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ContentLanguage> saveAll(List<ContentLanguage> contentlanguageList) {
        return super.saveAll(contentlanguageList);
    }

}