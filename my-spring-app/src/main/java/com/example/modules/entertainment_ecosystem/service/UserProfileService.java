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
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.model.UserMessage;

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
    private final UserWalletRepository walletRepository;

    public UserProfileService(UserProfileRepository repository,MovieRepository watchlistMoviesRepository,ArtistRepository favoriteArtistsRepository,UserProfileRepository followedUsersRepository,UserProfileRepository followingUsersRepository,GenreRepository favoriteGenresRepository,EpisodeRepository watchedEpisodesRepository,VideoGameRepository playedGamesRepository,OnlineEventRepository attendedOnlineEventsRepository,MerchandiseRepository ownedMerchandiseRepository,PodcastRepository libraryPodcastsRepository,MusicTrackRepository listenedMusicRepository,UserWalletRepository walletRepository)
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
            this.walletRepository = walletRepository;
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

        if (userprofile.getDigitalPurchases() != null) {
            for (DigitalPurchase item : userprofile.getDigitalPurchases()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getGameSessions() != null) {
            for (GameSession item : userprofile.getGameSessions()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getGameReviewComments() != null) {
            for (GameReviewComment item : userprofile.getGameReviewComments()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getUserPlaylists() != null) {
            for (UserPlaylist item : userprofile.getUserPlaylists()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getUserPlaylistItems() != null) {
            for (UserPlaylistItem item : userprofile.getUserPlaylistItems()) {
            item.setAddedBy(userprofile);
            }
        }

        if (userprofile.getGivenRatings() != null) {
            for (ReviewRating item : userprofile.getGivenRatings()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getLikedReviews() != null) {
            for (ReviewLike item : userprofile.getLikedReviews()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getActivityLogs() != null) {
            for (UserActivityLog item : userprofile.getActivityLogs()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getSettings() != null) {
            for (UserSetting item : userprofile.getSettings()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getFollowers() != null) {
            for (UserFollower item : userprofile.getFollowers()) {
            item.setFollowed(userprofile);
            }
        }

        if (userprofile.getFollowing() != null) {
            for (UserFollower item : userprofile.getFollowing()) {
            item.setFollower(userprofile);
            }
        }

        if (userprofile.getUserAchievements() != null) {
            for (UserAchievement item : userprofile.getUserAchievements()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getNotifications() != null) {
            for (Notification item : userprofile.getNotifications()) {
            item.setRecipient(userprofile);
            }
        }

        if (userprofile.getMerchandiseReviews() != null) {
            for (MerchandiseReview item : userprofile.getMerchandiseReviews()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getPreferences() != null) {
            for (UserPreference item : userprofile.getPreferences()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getMerchandiseSales() != null) {
            for (MerchandiseSale item : userprofile.getMerchandiseSales()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getGamePlaySessions() != null) {
            for (GamePlaySession item : userprofile.getGamePlaySessions()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getGameReviewUpvotes() != null) {
            for (GameReviewUpvote item : userprofile.getGameReviewUpvotes()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getGameReviewDownvotes() != null) {
            for (GameReviewDownvote item : userprofile.getGameReviewDownvotes()) {
            item.setUser(userprofile);
            }
        }

        if (userprofile.getSentMessages() != null) {
            for (UserMessage item : userprofile.getSentMessages()) {
            item.setSender(userprofile);
            }
        }

        if (userprofile.getReceivedMessages() != null) {
            for (UserMessage item : userprofile.getReceivedMessages()) {
            item.setReceiver(userprofile);
            }
        }
        if (userprofile.getWallet() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            userprofile.setWallet(
            walletRepository.findById(userprofile.getWallet().getId())
            .orElseThrow(() -> new RuntimeException("wallet not found"))
            );
        
        userprofile.getWallet().setUser(userprofile);
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

        existing.getDigitalPurchases().clear();
        if (userprofileRequest.getDigitalPurchases() != null) {
            for (var item : userprofileRequest.getDigitalPurchases()) {
            item.setUser(existing);
            existing.getDigitalPurchases().add(item);
            }
        }

        existing.getGameSessions().clear();
        if (userprofileRequest.getGameSessions() != null) {
            for (var item : userprofileRequest.getGameSessions()) {
            item.setUser(existing);
            existing.getGameSessions().add(item);
            }
        }

        existing.getGameReviewComments().clear();
        if (userprofileRequest.getGameReviewComments() != null) {
            for (var item : userprofileRequest.getGameReviewComments()) {
            item.setUser(existing);
            existing.getGameReviewComments().add(item);
            }
        }

        existing.getUserPlaylists().clear();
        if (userprofileRequest.getUserPlaylists() != null) {
            for (var item : userprofileRequest.getUserPlaylists()) {
            item.setUser(existing);
            existing.getUserPlaylists().add(item);
            }
        }

        existing.getUserPlaylistItems().clear();
        if (userprofileRequest.getUserPlaylistItems() != null) {
            for (var item : userprofileRequest.getUserPlaylistItems()) {
            item.setAddedBy(existing);
            existing.getUserPlaylistItems().add(item);
            }
        }

        existing.getGivenRatings().clear();
        if (userprofileRequest.getGivenRatings() != null) {
            for (var item : userprofileRequest.getGivenRatings()) {
            item.setUser(existing);
            existing.getGivenRatings().add(item);
            }
        }

        existing.getLikedReviews().clear();
        if (userprofileRequest.getLikedReviews() != null) {
            for (var item : userprofileRequest.getLikedReviews()) {
            item.setUser(existing);
            existing.getLikedReviews().add(item);
            }
        }

        existing.getActivityLogs().clear();
        if (userprofileRequest.getActivityLogs() != null) {
            for (var item : userprofileRequest.getActivityLogs()) {
            item.setUser(existing);
            existing.getActivityLogs().add(item);
            }
        }

        existing.getSettings().clear();
        if (userprofileRequest.getSettings() != null) {
            for (var item : userprofileRequest.getSettings()) {
            item.setUser(existing);
            existing.getSettings().add(item);
            }
        }

        existing.getFollowers().clear();
        if (userprofileRequest.getFollowers() != null) {
            for (var item : userprofileRequest.getFollowers()) {
            item.setFollowed(existing);
            existing.getFollowers().add(item);
            }
        }

        existing.getFollowing().clear();
        if (userprofileRequest.getFollowing() != null) {
            for (var item : userprofileRequest.getFollowing()) {
            item.setFollower(existing);
            existing.getFollowing().add(item);
            }
        }

        existing.getUserAchievements().clear();
        if (userprofileRequest.getUserAchievements() != null) {
            for (var item : userprofileRequest.getUserAchievements()) {
            item.setUser(existing);
            existing.getUserAchievements().add(item);
            }
        }

        existing.getNotifications().clear();
        if (userprofileRequest.getNotifications() != null) {
            for (var item : userprofileRequest.getNotifications()) {
            item.setRecipient(existing);
            existing.getNotifications().add(item);
            }
        }

        existing.getMerchandiseReviews().clear();
        if (userprofileRequest.getMerchandiseReviews() != null) {
            for (var item : userprofileRequest.getMerchandiseReviews()) {
            item.setUser(existing);
            existing.getMerchandiseReviews().add(item);
            }
        }

        existing.getPreferences().clear();
        if (userprofileRequest.getPreferences() != null) {
            for (var item : userprofileRequest.getPreferences()) {
            item.setUser(existing);
            existing.getPreferences().add(item);
            }
        }

        existing.getMerchandiseSales().clear();
        if (userprofileRequest.getMerchandiseSales() != null) {
            for (var item : userprofileRequest.getMerchandiseSales()) {
            item.setUser(existing);
            existing.getMerchandiseSales().add(item);
            }
        }

        existing.getGamePlaySessions().clear();
        if (userprofileRequest.getGamePlaySessions() != null) {
            for (var item : userprofileRequest.getGamePlaySessions()) {
            item.setUser(existing);
            existing.getGamePlaySessions().add(item);
            }
        }

        existing.getGameReviewUpvotes().clear();
        if (userprofileRequest.getGameReviewUpvotes() != null) {
            for (var item : userprofileRequest.getGameReviewUpvotes()) {
            item.setUser(existing);
            existing.getGameReviewUpvotes().add(item);
            }
        }

        existing.getGameReviewDownvotes().clear();
        if (userprofileRequest.getGameReviewDownvotes() != null) {
            for (var item : userprofileRequest.getGameReviewDownvotes()) {
            item.setUser(existing);
            existing.getGameReviewDownvotes().add(item);
            }
        }

        existing.getSentMessages().clear();
        if (userprofileRequest.getSentMessages() != null) {
            for (var item : userprofileRequest.getSentMessages()) {
            item.setSender(existing);
            existing.getSentMessages().add(item);
            }
        }

        existing.getReceivedMessages().clear();
        if (userprofileRequest.getReceivedMessages() != null) {
            for (var item : userprofileRequest.getReceivedMessages()) {
            item.setReceiver(existing);
            existing.getReceivedMessages().add(item);
            }
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

        if (userprofileRequest.getWallet() != null
        && userprofileRequest.getWallet().getId() != null) {

        UserWallet wallet = walletRepository.findById(
        userprofileRequest.getWallet().getId()
        ).orElseThrow(() -> new RuntimeException("UserWallet not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setWallet(wallet);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            wallet.setUser(existing);
        
        }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


        return userprofileRepository.save(existing);
    }
}