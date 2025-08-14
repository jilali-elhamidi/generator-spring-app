package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.repository.AlbumRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.repository.ManagerRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.repository.ArtistAwardRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.repository.DigitalAssetRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.repository.ArtistSocialMediaRepository;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.repository.EpisodeCreditRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ArtistService extends BaseService<Artist> {

    protected final ArtistRepository artistRepository;
    private final UserProfileRepository favoriteArtistsRepository;
    private final MusicTrackRepository composedMusicRepository;
    private final AlbumRepository albumsRepository;
    private final BookRepository booksAuthoredRepository;
    private final LiveEventRepository participatedInEventsRepository;
    private final PodcastRepository hostedPodcastsRepository;
    private final MerchandiseRepository managedMerchandiseRepository;
    private final VideoGameRepository managedGamesRepository;
    private final ManagerRepository managerRepository;
    private final ArtistAwardRepository awardsRepository;
    private final MovieRepository actedInMoviesRepository;
    private final MovieRepository directedMoviesRepository;
    private final TVShowRepository directedShowsRepository;
    private final DigitalAssetRepository managedAssetsRepository;
    private final TVShowRepository actedInShowsRepository;
    private final ArtistSocialMediaRepository socialMediaLinksRepository;
    private final EpisodeCreditRepository episodeCreditsRepository;

    public ArtistService(ArtistRepository repository,UserProfileRepository favoriteArtistsRepository,MusicTrackRepository composedMusicRepository,AlbumRepository albumsRepository,BookRepository booksAuthoredRepository,LiveEventRepository participatedInEventsRepository,PodcastRepository hostedPodcastsRepository,MerchandiseRepository managedMerchandiseRepository,VideoGameRepository managedGamesRepository,ManagerRepository managerRepository,ArtistAwardRepository awardsRepository,MovieRepository actedInMoviesRepository,MovieRepository directedMoviesRepository,TVShowRepository directedShowsRepository,DigitalAssetRepository managedAssetsRepository,TVShowRepository actedInShowsRepository,ArtistSocialMediaRepository socialMediaLinksRepository,EpisodeCreditRepository episodeCreditsRepository)
    {
        super(repository);
        this.artistRepository = repository;
        this.favoriteArtistsRepository = favoriteArtistsRepository;
        this.composedMusicRepository = composedMusicRepository;
        this.albumsRepository = albumsRepository;
        this.booksAuthoredRepository = booksAuthoredRepository;
        this.participatedInEventsRepository = participatedInEventsRepository;
        this.hostedPodcastsRepository = hostedPodcastsRepository;
        this.managedMerchandiseRepository = managedMerchandiseRepository;
        this.managedGamesRepository = managedGamesRepository;
        this.managerRepository = managerRepository;
        this.awardsRepository = awardsRepository;
        this.actedInMoviesRepository = actedInMoviesRepository;
        this.directedMoviesRepository = directedMoviesRepository;
        this.directedShowsRepository = directedShowsRepository;
        this.managedAssetsRepository = managedAssetsRepository;
        this.actedInShowsRepository = actedInShowsRepository;
        this.socialMediaLinksRepository = socialMediaLinksRepository;
        this.episodeCreditsRepository = episodeCreditsRepository;
    }

    @Override
    public Artist save(Artist artist) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getComposedMusic() != null) {
            List<MusicTrack> managedComposedMusic = new ArrayList<>();
            for (MusicTrack item : artist.getComposedMusic()) {
            if (item.getId() != null) {
            MusicTrack existingItem = composedMusicRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedComposedMusic.add(existingItem);
            } else {
            item.setArtist(artist);
            managedComposedMusic.add(item);
            }
            }
            artist.setComposedMusic(managedComposedMusic);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getAlbums() != null) {
            List<Album> managedAlbums = new ArrayList<>();
            for (Album item : artist.getAlbums()) {
            if (item.getId() != null) {
            Album existingItem = albumsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Album not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedAlbums.add(existingItem);
            } else {
            item.setArtist(artist);
            managedAlbums.add(item);
            }
            }
            artist.setAlbums(managedAlbums);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getBooksAuthored() != null) {
            List<Book> managedBooksAuthored = new ArrayList<>();
            for (Book item : artist.getBooksAuthored()) {
            if (item.getId() != null) {
            Book existingItem = booksAuthoredRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAuthor(artist);
            managedBooksAuthored.add(existingItem);
            } else {
            item.setAuthor(artist);
            managedBooksAuthored.add(item);
            }
            }
            artist.setBooksAuthored(managedBooksAuthored);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getHostedPodcasts() != null) {
            List<Podcast> managedHostedPodcasts = new ArrayList<>();
            for (Podcast item : artist.getHostedPodcasts()) {
            if (item.getId() != null) {
            Podcast existingItem = hostedPodcastsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Podcast not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setHost(artist);
            managedHostedPodcasts.add(existingItem);
            } else {
            item.setHost(artist);
            managedHostedPodcasts.add(item);
            }
            }
            artist.setHostedPodcasts(managedHostedPodcasts);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getManagedMerchandise() != null) {
            List<Merchandise> managedManagedMerchandise = new ArrayList<>();
            for (Merchandise item : artist.getManagedMerchandise()) {
            if (item.getId() != null) {
            Merchandise existingItem = managedMerchandiseRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Merchandise not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedManagedMerchandise.add(existingItem);
            } else {
            item.setArtist(artist);
            managedManagedMerchandise.add(item);
            }
            }
            artist.setManagedMerchandise(managedManagedMerchandise);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getAwards() != null) {
            List<ArtistAward> managedAwards = new ArrayList<>();
            for (ArtistAward item : artist.getAwards()) {
            if (item.getId() != null) {
            ArtistAward existingItem = awardsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ArtistAward not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedAwards.add(existingItem);
            } else {
            item.setArtist(artist);
            managedAwards.add(item);
            }
            }
            artist.setAwards(managedAwards);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getDirectedMovies() != null) {
            List<Movie> managedDirectedMovies = new ArrayList<>();
            for (Movie item : artist.getDirectedMovies()) {
            if (item.getId() != null) {
            Movie existingItem = directedMoviesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setDirector(artist);
            managedDirectedMovies.add(existingItem);
            } else {
            item.setDirector(artist);
            managedDirectedMovies.add(item);
            }
            }
            artist.setDirectedMovies(managedDirectedMovies);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getDirectedShows() != null) {
            List<TVShow> managedDirectedShows = new ArrayList<>();
            for (TVShow item : artist.getDirectedShows()) {
            if (item.getId() != null) {
            TVShow existingItem = directedShowsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TVShow not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setDirector(artist);
            managedDirectedShows.add(existingItem);
            } else {
            item.setDirector(artist);
            managedDirectedShows.add(item);
            }
            }
            artist.setDirectedShows(managedDirectedShows);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getManagedAssets() != null) {
            List<DigitalAsset> managedManagedAssets = new ArrayList<>();
            for (DigitalAsset item : artist.getManagedAssets()) {
            if (item.getId() != null) {
            DigitalAsset existingItem = managedAssetsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedManagedAssets.add(existingItem);
            } else {
            item.setArtist(artist);
            managedManagedAssets.add(item);
            }
            }
            artist.setManagedAssets(managedManagedAssets);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getSocialMediaLinks() != null) {
            List<ArtistSocialMedia> managedSocialMediaLinks = new ArrayList<>();
            for (ArtistSocialMedia item : artist.getSocialMediaLinks()) {
            if (item.getId() != null) {
            ArtistSocialMedia existingItem = socialMediaLinksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedSocialMediaLinks.add(existingItem);
            } else {
            item.setArtist(artist);
            managedSocialMediaLinks.add(item);
            }
            }
            artist.setSocialMediaLinks(managedSocialMediaLinks);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (artist.getEpisodeCredits() != null) {
            List<EpisodeCredit> managedEpisodeCredits = new ArrayList<>();
            for (EpisodeCredit item : artist.getEpisodeCredits()) {
            if (item.getId() != null) {
            EpisodeCredit existingItem = episodeCreditsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setArtist(artist);
            managedEpisodeCredits.add(existingItem);
            } else {
            item.setArtist(artist);
            managedEpisodeCredits.add(item);
            }
            }
            artist.setEpisodeCredits(managedEpisodeCredits);
            }
        
    

    
    
    
    
    
    
    
    
    if (artist.getManager() != null
        && artist.getManager().getId() != null) {
        Manager existingManager = managerRepository.findById(
        artist.getManager().getId()
        ).orElseThrow(() -> new RuntimeException("Manager not found"));
        artist.setManager(existingManager);
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
        if (artistRequest.getManager() != null &&
        artistRequest.getManager().getId() != null) {

        Manager existingManager = managerRepository.findById(
        artistRequest.getManager().getId()
        ).orElseThrow(() -> new RuntimeException("Manager not found"));

        existing.setManager(existingManager);
        } else {
        existing.setManager(null);
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
        List<MusicTrack> managedComposedMusic = new ArrayList<>();

        for (var item : artistRequest.getComposedMusic()) {
        if (item.getId() != null) {
        MusicTrack existingItem = composedMusicRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        existingItem.setArtist(existing);
        managedComposedMusic.add(existingItem);
        } else {
        item.setArtist(existing);
        managedComposedMusic.add(item);
        }
        }
        existing.setComposedMusic(managedComposedMusic);
        }
        existing.getAlbums().clear();

        if (artistRequest.getAlbums() != null) {
        List<Album> managedAlbums = new ArrayList<>();

        for (var item : artistRequest.getAlbums()) {
        if (item.getId() != null) {
        Album existingItem = albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found"));
        existingItem.setArtist(existing);
        managedAlbums.add(existingItem);
        } else {
        item.setArtist(existing);
        managedAlbums.add(item);
        }
        }
        existing.setAlbums(managedAlbums);
        }
        existing.getBooksAuthored().clear();

        if (artistRequest.getBooksAuthored() != null) {
        List<Book> managedBooksAuthored = new ArrayList<>();

        for (var item : artistRequest.getBooksAuthored()) {
        if (item.getId() != null) {
        Book existingItem = booksAuthoredRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found"));
        existingItem.setAuthor(existing);
        managedBooksAuthored.add(existingItem);
        } else {
        item.setAuthor(existing);
        managedBooksAuthored.add(item);
        }
        }
        existing.setBooksAuthored(managedBooksAuthored);
        }
        existing.getHostedPodcasts().clear();

        if (artistRequest.getHostedPodcasts() != null) {
        List<Podcast> managedHostedPodcasts = new ArrayList<>();

        for (var item : artistRequest.getHostedPodcasts()) {
        if (item.getId() != null) {
        Podcast existingItem = hostedPodcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found"));
        existingItem.setHost(existing);
        managedHostedPodcasts.add(existingItem);
        } else {
        item.setHost(existing);
        managedHostedPodcasts.add(item);
        }
        }
        existing.setHostedPodcasts(managedHostedPodcasts);
        }
        existing.getManagedMerchandise().clear();

        if (artistRequest.getManagedMerchandise() != null) {
        List<Merchandise> managedManagedMerchandise = new ArrayList<>();

        for (var item : artistRequest.getManagedMerchandise()) {
        if (item.getId() != null) {
        Merchandise existingItem = managedMerchandiseRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        existingItem.setArtist(existing);
        managedManagedMerchandise.add(existingItem);
        } else {
        item.setArtist(existing);
        managedManagedMerchandise.add(item);
        }
        }
        existing.setManagedMerchandise(managedManagedMerchandise);
        }
        existing.getManagedGames().clear();

        if (artistRequest.getManagedGames() != null) {
        List<VideoGame> managedManagedGames = new ArrayList<>();

        for (var item : artistRequest.getManagedGames()) {
        if (item.getId() != null) {
        VideoGame existingItem = managedGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existingItem.setDeveloper(existing);
        managedManagedGames.add(existingItem);
        } else {
        item.setDeveloper(existing);
        managedManagedGames.add(item);
        }
        }
        existing.setManagedGames(managedManagedGames);
        }
        existing.getAwards().clear();

        if (artistRequest.getAwards() != null) {
        List<ArtistAward> managedAwards = new ArrayList<>();

        for (var item : artistRequest.getAwards()) {
        if (item.getId() != null) {
        ArtistAward existingItem = awardsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ArtistAward not found"));
        existingItem.setArtist(existing);
        managedAwards.add(existingItem);
        } else {
        item.setArtist(existing);
        managedAwards.add(item);
        }
        }
        existing.setAwards(managedAwards);
        }
        existing.getDirectedMovies().clear();

        if (artistRequest.getDirectedMovies() != null) {
        List<Movie> managedDirectedMovies = new ArrayList<>();

        for (var item : artistRequest.getDirectedMovies()) {
        if (item.getId() != null) {
        Movie existingItem = directedMoviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        existingItem.setDirector(existing);
        managedDirectedMovies.add(existingItem);
        } else {
        item.setDirector(existing);
        managedDirectedMovies.add(item);
        }
        }
        existing.setDirectedMovies(managedDirectedMovies);
        }
        existing.getDirectedShows().clear();

        if (artistRequest.getDirectedShows() != null) {
        List<TVShow> managedDirectedShows = new ArrayList<>();

        for (var item : artistRequest.getDirectedShows()) {
        if (item.getId() != null) {
        TVShow existingItem = directedShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        existingItem.setDirector(existing);
        managedDirectedShows.add(existingItem);
        } else {
        item.setDirector(existing);
        managedDirectedShows.add(item);
        }
        }
        existing.setDirectedShows(managedDirectedShows);
        }
        existing.getManagedAssets().clear();

        if (artistRequest.getManagedAssets() != null) {
        List<DigitalAsset> managedManagedAssets = new ArrayList<>();

        for (var item : artistRequest.getManagedAssets()) {
        if (item.getId() != null) {
        DigitalAsset existingItem = managedAssetsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
        existingItem.setArtist(existing);
        managedManagedAssets.add(existingItem);
        } else {
        item.setArtist(existing);
        managedManagedAssets.add(item);
        }
        }
        existing.setManagedAssets(managedManagedAssets);
        }
        existing.getSocialMediaLinks().clear();

        if (artistRequest.getSocialMediaLinks() != null) {
        List<ArtistSocialMedia> managedSocialMediaLinks = new ArrayList<>();

        for (var item : artistRequest.getSocialMediaLinks()) {
        if (item.getId() != null) {
        ArtistSocialMedia existingItem = socialMediaLinksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
        existingItem.setArtist(existing);
        managedSocialMediaLinks.add(existingItem);
        } else {
        item.setArtist(existing);
        managedSocialMediaLinks.add(item);
        }
        }
        existing.setSocialMediaLinks(managedSocialMediaLinks);
        }
        existing.getEpisodeCredits().clear();

        if (artistRequest.getEpisodeCredits() != null) {
        List<EpisodeCredit> managedEpisodeCredits = new ArrayList<>();

        for (var item : artistRequest.getEpisodeCredits()) {
        if (item.getId() != null) {
        EpisodeCredit existingItem = episodeCreditsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
        existingItem.setArtist(existing);
        managedEpisodeCredits.add(existingItem);
        } else {
        item.setArtist(existing);
        managedEpisodeCredits.add(item);
        }
        }
        existing.setEpisodeCredits(managedEpisodeCredits);
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return artistRepository.save(existing);
    }


}