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
import org.springframework.transaction.annotation.Transactional;
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
        // Vider la collection existante
        existing.getComposedMusic().clear();

        if (artistRequest.getComposedMusic() != null) {
        for (var item : artistRequest.getComposedMusic()) {
        MusicTrack existingItem;
        if (item.getId() != null) {
        existingItem = composedMusicRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getComposedMusic().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getAlbums().clear();

        if (artistRequest.getAlbums() != null) {
        for (var item : artistRequest.getAlbums()) {
        Album existingItem;
        if (item.getId() != null) {
        existingItem = albumsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Album not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getAlbums().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getBooksAuthored().clear();

        if (artistRequest.getBooksAuthored() != null) {
        for (var item : artistRequest.getBooksAuthored()) {
        Book existingItem;
        if (item.getId() != null) {
        existingItem = booksAuthoredRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAuthor(existing);

        // Ajouter directement dans la collection existante
        existing.getBooksAuthored().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getHostedPodcasts().clear();

        if (artistRequest.getHostedPodcasts() != null) {
        for (var item : artistRequest.getHostedPodcasts()) {
        Podcast existingItem;
        if (item.getId() != null) {
        existingItem = hostedPodcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setHost(existing);

        // Ajouter directement dans la collection existante
        existing.getHostedPodcasts().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getManagedMerchandise().clear();

        if (artistRequest.getManagedMerchandise() != null) {
        for (var item : artistRequest.getManagedMerchandise()) {
        Merchandise existingItem;
        if (item.getId() != null) {
        existingItem = managedMerchandiseRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getManagedMerchandise().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getManagedGames().clear();

        if (artistRequest.getManagedGames() != null) {
        for (var item : artistRequest.getManagedGames()) {
        VideoGame existingItem;
        if (item.getId() != null) {
        existingItem = managedGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setDeveloper(existing);

        // Ajouter directement dans la collection existante
        existing.getManagedGames().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getAwards().clear();

        if (artistRequest.getAwards() != null) {
        for (var item : artistRequest.getAwards()) {
        ArtistAward existingItem;
        if (item.getId() != null) {
        existingItem = awardsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ArtistAward not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getAwards().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getDirectedMovies().clear();

        if (artistRequest.getDirectedMovies() != null) {
        for (var item : artistRequest.getDirectedMovies()) {
        Movie existingItem;
        if (item.getId() != null) {
        existingItem = directedMoviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setDirector(existing);

        // Ajouter directement dans la collection existante
        existing.getDirectedMovies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getDirectedShows().clear();

        if (artistRequest.getDirectedShows() != null) {
        for (var item : artistRequest.getDirectedShows()) {
        TVShow existingItem;
        if (item.getId() != null) {
        existingItem = directedShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setDirector(existing);

        // Ajouter directement dans la collection existante
        existing.getDirectedShows().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getManagedAssets().clear();

        if (artistRequest.getManagedAssets() != null) {
        for (var item : artistRequest.getManagedAssets()) {
        DigitalAsset existingItem;
        if (item.getId() != null) {
        existingItem = managedAssetsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getManagedAssets().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSocialMediaLinks().clear();

        if (artistRequest.getSocialMediaLinks() != null) {
        for (var item : artistRequest.getSocialMediaLinks()) {
        ArtistSocialMedia existingItem;
        if (item.getId() != null) {
        existingItem = socialMediaLinksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getSocialMediaLinks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getEpisodeCredits().clear();

        if (artistRequest.getEpisodeCredits() != null) {
        for (var item : artistRequest.getEpisodeCredits()) {
        EpisodeCredit existingItem;
        if (item.getId() != null) {
        existingItem = episodeCreditsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setArtist(existing);

        // Ajouter directement dans la collection existante
        existing.getEpisodeCredits().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return artistRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Artist> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Artist entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getComposedMusic() != null) {
        for (var child : entity.getComposedMusic()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getComposedMusic().clear();
        }
    

    
        if (entity.getAlbums() != null) {
        for (var child : entity.getAlbums()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getAlbums().clear();
        }
    

    
        if (entity.getBooksAuthored() != null) {
        for (var child : entity.getBooksAuthored()) {
        
            child.setAuthor(null); // retirer la référence inverse
        
        }
        entity.getBooksAuthored().clear();
        }
    

    

    
        if (entity.getHostedPodcasts() != null) {
        for (var child : entity.getHostedPodcasts()) {
        
            child.setHost(null); // retirer la référence inverse
        
        }
        entity.getHostedPodcasts().clear();
        }
    

    
        if (entity.getManagedMerchandise() != null) {
        for (var child : entity.getManagedMerchandise()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getManagedMerchandise().clear();
        }
    

    
        if (entity.getManagedGames() != null) {
        for (var child : entity.getManagedGames()) {
        
            child.setDeveloper(null); // retirer la référence inverse
        
        }
        entity.getManagedGames().clear();
        }
    

    

    
        if (entity.getAwards() != null) {
        for (var child : entity.getAwards()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getAwards().clear();
        }
    

    

    
        if (entity.getDirectedMovies() != null) {
        for (var child : entity.getDirectedMovies()) {
        
            child.setDirector(null); // retirer la référence inverse
        
        }
        entity.getDirectedMovies().clear();
        }
    

    
        if (entity.getDirectedShows() != null) {
        for (var child : entity.getDirectedShows()) {
        
            child.setDirector(null); // retirer la référence inverse
        
        }
        entity.getDirectedShows().clear();
        }
    

    
        if (entity.getManagedAssets() != null) {
        for (var child : entity.getManagedAssets()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getManagedAssets().clear();
        }
    

    

    
        if (entity.getSocialMediaLinks() != null) {
        for (var child : entity.getSocialMediaLinks()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getSocialMediaLinks().clear();
        }
    

    
        if (entity.getEpisodeCredits() != null) {
        for (var child : entity.getEpisodeCredits()) {
        
            child.setArtist(null); // retirer la référence inverse
        
        }
        entity.getEpisodeCredits().clear();
        }
    


// --- Dissocier ManyToMany ---

    
        if (entity.getFavoriteArtists() != null) {
        entity.getFavoriteArtists().clear();
        }
    

    

    

    

    
        if (entity.getParticipatedInEvents() != null) {
        entity.getParticipatedInEvents().clear();
        }
    

    

    

    

    

    

    
        if (entity.getActedInMovies() != null) {
        entity.getActedInMovies().clear();
        }
    

    

    

    

    
        if (entity.getActedInShows() != null) {
        entity.getActedInShows().clear();
        }
    

    

    


// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    

    

    

    

    

    

    
        if (entity.getManager() != null) {
        entity.setManager(null);
        }
    

    

    

    

    

    

    

    

    


repository.delete(entity);
return true;
}
}