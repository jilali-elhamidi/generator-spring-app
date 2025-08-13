package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.repository.ContentProviderRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.model.Podcast;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ContentProviderService extends BaseService<ContentProvider> {

    protected final ContentProviderRepository contentproviderRepository;

    public ContentProviderService(ContentProviderRepository repository)
    {
        super(repository);
        this.contentproviderRepository = repository;
    }

    @Override
    public ContentProvider save(ContentProvider contentprovider) {

        if (contentprovider.getProvidedMovies() != null) {
            for (Movie item : contentprovider.getProvidedMovies()) {
            item.setProvider(contentprovider);
            }
        }

        if (contentprovider.getProvidedTvShows() != null) {
            for (TVShow item : contentprovider.getProvidedTvShows()) {
            item.setProvider(contentprovider);
            }
        }

        if (contentprovider.getProvidedMusicTracks() != null) {
            for (MusicTrack item : contentprovider.getProvidedMusicTracks()) {
            item.setProvider(contentprovider);
            }
        }

        if (contentprovider.getProvidedPodcasts() != null) {
            for (Podcast item : contentprovider.getProvidedPodcasts()) {
            item.setProvider(contentprovider);
            }
        }

        return contentproviderRepository.save(contentprovider);
    }


    public ContentProvider update(Long id, ContentProvider contentproviderRequest) {
        ContentProvider existing = contentproviderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentProvider not found"));

    // Copier les champs simples
        existing.setName(contentproviderRequest.getName());
        existing.setContactEmail(contentproviderRequest.getContactEmail());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getProvidedMovies().clear();
        if (contentproviderRequest.getProvidedMovies() != null) {
            for (var item : contentproviderRequest.getProvidedMovies()) {
            item.setProvider(existing);
            existing.getProvidedMovies().add(item);
            }
        }

        existing.getProvidedTvShows().clear();
        if (contentproviderRequest.getProvidedTvShows() != null) {
            for (var item : contentproviderRequest.getProvidedTvShows()) {
            item.setProvider(existing);
            existing.getProvidedTvShows().add(item);
            }
        }

        existing.getProvidedMusicTracks().clear();
        if (contentproviderRequest.getProvidedMusicTracks() != null) {
            for (var item : contentproviderRequest.getProvidedMusicTracks()) {
            item.setProvider(existing);
            existing.getProvidedMusicTracks().add(item);
            }
        }

        existing.getProvidedPodcasts().clear();
        if (contentproviderRequest.getProvidedPodcasts() != null) {
            for (var item : contentproviderRequest.getProvidedPodcasts()) {
            item.setProvider(existing);
            existing.getProvidedPodcasts().add(item);
            }
        }

    

    

    

    


        return contentproviderRepository.save(existing);
    }
}