package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Playlist;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserProfileService extends BaseService<UserProfile> {

    protected final UserProfileRepository userprofileRepository;
    private final MovieRepository watchlistMoviesRepository;
    private final ArtistRepository favoriteArtistsRepository;
    private final UserProfileRepository followedUsersRepository;
    private final UserProfileRepository followingUsersRepository;
    private final GenreRepository favoriteGenresRepository;
    private final EpisodeRepository watchedEpisodesRepository;
    private final VideoGameRepository playedGamesRepository;
    private final OnlineEventRepository attendedOnlineEventsRepository;
    private final MerchandiseRepository ownedMerchandiseRepository;
    private final PodcastRepository libraryPodcastsRepository;
    private final MusicTrackRepository listenedMusicRepository;

    public UserProfileService(UserProfileRepository repository,MovieRepository watchlistMoviesRepository,ArtistRepository favoriteArtistsRepository,UserProfileRepository followedUsersRepository,UserProfileRepository followingUsersRepository,GenreRepository favoriteGenresRepository,EpisodeRepository watchedEpisodesRepository,VideoGameRepository playedGamesRepository,OnlineEventRepository attendedOnlineEventsRepository,MerchandiseRepository ownedMerchandiseRepository,PodcastRepository libraryPodcastsRepository,MusicTrackRepository listenedMusicRepository)
    {
        super(repository);
        this.userprofileRepository = repository;
        this.watchlistMoviesRepository = watchlistMoviesRepository;
        this.favoriteArtistsRepository = favoriteArtistsRepository;
        this.followedUsersRepository = followedUsersRepository;
        this.followingUsersRepository = followingUsersRepository;
        this.favoriteGenresRepository = favoriteGenresRepository;
        this.watchedEpisodesRepository = watchedEpisodesRepository;
        this.playedGamesRepository = playedGamesRepository;
        this.attendedOnlineEventsRepository = attendedOnlineEventsRepository;
        this.ownedMerchandiseRepository = ownedMerchandiseRepository;
        this.libraryPodcastsRepository = libraryPodcastsRepository;
        this.listenedMusicRepository = listenedMusicRepository;
    }

    @Override
    public UserProfile save(UserProfile userprofile) {

        if (userprofile.getReviews() != null) {
            for (Review item : userprofile.getReviews()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getSubscriptions() != null) {
            for (Subscription item : userprofile.getSubscriptions()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getForumThreads() != null) {
            for (ForumThread item : userprofile.getForumThreads()) {
            item.setAuthor(userprofile);
            }
        }

        if (userprofile.getForumPosts() != null) {
            for (ForumPost item : userprofile.getForumPosts()) {
            item.setAuthor(userprofile);
            }
        }

        if (userprofile.getAchievements() != null) {
            for (Achievement item : userprofile.getAchievements()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getHostedOnlineEvents() != null) {
            for (OnlineEvent item : userprofile.getHostedOnlineEvents()) {
            item.setHost(userprofile);
            }
        }

        if (userprofile.getPlaylists() != null) {
            for (Playlist item : userprofile.getPlaylists()) {
            item.setOwner(userprofile);
            }
        }

        return userprofileRepository.save(userprofile);
    }


    public UserProfile update(Long id, UserProfile userprofileRequest) {
        UserProfile existing = userprofileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserProfile not found"));

    // Copier les champs simples
        existing.setUsername(userprofileRequest.getUsername());
        existing.setEmail(userprofileRequest.getEmail());
        existing.setPassword(userprofileRequest.getPassword());
        existing.setRegistrationDate(userprofileRequest.getRegistrationDate());
        existing.setCountry(userprofileRequest.getCountry());
        existing.setProfilePictureUrl(userprofileRequest.getProfilePictureUrl());
        existing.setLastActiveDate(userprofileRequest.getLastActiveDate());
        existing.setUserStatus(userprofileRequest.getUserStatus());
        existing.setBio(userprofileRequest.getBio());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (userprofileRequest.getWatchlistMovies() != null) {
            existing.getWatchlistMovies().clear();
            List<Movie> watchlistMoviesList = userprofileRequest.getWatchlistMovies().stream()
                .map(item -> watchlistMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getWatchlistMovies().addAll(watchlistMoviesList);
        }

        if (userprofileRequest.getFavoriteArtists() != null) {
            existing.getFavoriteArtists().clear();
            List<Artist> favoriteArtistsList = userprofileRequest.getFavoriteArtists().stream()
                .map(item -> favoriteArtistsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());
        existing.getFavoriteArtists().addAll(favoriteArtistsList);
        }

        if (userprofileRequest.getFollowedUsers() != null) {
            existing.getFollowedUsers().clear();
            List<UserProfile> followedUsersList = userprofileRequest.getFollowedUsers().stream()
                .map(item -> followedUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getFollowedUsers().addAll(followedUsersList);
        }

        if (userprofileRequest.getFollowingUsers() != null) {
            existing.getFollowingUsers().clear();
            List<UserProfile> followingUsersList = userprofileRequest.getFollowingUsers().stream()
                .map(item -> followingUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getFollowingUsers().addAll(followingUsersList);
        }

        if (userprofileRequest.getFavoriteGenres() != null) {
            existing.getFavoriteGenres().clear();
            List<Genre> favoriteGenresList = userprofileRequest.getFavoriteGenres().stream()
                .map(item -> favoriteGenresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getFavoriteGenres().addAll(favoriteGenresList);
        }

        if (userprofileRequest.getWatchedEpisodes() != null) {
            existing.getWatchedEpisodes().clear();
            List<Episode> watchedEpisodesList = userprofileRequest.getWatchedEpisodes().stream()
                .map(item -> watchedEpisodesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Episode not found")))
                .collect(Collectors.toList());
        existing.getWatchedEpisodes().addAll(watchedEpisodesList);
        }

        if (userprofileRequest.getPlayedGames() != null) {
            existing.getPlayedGames().clear();
            List<VideoGame> playedGamesList = userprofileRequest.getPlayedGames().stream()
                .map(item -> playedGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());
        existing.getPlayedGames().addAll(playedGamesList);
        }

        if (userprofileRequest.getAttendedOnlineEvents() != null) {
            existing.getAttendedOnlineEvents().clear();
            List<OnlineEvent> attendedOnlineEventsList = userprofileRequest.getAttendedOnlineEvents().stream()
                .map(item -> attendedOnlineEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("OnlineEvent not found")))
                .collect(Collectors.toList());
        existing.getAttendedOnlineEvents().addAll(attendedOnlineEventsList);
        }

        if (userprofileRequest.getOwnedMerchandise() != null) {
            existing.getOwnedMerchandise().clear();
            List<Merchandise> ownedMerchandiseList = userprofileRequest.getOwnedMerchandise().stream()
                .map(item -> ownedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());
        existing.getOwnedMerchandise().addAll(ownedMerchandiseList);
        }

        if (userprofileRequest.getLibraryPodcasts() != null) {
            existing.getLibraryPodcasts().clear();
            List<Podcast> libraryPodcastsList = userprofileRequest.getLibraryPodcasts().stream()
                .map(item -> libraryPodcastsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Podcast not found")))
                .collect(Collectors.toList());
        existing.getLibraryPodcasts().addAll(libraryPodcastsList);
        }

        if (userprofileRequest.getListenedMusic() != null) {
            existing.getListenedMusic().clear();
            List<MusicTrack> listenedMusicList = userprofileRequest.getListenedMusic().stream()
                .map(item -> listenedMusicRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
                .collect(Collectors.toList());
        existing.getListenedMusic().addAll(listenedMusicList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (userprofileRequest.getReviews() != null) {
            for (var item : userprofileRequest.getReviews()) {
            item.setUser(existing);
            existing.getReviews().add(item);
            }
        }

        existing.getSubscriptions().clear();
        if (userprofileRequest.getSubscriptions() != null) {
            for (var item : userprofileRequest.getSubscriptions()) {
            item.setUser(existing);
            existing.getSubscriptions().add(item);
            }
        }

        existing.getForumThreads().clear();
        if (userprofileRequest.getForumThreads() != null) {
            for (var item : userprofileRequest.getForumThreads()) {
            item.setAuthor(existing);
            existing.getForumThreads().add(item);
            }
        }

        existing.getForumPosts().clear();
        if (userprofileRequest.getForumPosts() != null) {
            for (var item : userprofileRequest.getForumPosts()) {
            item.setAuthor(existing);
            existing.getForumPosts().add(item);
            }
        }

        existing.getAchievements().clear();
        if (userprofileRequest.getAchievements() != null) {
            for (var item : userprofileRequest.getAchievements()) {
            item.setUser(existing);
            existing.getAchievements().add(item);
            }
        }

        existing.getHostedOnlineEvents().clear();
        if (userprofileRequest.getHostedOnlineEvents() != null) {
            for (var item : userprofileRequest.getHostedOnlineEvents()) {
            item.setHost(existing);
            existing.getHostedOnlineEvents().add(item);
            }
        }

        existing.getPlaylists().clear();
        if (userprofileRequest.getPlaylists() != null) {
            for (var item : userprofileRequest.getPlaylists()) {
            item.setOwner(existing);
            existing.getPlaylists().add(item);
            }
        }

        return userprofileRepository.save(existing);
    }
}