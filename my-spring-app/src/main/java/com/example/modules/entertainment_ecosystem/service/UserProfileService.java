package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
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
import com.example.modules.entertainment_ecosystem.repository.SubscriptionRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.repository.AchievementRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.repository.PlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.repository.UserWalletRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.repository.GameSessionRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.repository.GameReviewCommentRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.repository.UserPlaylistItemRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.repository.ReviewRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.repository.ReviewLikeRepository;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.repository.UserActivityLogRepository;
import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.repository.UserSettingRepository;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.repository.UserFollowerRepository;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.repository.UserFollowerRepository;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.repository.UserAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.repository.NotificationRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.repository.UserPreferenceRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSaleRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.repository.GamePlaySessionRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewUpvoteRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.repository.GameReviewDownvoteRepository;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.repository.UserMessageRepository;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.repository.UserMessageRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserProfileService extends BaseService<UserProfile> {

    protected final UserProfileRepository userprofileRepository;
    private final ReviewRepository reviewsRepository;
    private final MovieRepository watchlistMoviesRepository;
    private final ArtistRepository favoriteArtistsRepository;
    private final UserProfileRepository followedUsersRepository;
    private final UserProfileRepository followingUsersRepository;
    private final GenreRepository favoriteGenresRepository;
    private final SubscriptionRepository subscriptionsRepository;
    private final EpisodeRepository watchedEpisodesRepository;
    private final VideoGameRepository playedGamesRepository;
    private final ForumThreadRepository forumThreadsRepository;
    private final ForumPostRepository forumPostsRepository;
    private final AchievementRepository achievementsRepository;
    private final OnlineEventRepository hostedOnlineEventsRepository;
    private final OnlineEventRepository attendedOnlineEventsRepository;
    private final MerchandiseRepository ownedMerchandiseRepository;
    private final PodcastRepository libraryPodcastsRepository;
    private final MusicTrackRepository listenedMusicRepository;
    private final PlaylistRepository playlistsRepository;
    private final UserWalletRepository walletRepository;
    private final DigitalPurchaseRepository digitalPurchasesRepository;
    private final GameSessionRepository gameSessionsRepository;
    private final GameReviewCommentRepository gameReviewCommentsRepository;
    private final UserPlaylistRepository userPlaylistsRepository;
    private final UserPlaylistItemRepository userPlaylistItemsRepository;
    private final ReviewRatingRepository givenRatingsRepository;
    private final ReviewLikeRepository likedReviewsRepository;
    private final UserActivityLogRepository activityLogsRepository;
    private final UserSettingRepository settingsRepository;
    private final UserFollowerRepository followersRepository;
    private final UserFollowerRepository followingRepository;
    private final UserAchievementRepository userAchievementsRepository;
    private final NotificationRepository notificationsRepository;
    private final MerchandiseReviewRepository merchandiseReviewsRepository;
    private final UserPreferenceRepository preferencesRepository;
    private final MerchandiseSaleRepository merchandiseSalesRepository;
    private final GamePlaySessionRepository gamePlaySessionsRepository;
    private final GameReviewUpvoteRepository gameReviewUpvotesRepository;
    private final GameReviewDownvoteRepository gameReviewDownvotesRepository;
    private final UserMessageRepository sentMessagesRepository;
    private final UserMessageRepository receivedMessagesRepository;

    public UserProfileService(UserProfileRepository repository,ReviewRepository reviewsRepository,MovieRepository watchlistMoviesRepository,ArtistRepository favoriteArtistsRepository,UserProfileRepository followedUsersRepository,UserProfileRepository followingUsersRepository,GenreRepository favoriteGenresRepository,SubscriptionRepository subscriptionsRepository,EpisodeRepository watchedEpisodesRepository,VideoGameRepository playedGamesRepository,ForumThreadRepository forumThreadsRepository,ForumPostRepository forumPostsRepository,AchievementRepository achievementsRepository,OnlineEventRepository hostedOnlineEventsRepository,OnlineEventRepository attendedOnlineEventsRepository,MerchandiseRepository ownedMerchandiseRepository,PodcastRepository libraryPodcastsRepository,MusicTrackRepository listenedMusicRepository,PlaylistRepository playlistsRepository,UserWalletRepository walletRepository,DigitalPurchaseRepository digitalPurchasesRepository,GameSessionRepository gameSessionsRepository,GameReviewCommentRepository gameReviewCommentsRepository,UserPlaylistRepository userPlaylistsRepository,UserPlaylistItemRepository userPlaylistItemsRepository,ReviewRatingRepository givenRatingsRepository,ReviewLikeRepository likedReviewsRepository,UserActivityLogRepository activityLogsRepository,UserSettingRepository settingsRepository,UserFollowerRepository followersRepository,UserFollowerRepository followingRepository,UserAchievementRepository userAchievementsRepository,NotificationRepository notificationsRepository,MerchandiseReviewRepository merchandiseReviewsRepository,UserPreferenceRepository preferencesRepository,MerchandiseSaleRepository merchandiseSalesRepository,GamePlaySessionRepository gamePlaySessionsRepository,GameReviewUpvoteRepository gameReviewUpvotesRepository,GameReviewDownvoteRepository gameReviewDownvotesRepository,UserMessageRepository sentMessagesRepository,UserMessageRepository receivedMessagesRepository)
    {
        super(repository);
        this.userprofileRepository = repository;
        this.reviewsRepository = reviewsRepository;
        this.watchlistMoviesRepository = watchlistMoviesRepository;
        this.favoriteArtistsRepository = favoriteArtistsRepository;
        this.followedUsersRepository = followedUsersRepository;
        this.followingUsersRepository = followingUsersRepository;
        this.favoriteGenresRepository = favoriteGenresRepository;
        this.subscriptionsRepository = subscriptionsRepository;
        this.watchedEpisodesRepository = watchedEpisodesRepository;
        this.playedGamesRepository = playedGamesRepository;
        this.forumThreadsRepository = forumThreadsRepository;
        this.forumPostsRepository = forumPostsRepository;
        this.achievementsRepository = achievementsRepository;
        this.hostedOnlineEventsRepository = hostedOnlineEventsRepository;
        this.attendedOnlineEventsRepository = attendedOnlineEventsRepository;
        this.ownedMerchandiseRepository = ownedMerchandiseRepository;
        this.libraryPodcastsRepository = libraryPodcastsRepository;
        this.listenedMusicRepository = listenedMusicRepository;
        this.playlistsRepository = playlistsRepository;
        this.walletRepository = walletRepository;
        this.digitalPurchasesRepository = digitalPurchasesRepository;
        this.gameSessionsRepository = gameSessionsRepository;
        this.gameReviewCommentsRepository = gameReviewCommentsRepository;
        this.userPlaylistsRepository = userPlaylistsRepository;
        this.userPlaylistItemsRepository = userPlaylistItemsRepository;
        this.givenRatingsRepository = givenRatingsRepository;
        this.likedReviewsRepository = likedReviewsRepository;
        this.activityLogsRepository = activityLogsRepository;
        this.settingsRepository = settingsRepository;
        this.followersRepository = followersRepository;
        this.followingRepository = followingRepository;
        this.userAchievementsRepository = userAchievementsRepository;
        this.notificationsRepository = notificationsRepository;
        this.merchandiseReviewsRepository = merchandiseReviewsRepository;
        this.preferencesRepository = preferencesRepository;
        this.merchandiseSalesRepository = merchandiseSalesRepository;
        this.gamePlaySessionsRepository = gamePlaySessionsRepository;
        this.gameReviewUpvotesRepository = gameReviewUpvotesRepository;
        this.gameReviewDownvotesRepository = gameReviewDownvotesRepository;
        this.sentMessagesRepository = sentMessagesRepository;
        this.receivedMessagesRepository = receivedMessagesRepository;
    }

    @Override
    public UserProfile save(UserProfile userprofile) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getReviews() != null) {
            List<Review> managedReviews = new ArrayList<>();
            for (Review item : userprofile.getReviews()) {
            if (item.getId() != null) {
            Review existingItem = reviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Review not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedReviews.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedReviews.add(item);
            }
            }
            userprofile.setReviews(managedReviews);
            }
        
    

    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : userprofile.getSubscriptions()) {
            if (item.getId() != null) {
            Subscription existingItem = subscriptionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Subscription not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedSubscriptions.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedSubscriptions.add(item);
            }
            }
            userprofile.setSubscriptions(managedSubscriptions);
            }
        
    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getForumThreads() != null) {
            List<ForumThread> managedForumThreads = new ArrayList<>();
            for (ForumThread item : userprofile.getForumThreads()) {
            if (item.getId() != null) {
            ForumThread existingItem = forumThreadsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ForumThread not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAuthor(userprofile);
            managedForumThreads.add(existingItem);
            } else {
            item.setAuthor(userprofile);
            managedForumThreads.add(item);
            }
            }
            userprofile.setForumThreads(managedForumThreads);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getForumPosts() != null) {
            List<ForumPost> managedForumPosts = new ArrayList<>();
            for (ForumPost item : userprofile.getForumPosts()) {
            if (item.getId() != null) {
            ForumPost existingItem = forumPostsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ForumPost not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAuthor(userprofile);
            managedForumPosts.add(existingItem);
            } else {
            item.setAuthor(userprofile);
            managedForumPosts.add(item);
            }
            }
            userprofile.setForumPosts(managedForumPosts);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getAchievements() != null) {
            List<Achievement> managedAchievements = new ArrayList<>();
            for (Achievement item : userprofile.getAchievements()) {
            if (item.getId() != null) {
            Achievement existingItem = achievementsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Achievement not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedAchievements.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedAchievements.add(item);
            }
            }
            userprofile.setAchievements(managedAchievements);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getHostedOnlineEvents() != null) {
            List<OnlineEvent> managedHostedOnlineEvents = new ArrayList<>();
            for (OnlineEvent item : userprofile.getHostedOnlineEvents()) {
            if (item.getId() != null) {
            OnlineEvent existingItem = hostedOnlineEventsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setHost(userprofile);
            managedHostedOnlineEvents.add(existingItem);
            } else {
            item.setHost(userprofile);
            managedHostedOnlineEvents.add(item);
            }
            }
            userprofile.setHostedOnlineEvents(managedHostedOnlineEvents);
            }
        
    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getPlaylists() != null) {
            List<Playlist> managedPlaylists = new ArrayList<>();
            for (Playlist item : userprofile.getPlaylists()) {
            if (item.getId() != null) {
            Playlist existingItem = playlistsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Playlist not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setOwner(userprofile);
            managedPlaylists.add(existingItem);
            } else {
            item.setOwner(userprofile);
            managedPlaylists.add(item);
            }
            }
            userprofile.setPlaylists(managedPlaylists);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getDigitalPurchases() != null) {
            List<DigitalPurchase> managedDigitalPurchases = new ArrayList<>();
            for (DigitalPurchase item : userprofile.getDigitalPurchases()) {
            if (item.getId() != null) {
            DigitalPurchase existingItem = digitalPurchasesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedDigitalPurchases.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedDigitalPurchases.add(item);
            }
            }
            userprofile.setDigitalPurchases(managedDigitalPurchases);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGameSessions() != null) {
            List<GameSession> managedGameSessions = new ArrayList<>();
            for (GameSession item : userprofile.getGameSessions()) {
            if (item.getId() != null) {
            GameSession existingItem = gameSessionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameSession not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGameSessions.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGameSessions.add(item);
            }
            }
            userprofile.setGameSessions(managedGameSessions);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGameReviewComments() != null) {
            List<GameReviewComment> managedGameReviewComments = new ArrayList<>();
            for (GameReviewComment item : userprofile.getGameReviewComments()) {
            if (item.getId() != null) {
            GameReviewComment existingItem = gameReviewCommentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGameReviewComments.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGameReviewComments.add(item);
            }
            }
            userprofile.setGameReviewComments(managedGameReviewComments);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getUserPlaylists() != null) {
            List<UserPlaylist> managedUserPlaylists = new ArrayList<>();
            for (UserPlaylist item : userprofile.getUserPlaylists()) {
            if (item.getId() != null) {
            UserPlaylist existingItem = userPlaylistsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedUserPlaylists.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedUserPlaylists.add(item);
            }
            }
            userprofile.setUserPlaylists(managedUserPlaylists);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getUserPlaylistItems() != null) {
            List<UserPlaylistItem> managedUserPlaylistItems = new ArrayList<>();
            for (UserPlaylistItem item : userprofile.getUserPlaylistItems()) {
            if (item.getId() != null) {
            UserPlaylistItem existingItem = userPlaylistItemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAddedBy(userprofile);
            managedUserPlaylistItems.add(existingItem);
            } else {
            item.setAddedBy(userprofile);
            managedUserPlaylistItems.add(item);
            }
            }
            userprofile.setUserPlaylistItems(managedUserPlaylistItems);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGivenRatings() != null) {
            List<ReviewRating> managedGivenRatings = new ArrayList<>();
            for (ReviewRating item : userprofile.getGivenRatings()) {
            if (item.getId() != null) {
            ReviewRating existingItem = givenRatingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGivenRatings.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGivenRatings.add(item);
            }
            }
            userprofile.setGivenRatings(managedGivenRatings);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getLikedReviews() != null) {
            List<ReviewLike> managedLikedReviews = new ArrayList<>();
            for (ReviewLike item : userprofile.getLikedReviews()) {
            if (item.getId() != null) {
            ReviewLike existingItem = likedReviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedLikedReviews.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedLikedReviews.add(item);
            }
            }
            userprofile.setLikedReviews(managedLikedReviews);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getActivityLogs() != null) {
            List<UserActivityLog> managedActivityLogs = new ArrayList<>();
            for (UserActivityLog item : userprofile.getActivityLogs()) {
            if (item.getId() != null) {
            UserActivityLog existingItem = activityLogsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedActivityLogs.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedActivityLogs.add(item);
            }
            }
            userprofile.setActivityLogs(managedActivityLogs);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getSettings() != null) {
            List<UserSetting> managedSettings = new ArrayList<>();
            for (UserSetting item : userprofile.getSettings()) {
            if (item.getId() != null) {
            UserSetting existingItem = settingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserSetting not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedSettings.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedSettings.add(item);
            }
            }
            userprofile.setSettings(managedSettings);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getFollowers() != null) {
            List<UserFollower> managedFollowers = new ArrayList<>();
            for (UserFollower item : userprofile.getFollowers()) {
            if (item.getId() != null) {
            UserFollower existingItem = followersRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserFollower not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setFollowed(userprofile);
            managedFollowers.add(existingItem);
            } else {
            item.setFollowed(userprofile);
            managedFollowers.add(item);
            }
            }
            userprofile.setFollowers(managedFollowers);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getFollowing() != null) {
            List<UserFollower> managedFollowing = new ArrayList<>();
            for (UserFollower item : userprofile.getFollowing()) {
            if (item.getId() != null) {
            UserFollower existingItem = followingRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserFollower not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setFollower(userprofile);
            managedFollowing.add(existingItem);
            } else {
            item.setFollower(userprofile);
            managedFollowing.add(item);
            }
            }
            userprofile.setFollowing(managedFollowing);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getUserAchievements() != null) {
            List<UserAchievement> managedUserAchievements = new ArrayList<>();
            for (UserAchievement item : userprofile.getUserAchievements()) {
            if (item.getId() != null) {
            UserAchievement existingItem = userAchievementsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedUserAchievements.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedUserAchievements.add(item);
            }
            }
            userprofile.setUserAchievements(managedUserAchievements);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getNotifications() != null) {
            List<Notification> managedNotifications = new ArrayList<>();
            for (Notification item : userprofile.getNotifications()) {
            if (item.getId() != null) {
            Notification existingItem = notificationsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Notification not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setRecipient(userprofile);
            managedNotifications.add(existingItem);
            } else {
            item.setRecipient(userprofile);
            managedNotifications.add(item);
            }
            }
            userprofile.setNotifications(managedNotifications);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getMerchandiseReviews() != null) {
            List<MerchandiseReview> managedMerchandiseReviews = new ArrayList<>();
            for (MerchandiseReview item : userprofile.getMerchandiseReviews()) {
            if (item.getId() != null) {
            MerchandiseReview existingItem = merchandiseReviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedMerchandiseReviews.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedMerchandiseReviews.add(item);
            }
            }
            userprofile.setMerchandiseReviews(managedMerchandiseReviews);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getPreferences() != null) {
            List<UserPreference> managedPreferences = new ArrayList<>();
            for (UserPreference item : userprofile.getPreferences()) {
            if (item.getId() != null) {
            UserPreference existingItem = preferencesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserPreference not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedPreferences.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedPreferences.add(item);
            }
            }
            userprofile.setPreferences(managedPreferences);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getMerchandiseSales() != null) {
            List<MerchandiseSale> managedMerchandiseSales = new ArrayList<>();
            for (MerchandiseSale item : userprofile.getMerchandiseSales()) {
            if (item.getId() != null) {
            MerchandiseSale existingItem = merchandiseSalesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedMerchandiseSales.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedMerchandiseSales.add(item);
            }
            }
            userprofile.setMerchandiseSales(managedMerchandiseSales);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGamePlaySessions() != null) {
            List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();
            for (GamePlaySession item : userprofile.getGamePlaySessions()) {
            if (item.getId() != null) {
            GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGamePlaySessions.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGamePlaySessions.add(item);
            }
            }
            userprofile.setGamePlaySessions(managedGamePlaySessions);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGameReviewUpvotes() != null) {
            List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();
            for (GameReviewUpvote item : userprofile.getGameReviewUpvotes()) {
            if (item.getId() != null) {
            GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGameReviewUpvotes.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGameReviewUpvotes.add(item);
            }
            }
            userprofile.setGameReviewUpvotes(managedGameReviewUpvotes);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getGameReviewDownvotes() != null) {
            List<GameReviewDownvote> managedGameReviewDownvotes = new ArrayList<>();
            for (GameReviewDownvote item : userprofile.getGameReviewDownvotes()) {
            if (item.getId() != null) {
            GameReviewDownvote existingItem = gameReviewDownvotesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setUser(userprofile);
            managedGameReviewDownvotes.add(existingItem);
            } else {
            item.setUser(userprofile);
            managedGameReviewDownvotes.add(item);
            }
            }
            userprofile.setGameReviewDownvotes(managedGameReviewDownvotes);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getSentMessages() != null) {
            List<UserMessage> managedSentMessages = new ArrayList<>();
            for (UserMessage item : userprofile.getSentMessages()) {
            if (item.getId() != null) {
            UserMessage existingItem = sentMessagesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserMessage not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSender(userprofile);
            managedSentMessages.add(existingItem);
            } else {
            item.setSender(userprofile);
            managedSentMessages.add(item);
            }
            }
            userprofile.setSentMessages(managedSentMessages);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (userprofile.getReceivedMessages() != null) {
            List<UserMessage> managedReceivedMessages = new ArrayList<>();
            for (UserMessage item : userprofile.getReceivedMessages()) {
            if (item.getId() != null) {
            UserMessage existingItem = receivedMessagesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserMessage not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setReceiver(userprofile);
            managedReceivedMessages.add(existingItem);
            } else {
            item.setReceiver(userprofile);
            managedReceivedMessages.add(item);
            }
            }
            userprofile.setReceivedMessages(managedReceivedMessages);
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
        List<Review> managedReviews = new ArrayList<>();

        for (var item : userprofileRequest.getReviews()) {
        if (item.getId() != null) {
        Review existingItem = reviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Review not found"));
        existingItem.setUser(existing);
        managedReviews.add(existingItem);
        } else {
        item.setUser(existing);
        managedReviews.add(item);
        }
        }
        existing.setReviews(managedReviews);
        }
        existing.getSubscriptions().clear();

        if (userprofileRequest.getSubscriptions() != null) {
        List<Subscription> managedSubscriptions = new ArrayList<>();

        for (var item : userprofileRequest.getSubscriptions()) {
        if (item.getId() != null) {
        Subscription existingItem = subscriptionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Subscription not found"));
        existingItem.setUser(existing);
        managedSubscriptions.add(existingItem);
        } else {
        item.setUser(existing);
        managedSubscriptions.add(item);
        }
        }
        existing.setSubscriptions(managedSubscriptions);
        }
        existing.getForumThreads().clear();

        if (userprofileRequest.getForumThreads() != null) {
        List<ForumThread> managedForumThreads = new ArrayList<>();

        for (var item : userprofileRequest.getForumThreads()) {
        if (item.getId() != null) {
        ForumThread existingItem = forumThreadsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumThread not found"));
        existingItem.setAuthor(existing);
        managedForumThreads.add(existingItem);
        } else {
        item.setAuthor(existing);
        managedForumThreads.add(item);
        }
        }
        existing.setForumThreads(managedForumThreads);
        }
        existing.getForumPosts().clear();

        if (userprofileRequest.getForumPosts() != null) {
        List<ForumPost> managedForumPosts = new ArrayList<>();

        for (var item : userprofileRequest.getForumPosts()) {
        if (item.getId() != null) {
        ForumPost existingItem = forumPostsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
        existingItem.setAuthor(existing);
        managedForumPosts.add(existingItem);
        } else {
        item.setAuthor(existing);
        managedForumPosts.add(item);
        }
        }
        existing.setForumPosts(managedForumPosts);
        }
        existing.getAchievements().clear();

        if (userprofileRequest.getAchievements() != null) {
        List<Achievement> managedAchievements = new ArrayList<>();

        for (var item : userprofileRequest.getAchievements()) {
        if (item.getId() != null) {
        Achievement existingItem = achievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Achievement not found"));
        existingItem.setUser(existing);
        managedAchievements.add(existingItem);
        } else {
        item.setUser(existing);
        managedAchievements.add(item);
        }
        }
        existing.setAchievements(managedAchievements);
        }
        existing.getHostedOnlineEvents().clear();

        if (userprofileRequest.getHostedOnlineEvents() != null) {
        List<OnlineEvent> managedHostedOnlineEvents = new ArrayList<>();

        for (var item : userprofileRequest.getHostedOnlineEvents()) {
        if (item.getId() != null) {
        OnlineEvent existingItem = hostedOnlineEventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
        existingItem.setHost(existing);
        managedHostedOnlineEvents.add(existingItem);
        } else {
        item.setHost(existing);
        managedHostedOnlineEvents.add(item);
        }
        }
        existing.setHostedOnlineEvents(managedHostedOnlineEvents);
        }
        existing.getPlaylists().clear();

        if (userprofileRequest.getPlaylists() != null) {
        List<Playlist> managedPlaylists = new ArrayList<>();

        for (var item : userprofileRequest.getPlaylists()) {
        if (item.getId() != null) {
        Playlist existingItem = playlistsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Playlist not found"));
        existingItem.setOwner(existing);
        managedPlaylists.add(existingItem);
        } else {
        item.setOwner(existing);
        managedPlaylists.add(item);
        }
        }
        existing.setPlaylists(managedPlaylists);
        }
        existing.getDigitalPurchases().clear();

        if (userprofileRequest.getDigitalPurchases() != null) {
        List<DigitalPurchase> managedDigitalPurchases = new ArrayList<>();

        for (var item : userprofileRequest.getDigitalPurchases()) {
        if (item.getId() != null) {
        DigitalPurchase existingItem = digitalPurchasesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
        existingItem.setUser(existing);
        managedDigitalPurchases.add(existingItem);
        } else {
        item.setUser(existing);
        managedDigitalPurchases.add(item);
        }
        }
        existing.setDigitalPurchases(managedDigitalPurchases);
        }
        existing.getGameSessions().clear();

        if (userprofileRequest.getGameSessions() != null) {
        List<GameSession> managedGameSessions = new ArrayList<>();

        for (var item : userprofileRequest.getGameSessions()) {
        if (item.getId() != null) {
        GameSession existingItem = gameSessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameSession not found"));
        existingItem.setUser(existing);
        managedGameSessions.add(existingItem);
        } else {
        item.setUser(existing);
        managedGameSessions.add(item);
        }
        }
        existing.setGameSessions(managedGameSessions);
        }
        existing.getGameReviewComments().clear();

        if (userprofileRequest.getGameReviewComments() != null) {
        List<GameReviewComment> managedGameReviewComments = new ArrayList<>();

        for (var item : userprofileRequest.getGameReviewComments()) {
        if (item.getId() != null) {
        GameReviewComment existingItem = gameReviewCommentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
        existingItem.setUser(existing);
        managedGameReviewComments.add(existingItem);
        } else {
        item.setUser(existing);
        managedGameReviewComments.add(item);
        }
        }
        existing.setGameReviewComments(managedGameReviewComments);
        }
        existing.getUserPlaylists().clear();

        if (userprofileRequest.getUserPlaylists() != null) {
        List<UserPlaylist> managedUserPlaylists = new ArrayList<>();

        for (var item : userprofileRequest.getUserPlaylists()) {
        if (item.getId() != null) {
        UserPlaylist existingItem = userPlaylistsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
        existingItem.setUser(existing);
        managedUserPlaylists.add(existingItem);
        } else {
        item.setUser(existing);
        managedUserPlaylists.add(item);
        }
        }
        existing.setUserPlaylists(managedUserPlaylists);
        }
        existing.getUserPlaylistItems().clear();

        if (userprofileRequest.getUserPlaylistItems() != null) {
        List<UserPlaylistItem> managedUserPlaylistItems = new ArrayList<>();

        for (var item : userprofileRequest.getUserPlaylistItems()) {
        if (item.getId() != null) {
        UserPlaylistItem existingItem = userPlaylistItemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
        existingItem.setAddedBy(existing);
        managedUserPlaylistItems.add(existingItem);
        } else {
        item.setAddedBy(existing);
        managedUserPlaylistItems.add(item);
        }
        }
        existing.setUserPlaylistItems(managedUserPlaylistItems);
        }
        existing.getGivenRatings().clear();

        if (userprofileRequest.getGivenRatings() != null) {
        List<ReviewRating> managedGivenRatings = new ArrayList<>();

        for (var item : userprofileRequest.getGivenRatings()) {
        if (item.getId() != null) {
        ReviewRating existingItem = givenRatingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
        existingItem.setUser(existing);
        managedGivenRatings.add(existingItem);
        } else {
        item.setUser(existing);
        managedGivenRatings.add(item);
        }
        }
        existing.setGivenRatings(managedGivenRatings);
        }
        existing.getLikedReviews().clear();

        if (userprofileRequest.getLikedReviews() != null) {
        List<ReviewLike> managedLikedReviews = new ArrayList<>();

        for (var item : userprofileRequest.getLikedReviews()) {
        if (item.getId() != null) {
        ReviewLike existingItem = likedReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
        existingItem.setUser(existing);
        managedLikedReviews.add(existingItem);
        } else {
        item.setUser(existing);
        managedLikedReviews.add(item);
        }
        }
        existing.setLikedReviews(managedLikedReviews);
        }
        existing.getActivityLogs().clear();

        if (userprofileRequest.getActivityLogs() != null) {
        List<UserActivityLog> managedActivityLogs = new ArrayList<>();

        for (var item : userprofileRequest.getActivityLogs()) {
        if (item.getId() != null) {
        UserActivityLog existingItem = activityLogsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));
        existingItem.setUser(existing);
        managedActivityLogs.add(existingItem);
        } else {
        item.setUser(existing);
        managedActivityLogs.add(item);
        }
        }
        existing.setActivityLogs(managedActivityLogs);
        }
        existing.getSettings().clear();

        if (userprofileRequest.getSettings() != null) {
        List<UserSetting> managedSettings = new ArrayList<>();

        for (var item : userprofileRequest.getSettings()) {
        if (item.getId() != null) {
        UserSetting existingItem = settingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserSetting not found"));
        existingItem.setUser(existing);
        managedSettings.add(existingItem);
        } else {
        item.setUser(existing);
        managedSettings.add(item);
        }
        }
        existing.setSettings(managedSettings);
        }
        existing.getFollowers().clear();

        if (userprofileRequest.getFollowers() != null) {
        List<UserFollower> managedFollowers = new ArrayList<>();

        for (var item : userprofileRequest.getFollowers()) {
        if (item.getId() != null) {
        UserFollower existingItem = followersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
        existingItem.setFollowed(existing);
        managedFollowers.add(existingItem);
        } else {
        item.setFollowed(existing);
        managedFollowers.add(item);
        }
        }
        existing.setFollowers(managedFollowers);
        }
        existing.getFollowing().clear();

        if (userprofileRequest.getFollowing() != null) {
        List<UserFollower> managedFollowing = new ArrayList<>();

        for (var item : userprofileRequest.getFollowing()) {
        if (item.getId() != null) {
        UserFollower existingItem = followingRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
        existingItem.setFollower(existing);
        managedFollowing.add(existingItem);
        } else {
        item.setFollower(existing);
        managedFollowing.add(item);
        }
        }
        existing.setFollowing(managedFollowing);
        }
        existing.getUserAchievements().clear();

        if (userprofileRequest.getUserAchievements() != null) {
        List<UserAchievement> managedUserAchievements = new ArrayList<>();

        for (var item : userprofileRequest.getUserAchievements()) {
        if (item.getId() != null) {
        UserAchievement existingItem = userAchievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
        existingItem.setUser(existing);
        managedUserAchievements.add(existingItem);
        } else {
        item.setUser(existing);
        managedUserAchievements.add(item);
        }
        }
        existing.setUserAchievements(managedUserAchievements);
        }
        existing.getNotifications().clear();

        if (userprofileRequest.getNotifications() != null) {
        List<Notification> managedNotifications = new ArrayList<>();

        for (var item : userprofileRequest.getNotifications()) {
        if (item.getId() != null) {
        Notification existingItem = notificationsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Notification not found"));
        existingItem.setRecipient(existing);
        managedNotifications.add(existingItem);
        } else {
        item.setRecipient(existing);
        managedNotifications.add(item);
        }
        }
        existing.setNotifications(managedNotifications);
        }
        existing.getMerchandiseReviews().clear();

        if (userprofileRequest.getMerchandiseReviews() != null) {
        List<MerchandiseReview> managedMerchandiseReviews = new ArrayList<>();

        for (var item : userprofileRequest.getMerchandiseReviews()) {
        if (item.getId() != null) {
        MerchandiseReview existingItem = merchandiseReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
        existingItem.setUser(existing);
        managedMerchandiseReviews.add(existingItem);
        } else {
        item.setUser(existing);
        managedMerchandiseReviews.add(item);
        }
        }
        existing.setMerchandiseReviews(managedMerchandiseReviews);
        }
        existing.getPreferences().clear();

        if (userprofileRequest.getPreferences() != null) {
        List<UserPreference> managedPreferences = new ArrayList<>();

        for (var item : userprofileRequest.getPreferences()) {
        if (item.getId() != null) {
        UserPreference existingItem = preferencesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPreference not found"));
        existingItem.setUser(existing);
        managedPreferences.add(existingItem);
        } else {
        item.setUser(existing);
        managedPreferences.add(item);
        }
        }
        existing.setPreferences(managedPreferences);
        }
        existing.getMerchandiseSales().clear();

        if (userprofileRequest.getMerchandiseSales() != null) {
        List<MerchandiseSale> managedMerchandiseSales = new ArrayList<>();

        for (var item : userprofileRequest.getMerchandiseSales()) {
        if (item.getId() != null) {
        MerchandiseSale existingItem = merchandiseSalesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
        existingItem.setUser(existing);
        managedMerchandiseSales.add(existingItem);
        } else {
        item.setUser(existing);
        managedMerchandiseSales.add(item);
        }
        }
        existing.setMerchandiseSales(managedMerchandiseSales);
        }
        existing.getGamePlaySessions().clear();

        if (userprofileRequest.getGamePlaySessions() != null) {
        List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();

        for (var item : userprofileRequest.getGamePlaySessions()) {
        if (item.getId() != null) {
        GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
        existingItem.setUser(existing);
        managedGamePlaySessions.add(existingItem);
        } else {
        item.setUser(existing);
        managedGamePlaySessions.add(item);
        }
        }
        existing.setGamePlaySessions(managedGamePlaySessions);
        }
        existing.getGameReviewUpvotes().clear();

        if (userprofileRequest.getGameReviewUpvotes() != null) {
        List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();

        for (var item : userprofileRequest.getGameReviewUpvotes()) {
        if (item.getId() != null) {
        GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
        existingItem.setUser(existing);
        managedGameReviewUpvotes.add(existingItem);
        } else {
        item.setUser(existing);
        managedGameReviewUpvotes.add(item);
        }
        }
        existing.setGameReviewUpvotes(managedGameReviewUpvotes);
        }
        existing.getGameReviewDownvotes().clear();

        if (userprofileRequest.getGameReviewDownvotes() != null) {
        List<GameReviewDownvote> managedGameReviewDownvotes = new ArrayList<>();

        for (var item : userprofileRequest.getGameReviewDownvotes()) {
        if (item.getId() != null) {
        GameReviewDownvote existingItem = gameReviewDownvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
        existingItem.setUser(existing);
        managedGameReviewDownvotes.add(existingItem);
        } else {
        item.setUser(existing);
        managedGameReviewDownvotes.add(item);
        }
        }
        existing.setGameReviewDownvotes(managedGameReviewDownvotes);
        }
        existing.getSentMessages().clear();

        if (userprofileRequest.getSentMessages() != null) {
        List<UserMessage> managedSentMessages = new ArrayList<>();

        for (var item : userprofileRequest.getSentMessages()) {
        if (item.getId() != null) {
        UserMessage existingItem = sentMessagesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
        existingItem.setSender(existing);
        managedSentMessages.add(existingItem);
        } else {
        item.setSender(existing);
        managedSentMessages.add(item);
        }
        }
        existing.setSentMessages(managedSentMessages);
        }
        existing.getReceivedMessages().clear();

        if (userprofileRequest.getReceivedMessages() != null) {
        List<UserMessage> managedReceivedMessages = new ArrayList<>();

        for (var item : userprofileRequest.getReceivedMessages()) {
        if (item.getId() != null) {
        UserMessage existingItem = receivedMessagesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
        existingItem.setReceiver(existing);
        managedReceivedMessages.add(existingItem);
        } else {
        item.setReceiver(existing);
        managedReceivedMessages.add(item);
        }
        }
        existing.setReceivedMessages(managedReceivedMessages);
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