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
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.repository.AudiobookRepository;
import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.repository.MusicVideoRepository;
import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.repository.ArtistGroupRepository;
import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.repository.TourRepository;

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
    private final AudiobookRepository authoredAudiobooksRepository;
    private final MusicVideoRepository directedMusicVideosRepository;
    private final ArtistGroupRepository groupsRepository;
    private final TourRepository toursRepository;

    public ArtistService(ArtistRepository repository, UserProfileRepository favoriteArtistsRepository, MusicTrackRepository composedMusicRepository, AlbumRepository albumsRepository, BookRepository booksAuthoredRepository, LiveEventRepository participatedInEventsRepository, PodcastRepository hostedPodcastsRepository, MerchandiseRepository managedMerchandiseRepository, VideoGameRepository managedGamesRepository, ManagerRepository managerRepository, ArtistAwardRepository awardsRepository, MovieRepository actedInMoviesRepository, MovieRepository directedMoviesRepository, TVShowRepository directedShowsRepository, DigitalAssetRepository managedAssetsRepository, TVShowRepository actedInShowsRepository, ArtistSocialMediaRepository socialMediaLinksRepository, EpisodeCreditRepository episodeCreditsRepository, AudiobookRepository authoredAudiobooksRepository, MusicVideoRepository directedMusicVideosRepository, ArtistGroupRepository groupsRepository, TourRepository toursRepository)
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
        this.authoredAudiobooksRepository = authoredAudiobooksRepository;
        this.directedMusicVideosRepository = directedMusicVideosRepository;
        this.groupsRepository = groupsRepository;
        this.toursRepository = toursRepository;
    }

    @Override
    public Artist save(Artist artist) {
    // ---------- OneToMany ----------
        if (artist.getComposedMusic() != null) {
            List<MusicTrack> managedComposedMusic = new ArrayList<>();
            for (MusicTrack item : artist.getComposedMusic()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = composedMusicRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));

                     existingItem.setArtist(artist);
                     managedComposedMusic.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedComposedMusic.add(item);
                }
            }
            artist.setComposedMusic(managedComposedMusic);
        }
    
        if (artist.getAlbums() != null) {
            List<Album> managedAlbums = new ArrayList<>();
            for (Album item : artist.getAlbums()) {
                if (item.getId() != null) {
                    Album existingItem = albumsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Album not found"));

                     existingItem.setArtist(artist);
                     managedAlbums.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedAlbums.add(item);
                }
            }
            artist.setAlbums(managedAlbums);
        }
    
        if (artist.getBooksAuthored() != null) {
            List<Book> managedBooksAuthored = new ArrayList<>();
            for (Book item : artist.getBooksAuthored()) {
                if (item.getId() != null) {
                    Book existingItem = booksAuthoredRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                     existingItem.setAuthor(artist);
                     managedBooksAuthored.add(existingItem);
                } else {
                    item.setAuthor(artist);
                    managedBooksAuthored.add(item);
                }
            }
            artist.setBooksAuthored(managedBooksAuthored);
        }
    
        if (artist.getHostedPodcasts() != null) {
            List<Podcast> managedHostedPodcasts = new ArrayList<>();
            for (Podcast item : artist.getHostedPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = hostedPodcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));

                     existingItem.setHost(artist);
                     managedHostedPodcasts.add(existingItem);
                } else {
                    item.setHost(artist);
                    managedHostedPodcasts.add(item);
                }
            }
            artist.setHostedPodcasts(managedHostedPodcasts);
        }
    
        if (artist.getManagedMerchandise() != null) {
            List<Merchandise> managedManagedMerchandise = new ArrayList<>();
            for (Merchandise item : artist.getManagedMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = managedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                     existingItem.setArtist(artist);
                     managedManagedMerchandise.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedManagedMerchandise.add(item);
                }
            }
            artist.setManagedMerchandise(managedManagedMerchandise);
        }
    
    
        if (artist.getAwards() != null) {
            List<ArtistAward> managedAwards = new ArrayList<>();
            for (ArtistAward item : artist.getAwards()) {
                if (item.getId() != null) {
                    ArtistAward existingItem = awardsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistAward not found"));

                     existingItem.setArtist(artist);
                     managedAwards.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedAwards.add(item);
                }
            }
            artist.setAwards(managedAwards);
        }
    
        if (artist.getDirectedMovies() != null) {
            List<Movie> managedDirectedMovies = new ArrayList<>();
            for (Movie item : artist.getDirectedMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = directedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));

                     existingItem.setDirector(artist);
                     managedDirectedMovies.add(existingItem);
                } else {
                    item.setDirector(artist);
                    managedDirectedMovies.add(item);
                }
            }
            artist.setDirectedMovies(managedDirectedMovies);
        }
    
        if (artist.getDirectedShows() != null) {
            List<TVShow> managedDirectedShows = new ArrayList<>();
            for (TVShow item : artist.getDirectedShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = directedShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));

                     existingItem.setDirector(artist);
                     managedDirectedShows.add(existingItem);
                } else {
                    item.setDirector(artist);
                    managedDirectedShows.add(item);
                }
            }
            artist.setDirectedShows(managedDirectedShows);
        }
    
        if (artist.getManagedAssets() != null) {
            List<DigitalAsset> managedManagedAssets = new ArrayList<>();
            for (DigitalAsset item : artist.getManagedAssets()) {
                if (item.getId() != null) {
                    DigitalAsset existingItem = managedAssetsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));

                     existingItem.setArtist(artist);
                     managedManagedAssets.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedManagedAssets.add(item);
                }
            }
            artist.setManagedAssets(managedManagedAssets);
        }
    
        if (artist.getSocialMediaLinks() != null) {
            List<ArtistSocialMedia> managedSocialMediaLinks = new ArrayList<>();
            for (ArtistSocialMedia item : artist.getSocialMediaLinks()) {
                if (item.getId() != null) {
                    ArtistSocialMedia existingItem = socialMediaLinksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));

                     existingItem.setArtist(artist);
                     managedSocialMediaLinks.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedSocialMediaLinks.add(item);
                }
            }
            artist.setSocialMediaLinks(managedSocialMediaLinks);
        }
    
        if (artist.getEpisodeCredits() != null) {
            List<EpisodeCredit> managedEpisodeCredits = new ArrayList<>();
            for (EpisodeCredit item : artist.getEpisodeCredits()) {
                if (item.getId() != null) {
                    EpisodeCredit existingItem = episodeCreditsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));

                     existingItem.setArtist(artist);
                     managedEpisodeCredits.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedEpisodeCredits.add(item);
                }
            }
            artist.setEpisodeCredits(managedEpisodeCredits);
        }
    
        if (artist.getAuthoredAudiobooks() != null) {
            List<Audiobook> managedAuthoredAudiobooks = new ArrayList<>();
            for (Audiobook item : artist.getAuthoredAudiobooks()) {
                if (item.getId() != null) {
                    Audiobook existingItem = authoredAudiobooksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Audiobook not found"));

                     existingItem.setAuthor(artist);
                     managedAuthoredAudiobooks.add(existingItem);
                } else {
                    item.setAuthor(artist);
                    managedAuthoredAudiobooks.add(item);
                }
            }
            artist.setAuthoredAudiobooks(managedAuthoredAudiobooks);
        }
    
        if (artist.getDirectedMusicVideos() != null) {
            List<MusicVideo> managedDirectedMusicVideos = new ArrayList<>();
            for (MusicVideo item : artist.getDirectedMusicVideos()) {
                if (item.getId() != null) {
                    MusicVideo existingItem = directedMusicVideosRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicVideo not found"));

                     existingItem.setDirector(artist);
                     managedDirectedMusicVideos.add(existingItem);
                } else {
                    item.setDirector(artist);
                    managedDirectedMusicVideos.add(item);
                }
            }
            artist.setDirectedMusicVideos(managedDirectedMusicVideos);
        }
    
        if (artist.getTours() != null) {
            List<Tour> managedTours = new ArrayList<>();
            for (Tour item : artist.getTours()) {
                if (item.getId() != null) {
                    Tour existingItem = toursRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Tour not found"));

                     existingItem.setArtist(artist);
                     managedTours.add(existingItem);
                } else {
                    item.setArtist(artist);
                    managedTours.add(item);
                }
            }
            artist.setTours(managedTours);
        }
    
    // ---------- ManyToMany ----------
        if (artist.getFavoriteArtists() != null &&
            !artist.getFavoriteArtists().isEmpty()) {

            List<UserProfile> attachedFavoriteArtists = new ArrayList<>();
            for (UserProfile item : artist.getFavoriteArtists()) {
                if (item.getId() != null) {
                    UserProfile existingItem = favoriteArtistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedFavoriteArtists.add(existingItem);
                } else {

                    UserProfile newItem = favoriteArtistsRepository.save(item);
                    attachedFavoriteArtists.add(newItem);
                }
            }

            artist.setFavoriteArtists(attachedFavoriteArtists);

            // côté propriétaire (UserProfile → Artist)
            attachedFavoriteArtists.forEach(it -> it.getFavoriteArtists().add(artist));
        }
        
        if (artist.getParticipatedInEvents() != null &&
            !artist.getParticipatedInEvents().isEmpty()) {

            List<LiveEvent> attachedParticipatedInEvents = new ArrayList<>();
            for (LiveEvent item : artist.getParticipatedInEvents()) {
                if (item.getId() != null) {
                    LiveEvent existingItem = participatedInEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found with id " + item.getId()));
                    attachedParticipatedInEvents.add(existingItem);
                } else {

                    LiveEvent newItem = participatedInEventsRepository.save(item);
                    attachedParticipatedInEvents.add(newItem);
                }
            }

            artist.setParticipatedInEvents(attachedParticipatedInEvents);

            // côté propriétaire (LiveEvent → Artist)
            attachedParticipatedInEvents.forEach(it -> it.getPerformers().add(artist));
        }
        
        if (artist.getActedInMovies() != null &&
            !artist.getActedInMovies().isEmpty()) {

            List<Movie> attachedActedInMovies = new ArrayList<>();
            for (Movie item : artist.getActedInMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = actedInMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedActedInMovies.add(existingItem);
                } else {

                    Movie newItem = actedInMoviesRepository.save(item);
                    attachedActedInMovies.add(newItem);
                }
            }

            artist.setActedInMovies(attachedActedInMovies);

            // côté propriétaire (Movie → Artist)
            attachedActedInMovies.forEach(it -> it.getCast().add(artist));
        }
        
        if (artist.getActedInShows() != null &&
            !artist.getActedInShows().isEmpty()) {

            List<TVShow> attachedActedInShows = new ArrayList<>();
            for (TVShow item : artist.getActedInShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = actedInShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId()));
                    attachedActedInShows.add(existingItem);
                } else {

                    TVShow newItem = actedInShowsRepository.save(item);
                    attachedActedInShows.add(newItem);
                }
            }

            artist.setActedInShows(attachedActedInShows);

            // côté propriétaire (TVShow → Artist)
            attachedActedInShows.forEach(it -> it.getCast().add(artist));
        }
        
        if (artist.getGroups() != null &&
            !artist.getGroups().isEmpty()) {

            List<ArtistGroup> attachedGroups = new ArrayList<>();
            for (ArtistGroup item : artist.getGroups()) {
                if (item.getId() != null) {
                    ArtistGroup existingItem = groupsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistGroup not found with id " + item.getId()));
                    attachedGroups.add(existingItem);
                } else {

                    ArtistGroup newItem = groupsRepository.save(item);
                    attachedGroups.add(newItem);
                }
            }

            artist.setGroups(attachedGroups);

            // côté propriétaire (ArtistGroup → Artist)
            attachedGroups.forEach(it -> it.getMembers().add(artist));
        }
        
    // ---------- ManyToOne ----------
        if (artist.getManager() != null) {
            if (artist.getManager().getId() != null) {
                Manager existingManager = managerRepository.findById(
                    artist.getManager().getId()
                ).orElseThrow(() -> new RuntimeException("Manager not found with id "
                    + artist.getManager().getId()));
                artist.setManager(existingManager);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Manager newManager = managerRepository.save(artist.getManager());
                artist.setManager(newManager);
            }
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
        if (artistRequest.getManager() != null &&
            artistRequest.getManager().getId() != null) {

            Manager existingManager = managerRepository.findById(
                artistRequest.getManager().getId()
            ).orElseThrow(() -> new RuntimeException("Manager not found"));

            existing.setManager(existingManager);
        } else {
            existing.setManager(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (artistRequest.getFavoriteArtists() != null) {
            existing.getFavoriteArtists().clear();

            List<UserProfile> favoriteArtistsList = artistRequest.getFavoriteArtists().stream()
                .map(item -> favoriteArtistsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getFavoriteArtists().addAll(favoriteArtistsList);

            // Mettre à jour le côté inverse
            favoriteArtistsList.forEach(it -> {
                if (!it.getFavoriteArtists().contains(existing)) {
                    it.getFavoriteArtists().add(existing);
                }
            });
        }
        
        if (artistRequest.getParticipatedInEvents() != null) {
            existing.getParticipatedInEvents().clear();

            List<LiveEvent> participatedInEventsList = artistRequest.getParticipatedInEvents().stream()
                .map(item -> participatedInEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found")))
                .collect(Collectors.toList());

            existing.getParticipatedInEvents().addAll(participatedInEventsList);

            // Mettre à jour le côté inverse
            participatedInEventsList.forEach(it -> {
                if (!it.getPerformers().contains(existing)) {
                    it.getPerformers().add(existing);
                }
            });
        }
        
        if (artistRequest.getActedInMovies() != null) {
            existing.getActedInMovies().clear();

            List<Movie> actedInMoviesList = artistRequest.getActedInMovies().stream()
                .map(item -> actedInMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getActedInMovies().addAll(actedInMoviesList);

            // Mettre à jour le côté inverse
            actedInMoviesList.forEach(it -> {
                if (!it.getCast().contains(existing)) {
                    it.getCast().add(existing);
                }
            });
        }
        
        if (artistRequest.getActedInShows() != null) {
            existing.getActedInShows().clear();

            List<TVShow> actedInShowsList = artistRequest.getActedInShows().stream()
                .map(item -> actedInShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());

            existing.getActedInShows().addAll(actedInShowsList);

            // Mettre à jour le côté inverse
            actedInShowsList.forEach(it -> {
                if (!it.getCast().contains(existing)) {
                    it.getCast().add(existing);
                }
            });
        }
        
        if (artistRequest.getGroups() != null) {
            existing.getGroups().clear();

            List<ArtistGroup> groupsList = artistRequest.getGroups().stream()
                .map(item -> groupsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ArtistGroup not found")))
                .collect(Collectors.toList());

            existing.getGroups().addAll(groupsList);

            // Mettre à jour le côté inverse
            groupsList.forEach(it -> {
                if (!it.getMembers().contains(existing)) {
                    it.getMembers().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getComposedMusic().clear();

        if (artistRequest.getComposedMusic() != null) {
            for (var item : artistRequest.getComposedMusic()) {
                MusicTrack existingItem;
                if (item.getId() != null) {
                    existingItem = composedMusicRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getComposedMusic().add(existingItem);
            }
        }
        
        existing.getAlbums().clear();

        if (artistRequest.getAlbums() != null) {
            for (var item : artistRequest.getAlbums()) {
                Album existingItem;
                if (item.getId() != null) {
                    existingItem = albumsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Album not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getAlbums().add(existingItem);
            }
        }
        
        existing.getBooksAuthored().clear();

        if (artistRequest.getBooksAuthored() != null) {
            for (var item : artistRequest.getBooksAuthored()) {
                Book existingItem;
                if (item.getId() != null) {
                    existingItem = booksAuthoredRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getBooksAuthored().add(existingItem);
            }
        }
        
        existing.getHostedPodcasts().clear();

        if (artistRequest.getHostedPodcasts() != null) {
            for (var item : artistRequest.getHostedPodcasts()) {
                Podcast existingItem;
                if (item.getId() != null) {
                    existingItem = hostedPodcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found"));
                } else {
                existingItem = item;
                }

                existingItem.setHost(existing);
                existing.getHostedPodcasts().add(existingItem);
            }
        }
        
        existing.getManagedMerchandise().clear();

        if (artistRequest.getManagedMerchandise() != null) {
            for (var item : artistRequest.getManagedMerchandise()) {
                Merchandise existingItem;
                if (item.getId() != null) {
                    existingItem = managedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getManagedMerchandise().add(existingItem);
            }
        }
        
        existing.getManagedGames().clear();

        if (artistRequest.getManagedGames() != null) {
            for (var item : artistRequest.getManagedGames()) {
                VideoGame existingItem;
                if (item.getId() != null) {
                    existingItem = managedGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDeveloper(existing);
                existing.getManagedGames().add(existingItem);
            }
        }
        
        existing.getAwards().clear();

        if (artistRequest.getAwards() != null) {
            for (var item : artistRequest.getAwards()) {
                ArtistAward existingItem;
                if (item.getId() != null) {
                    existingItem = awardsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistAward not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getAwards().add(existingItem);
            }
        }
        
        existing.getDirectedMovies().clear();

        if (artistRequest.getDirectedMovies() != null) {
            for (var item : artistRequest.getDirectedMovies()) {
                Movie existingItem;
                if (item.getId() != null) {
                    existingItem = directedMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDirector(existing);
                existing.getDirectedMovies().add(existingItem);
            }
        }
        
        existing.getDirectedShows().clear();

        if (artistRequest.getDirectedShows() != null) {
            for (var item : artistRequest.getDirectedShows()) {
                TVShow existingItem;
                if (item.getId() != null) {
                    existingItem = directedShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDirector(existing);
                existing.getDirectedShows().add(existingItem);
            }
        }
        
        existing.getManagedAssets().clear();

        if (artistRequest.getManagedAssets() != null) {
            for (var item : artistRequest.getManagedAssets()) {
                DigitalAsset existingItem;
                if (item.getId() != null) {
                    existingItem = managedAssetsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalAsset not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getManagedAssets().add(existingItem);
            }
        }
        
        existing.getSocialMediaLinks().clear();

        if (artistRequest.getSocialMediaLinks() != null) {
            for (var item : artistRequest.getSocialMediaLinks()) {
                ArtistSocialMedia existingItem;
                if (item.getId() != null) {
                    existingItem = socialMediaLinksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ArtistSocialMedia not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getSocialMediaLinks().add(existingItem);
            }
        }
        
        existing.getEpisodeCredits().clear();

        if (artistRequest.getEpisodeCredits() != null) {
            for (var item : artistRequest.getEpisodeCredits()) {
                EpisodeCredit existingItem;
                if (item.getId() != null) {
                    existingItem = episodeCreditsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getEpisodeCredits().add(existingItem);
            }
        }
        
        existing.getAuthoredAudiobooks().clear();

        if (artistRequest.getAuthoredAudiobooks() != null) {
            for (var item : artistRequest.getAuthoredAudiobooks()) {
                Audiobook existingItem;
                if (item.getId() != null) {
                    existingItem = authoredAudiobooksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Audiobook not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getAuthoredAudiobooks().add(existingItem);
            }
        }
        
        existing.getDirectedMusicVideos().clear();

        if (artistRequest.getDirectedMusicVideos() != null) {
            for (var item : artistRequest.getDirectedMusicVideos()) {
                MusicVideo existingItem;
                if (item.getId() != null) {
                    existingItem = directedMusicVideosRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicVideo not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDirector(existing);
                existing.getDirectedMusicVideos().add(existingItem);
            }
        }
        
        existing.getTours().clear();

        if (artistRequest.getTours() != null) {
            for (var item : artistRequest.getTours()) {
                Tour existingItem;
                if (item.getId() != null) {
                    existingItem = toursRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Tour not found"));
                } else {
                existingItem = item;
                }

                existingItem.setArtist(existing);
                existing.getTours().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
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
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getComposedMusic().clear();
        }
        
        if (entity.getAlbums() != null) {
            for (var child : entity.getAlbums()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getAlbums().clear();
        }
        
        if (entity.getBooksAuthored() != null) {
            for (var child : entity.getBooksAuthored()) {
                // retirer la référence inverse
                child.setAuthor(null);
            }
            entity.getBooksAuthored().clear();
        }
        
        if (entity.getHostedPodcasts() != null) {
            for (var child : entity.getHostedPodcasts()) {
                // retirer la référence inverse
                child.setHost(null);
            }
            entity.getHostedPodcasts().clear();
        }
        
        if (entity.getManagedMerchandise() != null) {
            for (var child : entity.getManagedMerchandise()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getManagedMerchandise().clear();
        }
        
        if (entity.getManagedGames() != null) {
            for (var child : entity.getManagedGames()) {
                // retirer la référence inverse
                child.setDeveloper(null);
            }
            entity.getManagedGames().clear();
        }
        
        if (entity.getAwards() != null) {
            for (var child : entity.getAwards()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getAwards().clear();
        }
        
        if (entity.getDirectedMovies() != null) {
            for (var child : entity.getDirectedMovies()) {
                // retirer la référence inverse
                child.setDirector(null);
            }
            entity.getDirectedMovies().clear();
        }
        
        if (entity.getDirectedShows() != null) {
            for (var child : entity.getDirectedShows()) {
                // retirer la référence inverse
                child.setDirector(null);
            }
            entity.getDirectedShows().clear();
        }
        
        if (entity.getManagedAssets() != null) {
            for (var child : entity.getManagedAssets()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getManagedAssets().clear();
        }
        
        if (entity.getSocialMediaLinks() != null) {
            for (var child : entity.getSocialMediaLinks()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getSocialMediaLinks().clear();
        }
        
        if (entity.getEpisodeCredits() != null) {
            for (var child : entity.getEpisodeCredits()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getEpisodeCredits().clear();
        }
        
        if (entity.getAuthoredAudiobooks() != null) {
            for (var child : entity.getAuthoredAudiobooks()) {
                // retirer la référence inverse
                child.setAuthor(null);
            }
            entity.getAuthoredAudiobooks().clear();
        }
        
        if (entity.getDirectedMusicVideos() != null) {
            for (var child : entity.getDirectedMusicVideos()) {
                // retirer la référence inverse
                child.setDirector(null);
            }
            entity.getDirectedMusicVideos().clear();
        }
        
        if (entity.getTours() != null) {
            for (var child : entity.getTours()) {
                // retirer la référence inverse
                child.setArtist(null);
            }
            entity.getTours().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getFavoriteArtists() != null) {
            for (UserProfile item : new ArrayList<>(entity.getFavoriteArtists())) {
                
                item.getFavoriteArtists().remove(entity); // retire côté inverse
            }
            entity.getFavoriteArtists().clear(); // puis vide côté courant
        }
        
        if (entity.getParticipatedInEvents() != null) {
            for (LiveEvent item : new ArrayList<>(entity.getParticipatedInEvents())) {
                
                item.getPerformers().remove(entity); // retire côté inverse
            }
            entity.getParticipatedInEvents().clear(); // puis vide côté courant
        }
        
        if (entity.getActedInMovies() != null) {
            for (Movie item : new ArrayList<>(entity.getActedInMovies())) {
                
                item.getCast().remove(entity); // retire côté inverse
            }
            entity.getActedInMovies().clear(); // puis vide côté courant
        }
        
        if (entity.getActedInShows() != null) {
            for (TVShow item : new ArrayList<>(entity.getActedInShows())) {
                
                item.getCast().remove(entity); // retire côté inverse
            }
            entity.getActedInShows().clear(); // puis vide côté courant
        }
        
        if (entity.getGroups() != null) {
            for (ArtistGroup item : new ArrayList<>(entity.getGroups())) {
                
                item.getMembers().remove(entity); // retire côté inverse
            }
            entity.getGroups().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getManager() != null) {
            entity.setManager(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Artist> saveAll(List<Artist> artistList) {

        return artistRepository.saveAll(artistList);
    }

}