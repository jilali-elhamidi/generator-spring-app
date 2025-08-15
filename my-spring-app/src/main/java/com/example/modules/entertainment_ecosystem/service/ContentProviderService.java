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

    public ContentProviderService(ContentProviderRepository repository,MovieRepository providedMoviesRepository,TVShowRepository providedTvShowsRepository,MusicTrackRepository providedMusicTracksRepository,PodcastRepository providedPodcastsRepository)
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


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentprovider.getProvidedMovies() != null) {
            List<Movie> managedProvidedMovies = new ArrayList<>();
            for (Movie item : contentprovider.getProvidedMovies()) {
            if (item.getId() != null) {
            Movie existingItem = providedMoviesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProvider(contentprovider);
            managedProvidedMovies.add(existingItem);
            } else {
            item.setProvider(contentprovider);
            managedProvidedMovies.add(item);
            }
            }
            contentprovider.setProvidedMovies(managedProvidedMovies);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentprovider.getProvidedTvShows() != null) {
            List<TVShow> managedProvidedTvShows = new ArrayList<>();
            for (TVShow item : contentprovider.getProvidedTvShows()) {
            if (item.getId() != null) {
            TVShow existingItem = providedTvShowsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TVShow not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProvider(contentprovider);
            managedProvidedTvShows.add(existingItem);
            } else {
            item.setProvider(contentprovider);
            managedProvidedTvShows.add(item);
            }
            }
            contentprovider.setProvidedTvShows(managedProvidedTvShows);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentprovider.getProvidedMusicTracks() != null) {
            List<MusicTrack> managedProvidedMusicTracks = new ArrayList<>();
            for (MusicTrack item : contentprovider.getProvidedMusicTracks()) {
            if (item.getId() != null) {
            MusicTrack existingItem = providedMusicTracksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProvider(contentprovider);
            managedProvidedMusicTracks.add(existingItem);
            } else {
            item.setProvider(contentprovider);
            managedProvidedMusicTracks.add(item);
            }
            }
            contentprovider.setProvidedMusicTracks(managedProvidedMusicTracks);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentprovider.getProvidedPodcasts() != null) {
            List<Podcast> managedProvidedPodcasts = new ArrayList<>();
            for (Podcast item : contentprovider.getProvidedPodcasts()) {
            if (item.getId() != null) {
            Podcast existingItem = providedPodcastsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Podcast not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProvider(contentprovider);
            managedProvidedPodcasts.add(existingItem);
            } else {
            item.setProvider(contentprovider);
            managedProvidedPodcasts.add(item);
            }
            }
            contentprovider.setProvidedPodcasts(managedProvidedPodcasts);
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
        // Vider la collection existante
        existing.getProvidedMovies().clear();

        if (contentproviderRequest.getProvidedMovies() != null) {
        for (var item : contentproviderRequest.getProvidedMovies()) {
        Movie existingItem;
        if (item.getId() != null) {
        existingItem = providedMoviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProvider(existing);

        // Ajouter directement dans la collection existante
        existing.getProvidedMovies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getProvidedTvShows().clear();

        if (contentproviderRequest.getProvidedTvShows() != null) {
        for (var item : contentproviderRequest.getProvidedTvShows()) {
        TVShow existingItem;
        if (item.getId() != null) {
        existingItem = providedTvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProvider(existing);

        // Ajouter directement dans la collection existante
        existing.getProvidedTvShows().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getProvidedMusicTracks().clear();

        if (contentproviderRequest.getProvidedMusicTracks() != null) {
        for (var item : contentproviderRequest.getProvidedMusicTracks()) {
        MusicTrack existingItem;
        if (item.getId() != null) {
        existingItem = providedMusicTracksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProvider(existing);

        // Ajouter directement dans la collection existante
        existing.getProvidedMusicTracks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getProvidedPodcasts().clear();

        if (contentproviderRequest.getProvidedPodcasts() != null) {
        for (var item : contentproviderRequest.getProvidedPodcasts()) {
        Podcast existingItem;
        if (item.getId() != null) {
        existingItem = providedPodcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProvider(existing);

        // Ajouter directement dans la collection existante
        existing.getProvidedPodcasts().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    


        return contentproviderRepository.save(existing);
    }


}