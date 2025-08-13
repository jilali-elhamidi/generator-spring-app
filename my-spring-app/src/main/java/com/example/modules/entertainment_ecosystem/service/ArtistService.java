package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.repository.ManagerRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ArtistService extends BaseService<Artist> {

    protected final ArtistRepository artistRepository;
    private final UserProfileRepository favoriteArtistsRepository;
    private final LiveEventRepository participatedInEventsRepository;
    private final ManagerRepository managerRepository;
    private final MovieRepository actedInMoviesRepository;
    private final TVShowRepository actedInShowsRepository;

    public ArtistService(ArtistRepository repository,UserProfileRepository favoriteArtistsRepository,LiveEventRepository participatedInEventsRepository,ManagerRepository managerRepository,MovieRepository actedInMoviesRepository,TVShowRepository actedInShowsRepository)
    {
        super(repository);
        this.artistRepository = repository;
        this.favoriteArtistsRepository = favoriteArtistsRepository;
        this.participatedInEventsRepository = participatedInEventsRepository;
        this.managerRepository = managerRepository;
        this.actedInMoviesRepository = actedInMoviesRepository;
        this.actedInShowsRepository = actedInShowsRepository;
    }

    @Override
    public Artist save(Artist artist) {

        if (artist.getManager() != null && artist.getManager().getId() != null) {
        Manager manager = managerRepository.findById(artist.getManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        artist.setManager(manager);
        }

        if (artist.getComposedMusic() != null) {
            for (MusicTrack item : artist.getComposedMusic()) {
            item.setArtist(artist);
            }
        }

        if (artist.getAlbums() != null) {
            for (Album item : artist.getAlbums()) {
            item.setArtist(artist);
            }
        }

        if (artist.getBooksAuthored() != null) {
            for (Book item : artist.getBooksAuthored()) {
            item.setAuthor(artist);
            }
        }

        if (artist.getHostedPodcasts() != null) {
            for (Podcast item : artist.getHostedPodcasts()) {
            item.setHost(artist);
            }
        }

        if (artist.getManagedMerchandise() != null) {
            for (Merchandise item : artist.getManagedMerchandise()) {
            item.setArtist(artist);
            }
        }

        if (artist.getManagedGames() != null) {
            for (VideoGame item : artist.getManagedGames()) {
            item.setDeveloper(artist);
            }
        }

        if (artist.getAwards() != null) {
            for (ArtistAward item : artist.getAwards()) {
            item.setArtist(artist);
            }
        }

        if (artist.getDirectedMovies() != null) {
            for (Movie item : artist.getDirectedMovies()) {
            item.setDirector(artist);
            }
        }

        if (artist.getDirectedShows() != null) {
            for (TVShow item : artist.getDirectedShows()) {
            item.setDirector(artist);
            }
        }

        if (artist.getManagedAssets() != null) {
            for (DigitalAsset item : artist.getManagedAssets()) {
            item.setArtist(artist);
            }
        }

        if (artist.getSocialMediaLinks() != null) {
            for (ArtistSocialMedia item : artist.getSocialMediaLinks()) {
            item.setArtist(artist);
            }
        }

        if (artist.getEpisodeCredits() != null) {
            for (EpisodeCredit item : artist.getEpisodeCredits()) {
            item.setArtist(artist);
            }
        }

        return artistRepository.save(artist);
    }


    public Artist update(Long id, Artist artistRequest) {
        Artist existing = artistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Artist not found"));

    // Copier les champs simples
        existing.setName(artistRequest.getName());
        existing.setBio(artistRequest.getBio());
        existing.setBirthDate(artistRequest.getBirthDate());
        existing.setNationality(artistRequest.getNationality());

// Relations ManyToOne : mise à jour conditionnelle

        if (artistRequest.getManager() != null && artistRequest.getManager().getId() != null) {
        Manager manager = managerRepository.findById(artistRequest.getManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        existing.setManager(manager);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (artistRequest.getFavoriteArtists() != null) {
            existing.getFavoriteArtists().clear();
            List<UserProfile> favoriteArtistsList = artistRequest.getFavoriteArtists().stream()
                .map(item -> favoriteArtistsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getFavoriteArtists().addAll(favoriteArtistsList);
        }

        if (artistRequest.getParticipatedInEvents() != null) {
            existing.getParticipatedInEvents().clear();
            List<LiveEvent> participatedInEventsList = artistRequest.getParticipatedInEvents().stream()
                .map(item -> participatedInEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found")))
                .collect(Collectors.toList());
        existing.getParticipatedInEvents().addAll(participatedInEventsList);
        }

        if (artistRequest.getActedInMovies() != null) {
            existing.getActedInMovies().clear();
            List<Movie> actedInMoviesList = artistRequest.getActedInMovies().stream()
                .map(item -> actedInMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getActedInMovies().addAll(actedInMoviesList);
        }

        if (artistRequest.getActedInShows() != null) {
            existing.getActedInShows().clear();
            List<TVShow> actedInShowsList = artistRequest.getActedInShows().stream()
                .map(item -> actedInShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getActedInShows().addAll(actedInShowsList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getComposedMusic().clear();
        if (artistRequest.getComposedMusic() != null) {
            for (var item : artistRequest.getComposedMusic()) {
            item.setArtist(existing);
            existing.getComposedMusic().add(item);
            }
        }

        existing.getAlbums().clear();
        if (artistRequest.getAlbums() != null) {
            for (var item : artistRequest.getAlbums()) {
            item.setArtist(existing);
            existing.getAlbums().add(item);
            }
        }

        existing.getBooksAuthored().clear();
        if (artistRequest.getBooksAuthored() != null) {
            for (var item : artistRequest.getBooksAuthored()) {
            item.setAuthor(existing);
            existing.getBooksAuthored().add(item);
            }
        }

        existing.getHostedPodcasts().clear();
        if (artistRequest.getHostedPodcasts() != null) {
            for (var item : artistRequest.getHostedPodcasts()) {
            item.setHost(existing);
            existing.getHostedPodcasts().add(item);
            }
        }

        existing.getManagedMerchandise().clear();
        if (artistRequest.getManagedMerchandise() != null) {
            for (var item : artistRequest.getManagedMerchandise()) {
            item.setArtist(existing);
            existing.getManagedMerchandise().add(item);
            }
        }

        existing.getManagedGames().clear();
        if (artistRequest.getManagedGames() != null) {
            for (var item : artistRequest.getManagedGames()) {
            item.setDeveloper(existing);
            existing.getManagedGames().add(item);
            }
        }

        existing.getAwards().clear();
        if (artistRequest.getAwards() != null) {
            for (var item : artistRequest.getAwards()) {
            item.setArtist(existing);
            existing.getAwards().add(item);
            }
        }

        existing.getDirectedMovies().clear();
        if (artistRequest.getDirectedMovies() != null) {
            for (var item : artistRequest.getDirectedMovies()) {
            item.setDirector(existing);
            existing.getDirectedMovies().add(item);
            }
        }

        existing.getDirectedShows().clear();
        if (artistRequest.getDirectedShows() != null) {
            for (var item : artistRequest.getDirectedShows()) {
            item.setDirector(existing);
            existing.getDirectedShows().add(item);
            }
        }

        existing.getManagedAssets().clear();
        if (artistRequest.getManagedAssets() != null) {
            for (var item : artistRequest.getManagedAssets()) {
            item.setArtist(existing);
            existing.getManagedAssets().add(item);
            }
        }

        existing.getSocialMediaLinks().clear();
        if (artistRequest.getSocialMediaLinks() != null) {
            for (var item : artistRequest.getSocialMediaLinks()) {
            item.setArtist(existing);
            existing.getSocialMediaLinks().add(item);
            }
        }

        existing.getEpisodeCredits().clear();
        if (artistRequest.getEpisodeCredits() != null) {
            for (var item : artistRequest.getEpisodeCredits()) {
            item.setArtist(existing);
            existing.getEpisodeCredits().add(item);
            }
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return artistRepository.save(existing);
    }
}