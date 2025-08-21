package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentProviderService extends BaseService<ContentProvider> {

    protected final ContentProviderRepository contentproviderRepository;
    private final MovieRepository providedMoviesRepository;
    private final TVShowRepository providedTvShowsRepository;
    private final MusicTrackRepository providedMusicTracksRepository;
    private final PodcastRepository providedPodcastsRepository;

    public ContentProviderService(ContentProviderRepository repository, MovieRepository providedMoviesRepository, TVShowRepository providedTvShowsRepository, MusicTrackRepository providedMusicTracksRepository, PodcastRepository providedPodcastsRepository)
    {
        super(repository);
        this.contentproviderRepository = repository;
        this.providedMoviesRepository = providedMoviesRepository;
        this.providedTvShowsRepository = providedTvShowsRepository;
        this.providedMusicTracksRepository = providedMusicTracksRepository;
        this.providedPodcastsRepository = providedPodcastsRepository;
    }

    @Override
    public ContentProvider save(ContentProvider contentprovider) {
    // ---------- OneToMany ----------
        if (contentprovider.getProvidedMovies() != null) {
            List<Movie> managedProvidedMovies = new ArrayList<>();
            for (Movie item : contentprovider.getProvidedMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = providedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));

                     existingItem.setProvider(contentprovider);
                     managedProvidedMovies.add(existingItem);
                } else {
                    item.setProvider(contentprovider);
                    managedProvidedMovies.add(item);
                }
            }
            contentprovider.setProvidedMovies(managedProvidedMovies);
        }
    
        if (contentprovider.getProvidedTvShows() != null) {
            List<TVShow> managedProvidedTvShows = new ArrayList<>();
            for (TVShow item : contentprovider.getProvidedTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = providedTvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));

                     existingItem.setProvider(contentprovider);
                     managedProvidedTvShows.add(existingItem);
                } else {
                    item.setProvider(contentprovider);
                    managedProvidedTvShows.add(item);
                }
            }
            contentprovider.setProvidedTvShows(managedProvidedTvShows);
        }
    
        if (contentprovider.getProvidedMusicTracks() != null) {
            List<MusicTrack> managedProvidedMusicTracks = new ArrayList<>();
            for (MusicTrack item : contentprovider.getProvidedMusicTracks()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = providedMusicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

                     existingItem.setProvider(contentprovider);
                     managedProvidedMusicTracks.add(existingItem);
                } else {
                    item.setProvider(contentprovider);
                    managedProvidedMusicTracks.add(item);
                }
            }
            contentprovider.setProvidedMusicTracks(managedProvidedMusicTracks);
        }
    
        if (contentprovider.getProvidedPodcasts() != null) {
            List<Podcast> managedProvidedPodcasts = new ArrayList<>();
            for (Podcast item : contentprovider.getProvidedPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = providedPodcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));

                     existingItem.setProvider(contentprovider);
                     managedProvidedPodcasts.add(existingItem);
                } else {
                    item.setProvider(contentprovider);
                    managedProvidedPodcasts.add(item);
                }
            }
            contentprovider.setProvidedPodcasts(managedProvidedPodcasts);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return contentproviderRepository.save(contentprovider);
}


    public ContentProvider update(Long id, ContentProvider contentproviderRequest) {
        ContentProvider existing = contentproviderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentProvider not found"));

    // Copier les champs simples
        existing.setName(contentproviderRequest.getName());
        existing.setContactEmail(contentproviderRequest.getContactEmail());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getProvidedMovies().clear();

        if (contentproviderRequest.getProvidedMovies() != null) {
            for (var item : contentproviderRequest.getProvidedMovies()) {
                Movie existingItem;
                if (item.getId() != null) {
                    existingItem = providedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProvider(existing);
                existing.getProvidedMovies().add(existingItem);
            }
        }
        
        existing.getProvidedTvShows().clear();

        if (contentproviderRequest.getProvidedTvShows() != null) {
            for (var item : contentproviderRequest.getProvidedTvShows()) {
                TVShow existingItem;
                if (item.getId() != null) {
                    existingItem = providedTvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProvider(existing);
                existing.getProvidedTvShows().add(existingItem);
            }
        }
        
        existing.getProvidedMusicTracks().clear();

        if (contentproviderRequest.getProvidedMusicTracks() != null) {
            for (var item : contentproviderRequest.getProvidedMusicTracks()) {
                MusicTrack existingItem;
                if (item.getId() != null) {
                    existingItem = providedMusicTracksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProvider(existing);
                existing.getProvidedMusicTracks().add(existingItem);
            }
        }
        
        existing.getProvidedPodcasts().clear();

        if (contentproviderRequest.getProvidedPodcasts() != null) {
            for (var item : contentproviderRequest.getProvidedPodcasts()) {
                Podcast existingItem;
                if (item.getId() != null) {
                    existingItem = providedPodcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProvider(existing);
                existing.getProvidedPodcasts().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return contentproviderRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ContentProvider> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ContentProvider entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getProvidedMovies() != null) {
            for (var child : entity.getProvidedMovies()) {
                // retirer la référence inverse
                child.setProvider(null);
            }
            entity.getProvidedMovies().clear();
        }
        
        if (entity.getProvidedTvShows() != null) {
            for (var child : entity.getProvidedTvShows()) {
                // retirer la référence inverse
                child.setProvider(null);
            }
            entity.getProvidedTvShows().clear();
        }
        
        if (entity.getProvidedMusicTracks() != null) {
            for (var child : entity.getProvidedMusicTracks()) {
                // retirer la référence inverse
                child.setProvider(null);
            }
            entity.getProvidedMusicTracks().clear();
        }
        
        if (entity.getProvidedPodcasts() != null) {
            for (var child : entity.getProvidedPodcasts()) {
                // retirer la référence inverse
                child.setProvider(null);
            }
            entity.getProvidedPodcasts().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}