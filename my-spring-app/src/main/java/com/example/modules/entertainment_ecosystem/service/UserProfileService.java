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
import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.repository.UserRoleRepository;
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
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.repository.FeatureFlagRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserRoleRepository UserRoleRepository;
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
    private final FeatureFlagRepository enabledFeatureFlagsRepository;

    public UserProfileService(UserProfileRepository repository,ReviewRepository reviewsRepository,MovieRepository watchlistMoviesRepository,ArtistRepository favoriteArtistsRepository,UserProfileRepository followedUsersRepository,UserProfileRepository followingUsersRepository,GenreRepository favoriteGenresRepository,SubscriptionRepository subscriptionsRepository,EpisodeRepository watchedEpisodesRepository,VideoGameRepository playedGamesRepository,ForumThreadRepository forumThreadsRepository,ForumPostRepository forumPostsRepository,AchievementRepository achievementsRepository,OnlineEventRepository hostedOnlineEventsRepository,OnlineEventRepository attendedOnlineEventsRepository,MerchandiseRepository ownedMerchandiseRepository,PodcastRepository libraryPodcastsRepository,MusicTrackRepository listenedMusicRepository,PlaylistRepository playlistsRepository,UserWalletRepository walletRepository,DigitalPurchaseRepository digitalPurchasesRepository,GameSessionRepository gameSessionsRepository,GameReviewCommentRepository gameReviewCommentsRepository,UserPlaylistRepository userPlaylistsRepository,UserPlaylistItemRepository userPlaylistItemsRepository,ReviewRatingRepository givenRatingsRepository,ReviewLikeRepository likedReviewsRepository,UserActivityLogRepository activityLogsRepository,UserSettingRepository settingsRepository,UserRoleRepository UserRoleRepository,UserFollowerRepository followersRepository,UserFollowerRepository followingRepository,UserAchievementRepository userAchievementsRepository,NotificationRepository notificationsRepository,MerchandiseReviewRepository merchandiseReviewsRepository,UserPreferenceRepository preferencesRepository,MerchandiseSaleRepository merchandiseSalesRepository,GamePlaySessionRepository gamePlaySessionsRepository,GameReviewUpvoteRepository gameReviewUpvotesRepository,GameReviewDownvoteRepository gameReviewDownvotesRepository,UserMessageRepository sentMessagesRepository,UserMessageRepository receivedMessagesRepository,FeatureFlagRepository enabledFeatureFlagsRepository)
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
        this.UserRoleRepository = UserRoleRepository;
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
        this.enabledFeatureFlagsRepository = enabledFeatureFlagsRepository;
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

        if (userprofileRequest.getUserRole() != null) {
            existing.getUserRole().clear();
            List<UserRole> UserRoleList = userprofileRequest.getUserRole().stream()
                .map(item -> UserRoleRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserRole not found")))
                .collect(Collectors.toList());
        existing.getUserRole().addAll(UserRoleList);
        }

        if (userprofileRequest.getEnabledFeatureFlags() != null) {
            existing.getEnabledFeatureFlags().clear();
            List<FeatureFlag> enabledFeatureFlagsList = userprofileRequest.getEnabledFeatureFlags().stream()
                .map(item -> enabledFeatureFlagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("FeatureFlag not found")))
                .collect(Collectors.toList());
        existing.getEnabledFeatureFlags().addAll(enabledFeatureFlagsList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getReviews().clear();

        if (userprofileRequest.getReviews() != null) {
        for (var item : userprofileRequest.getReviews()) {
        Review existingItem;
        if (item.getId() != null) {
        existingItem = reviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Review not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getReviews().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSubscriptions().clear();

        if (userprofileRequest.getSubscriptions() != null) {
        for (var item : userprofileRequest.getSubscriptions()) {
        Subscription existingItem;
        if (item.getId() != null) {
        existingItem = subscriptionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Subscription not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getSubscriptions().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getForumThreads().clear();

        if (userprofileRequest.getForumThreads() != null) {
        for (var item : userprofileRequest.getForumThreads()) {
        ForumThread existingItem;
        if (item.getId() != null) {
        existingItem = forumThreadsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumThread not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAuthor(existing);

        // Ajouter directement dans la collection existante
        existing.getForumThreads().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getForumPosts().clear();

        if (userprofileRequest.getForumPosts() != null) {
        for (var item : userprofileRequest.getForumPosts()) {
        ForumPost existingItem;
        if (item.getId() != null) {
        existingItem = forumPostsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAuthor(existing);

        // Ajouter directement dans la collection existante
        existing.getForumPosts().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getAchievements().clear();

        if (userprofileRequest.getAchievements() != null) {
        for (var item : userprofileRequest.getAchievements()) {
        Achievement existingItem;
        if (item.getId() != null) {
        existingItem = achievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Achievement not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getAchievements().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getHostedOnlineEvents().clear();

        if (userprofileRequest.getHostedOnlineEvents() != null) {
        for (var item : userprofileRequest.getHostedOnlineEvents()) {
        OnlineEvent existingItem;
        if (item.getId() != null) {
        existingItem = hostedOnlineEventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setHost(existing);

        // Ajouter directement dans la collection existante
        existing.getHostedOnlineEvents().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getPlaylists().clear();

        if (userprofileRequest.getPlaylists() != null) {
        for (var item : userprofileRequest.getPlaylists()) {
        Playlist existingItem;
        if (item.getId() != null) {
        existingItem = playlistsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Playlist not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setOwner(existing);

        // Ajouter directement dans la collection existante
        existing.getPlaylists().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getDigitalPurchases().clear();

        if (userprofileRequest.getDigitalPurchases() != null) {
        for (var item : userprofileRequest.getDigitalPurchases()) {
        DigitalPurchase existingItem;
        if (item.getId() != null) {
        existingItem = digitalPurchasesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getDigitalPurchases().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGameSessions().clear();

        if (userprofileRequest.getGameSessions() != null) {
        for (var item : userprofileRequest.getGameSessions()) {
        GameSession existingItem;
        if (item.getId() != null) {
        existingItem = gameSessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameSession not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGameSessions().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGameReviewComments().clear();

        if (userprofileRequest.getGameReviewComments() != null) {
        for (var item : userprofileRequest.getGameReviewComments()) {
        GameReviewComment existingItem;
        if (item.getId() != null) {
        existingItem = gameReviewCommentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGameReviewComments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getUserPlaylists().clear();

        if (userprofileRequest.getUserPlaylists() != null) {
        for (var item : userprofileRequest.getUserPlaylists()) {
        UserPlaylist existingItem;
        if (item.getId() != null) {
        existingItem = userPlaylistsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getUserPlaylists().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getUserPlaylistItems().clear();

        if (userprofileRequest.getUserPlaylistItems() != null) {
        for (var item : userprofileRequest.getUserPlaylistItems()) {
        UserPlaylistItem existingItem;
        if (item.getId() != null) {
        existingItem = userPlaylistItemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAddedBy(existing);

        // Ajouter directement dans la collection existante
        existing.getUserPlaylistItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGivenRatings().clear();

        if (userprofileRequest.getGivenRatings() != null) {
        for (var item : userprofileRequest.getGivenRatings()) {
        ReviewRating existingItem;
        if (item.getId() != null) {
        existingItem = givenRatingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGivenRatings().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getLikedReviews().clear();

        if (userprofileRequest.getLikedReviews() != null) {
        for (var item : userprofileRequest.getLikedReviews()) {
        ReviewLike existingItem;
        if (item.getId() != null) {
        existingItem = likedReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getLikedReviews().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getActivityLogs().clear();

        if (userprofileRequest.getActivityLogs() != null) {
        for (var item : userprofileRequest.getActivityLogs()) {
        UserActivityLog existingItem;
        if (item.getId() != null) {
        existingItem = activityLogsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getActivityLogs().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSettings().clear();

        if (userprofileRequest.getSettings() != null) {
        for (var item : userprofileRequest.getSettings()) {
        UserSetting existingItem;
        if (item.getId() != null) {
        existingItem = settingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserSetting not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getSettings().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getFollowers().clear();

        if (userprofileRequest.getFollowers() != null) {
        for (var item : userprofileRequest.getFollowers()) {
        UserFollower existingItem;
        if (item.getId() != null) {
        existingItem = followersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setFollowed(existing);

        // Ajouter directement dans la collection existante
        existing.getFollowers().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getFollowing().clear();

        if (userprofileRequest.getFollowing() != null) {
        for (var item : userprofileRequest.getFollowing()) {
        UserFollower existingItem;
        if (item.getId() != null) {
        existingItem = followingRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setFollower(existing);

        // Ajouter directement dans la collection existante
        existing.getFollowing().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getUserAchievements().clear();

        if (userprofileRequest.getUserAchievements() != null) {
        for (var item : userprofileRequest.getUserAchievements()) {
        UserAchievement existingItem;
        if (item.getId() != null) {
        existingItem = userAchievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getUserAchievements().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getNotifications().clear();

        if (userprofileRequest.getNotifications() != null) {
        for (var item : userprofileRequest.getNotifications()) {
        Notification existingItem;
        if (item.getId() != null) {
        existingItem = notificationsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Notification not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setRecipient(existing);

        // Ajouter directement dans la collection existante
        existing.getNotifications().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getMerchandiseReviews().clear();

        if (userprofileRequest.getMerchandiseReviews() != null) {
        for (var item : userprofileRequest.getMerchandiseReviews()) {
        MerchandiseReview existingItem;
        if (item.getId() != null) {
        existingItem = merchandiseReviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getMerchandiseReviews().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getPreferences().clear();

        if (userprofileRequest.getPreferences() != null) {
        for (var item : userprofileRequest.getPreferences()) {
        UserPreference existingItem;
        if (item.getId() != null) {
        existingItem = preferencesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserPreference not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getPreferences().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getMerchandiseSales().clear();

        if (userprofileRequest.getMerchandiseSales() != null) {
        for (var item : userprofileRequest.getMerchandiseSales()) {
        MerchandiseSale existingItem;
        if (item.getId() != null) {
        existingItem = merchandiseSalesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getMerchandiseSales().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGamePlaySessions().clear();

        if (userprofileRequest.getGamePlaySessions() != null) {
        for (var item : userprofileRequest.getGamePlaySessions()) {
        GamePlaySession existingItem;
        if (item.getId() != null) {
        existingItem = gamePlaySessionsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGamePlaySessions().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGameReviewUpvotes().clear();

        if (userprofileRequest.getGameReviewUpvotes() != null) {
        for (var item : userprofileRequest.getGameReviewUpvotes()) {
        GameReviewUpvote existingItem;
        if (item.getId() != null) {
        existingItem = gameReviewUpvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGameReviewUpvotes().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getGameReviewDownvotes().clear();

        if (userprofileRequest.getGameReviewDownvotes() != null) {
        for (var item : userprofileRequest.getGameReviewDownvotes()) {
        GameReviewDownvote existingItem;
        if (item.getId() != null) {
        existingItem = gameReviewDownvotesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setUser(existing);

        // Ajouter directement dans la collection existante
        existing.getGameReviewDownvotes().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSentMessages().clear();

        if (userprofileRequest.getSentMessages() != null) {
        for (var item : userprofileRequest.getSentMessages()) {
        UserMessage existingItem;
        if (item.getId() != null) {
        existingItem = sentMessagesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSender(existing);

        // Ajouter directement dans la collection existante
        existing.getSentMessages().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getReceivedMessages().clear();

        if (userprofileRequest.getReceivedMessages() != null) {
        for (var item : userprofileRequest.getReceivedMessages()) {
        UserMessage existingItem;
        if (item.getId() != null) {
        existingItem = receivedMessagesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setReceiver(existing);

        // Ajouter directement dans la collection existante
        existing.getReceivedMessages().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

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
@Transactional
public boolean deleteById(Long id) {
Optional<UserProfile> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserProfile entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getReviews() != null) {
        for (var child : entity.getReviews()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getReviews().clear();
        }
    

    

    

    

    

    

    
        if (entity.getSubscriptions() != null) {
        for (var child : entity.getSubscriptions()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getSubscriptions().clear();
        }
    

    

    

    
        if (entity.getForumThreads() != null) {
        for (var child : entity.getForumThreads()) {
        
            child.setAuthor(null); // retirer la référence inverse
        
        }
        entity.getForumThreads().clear();
        }
    

    
        if (entity.getForumPosts() != null) {
        for (var child : entity.getForumPosts()) {
        
            child.setAuthor(null); // retirer la référence inverse
        
        }
        entity.getForumPosts().clear();
        }
    

    
        if (entity.getAchievements() != null) {
        for (var child : entity.getAchievements()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getAchievements().clear();
        }
    

    
        if (entity.getHostedOnlineEvents() != null) {
        for (var child : entity.getHostedOnlineEvents()) {
        
            child.setHost(null); // retirer la référence inverse
        
        }
        entity.getHostedOnlineEvents().clear();
        }
    

    

    

    

    

    
        if (entity.getPlaylists() != null) {
        for (var child : entity.getPlaylists()) {
        
            child.setOwner(null); // retirer la référence inverse
        
        }
        entity.getPlaylists().clear();
        }
    

    

    
        if (entity.getDigitalPurchases() != null) {
        for (var child : entity.getDigitalPurchases()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getDigitalPurchases().clear();
        }
    

    
        if (entity.getGameSessions() != null) {
        for (var child : entity.getGameSessions()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGameSessions().clear();
        }
    

    
        if (entity.getGameReviewComments() != null) {
        for (var child : entity.getGameReviewComments()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGameReviewComments().clear();
        }
    

    
        if (entity.getUserPlaylists() != null) {
        for (var child : entity.getUserPlaylists()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getUserPlaylists().clear();
        }
    

    
        if (entity.getUserPlaylistItems() != null) {
        for (var child : entity.getUserPlaylistItems()) {
        
            child.setAddedBy(null); // retirer la référence inverse
        
        }
        entity.getUserPlaylistItems().clear();
        }
    

    
        if (entity.getGivenRatings() != null) {
        for (var child : entity.getGivenRatings()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGivenRatings().clear();
        }
    

    
        if (entity.getLikedReviews() != null) {
        for (var child : entity.getLikedReviews()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getLikedReviews().clear();
        }
    

    
        if (entity.getActivityLogs() != null) {
        for (var child : entity.getActivityLogs()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getActivityLogs().clear();
        }
    

    
        if (entity.getSettings() != null) {
        for (var child : entity.getSettings()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getSettings().clear();
        }
    

    

    
        if (entity.getFollowers() != null) {
        for (var child : entity.getFollowers()) {
        
            child.setFollowed(null); // retirer la référence inverse
        
        }
        entity.getFollowers().clear();
        }
    

    
        if (entity.getFollowing() != null) {
        for (var child : entity.getFollowing()) {
        
            child.setFollower(null); // retirer la référence inverse
        
        }
        entity.getFollowing().clear();
        }
    

    
        if (entity.getUserAchievements() != null) {
        for (var child : entity.getUserAchievements()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getUserAchievements().clear();
        }
    

    
        if (entity.getNotifications() != null) {
        for (var child : entity.getNotifications()) {
        
            child.setRecipient(null); // retirer la référence inverse
        
        }
        entity.getNotifications().clear();
        }
    

    
        if (entity.getMerchandiseReviews() != null) {
        for (var child : entity.getMerchandiseReviews()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getMerchandiseReviews().clear();
        }
    

    
        if (entity.getPreferences() != null) {
        for (var child : entity.getPreferences()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getPreferences().clear();
        }
    

    
        if (entity.getMerchandiseSales() != null) {
        for (var child : entity.getMerchandiseSales()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getMerchandiseSales().clear();
        }
    

    
        if (entity.getGamePlaySessions() != null) {
        for (var child : entity.getGamePlaySessions()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGamePlaySessions().clear();
        }
    

    
        if (entity.getGameReviewUpvotes() != null) {
        for (var child : entity.getGameReviewUpvotes()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGameReviewUpvotes().clear();
        }
    

    
        if (entity.getGameReviewDownvotes() != null) {
        for (var child : entity.getGameReviewDownvotes()) {
        
            child.setUser(null); // retirer la référence inverse
        
        }
        entity.getGameReviewDownvotes().clear();
        }
    

    
        if (entity.getSentMessages() != null) {
        for (var child : entity.getSentMessages()) {
        
            child.setSender(null); // retirer la référence inverse
        
        }
        entity.getSentMessages().clear();
        }
    

    
        if (entity.getReceivedMessages() != null) {
        for (var child : entity.getReceivedMessages()) {
        
            child.setReceiver(null); // retirer la référence inverse
        
        }
        entity.getReceivedMessages().clear();
        }
    

    


// --- Dissocier ManyToMany ---

    

    
        if (entity.getWatchlistMovies() != null) {
        for (Movie item : new ArrayList<>(entity.getWatchlistMovies())) {
        
            item.getWatchlistUsers().remove(entity); // retire côté inverse
        
        }
        entity.getWatchlistMovies().clear(); // puis vide côté courant
        }
    

    
        if (entity.getFavoriteArtists() != null) {
        for (Artist item : new ArrayList<>(entity.getFavoriteArtists())) {
        
            item.getFavoriteArtists().remove(entity); // retire côté inverse
        
        }
        entity.getFavoriteArtists().clear(); // puis vide côté courant
        }
    

    
        if (entity.getFollowedUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getFollowedUsers())) {
        
            item.getFollowingUsers().remove(entity); // retire côté inverse
        
        }
        entity.getFollowedUsers().clear(); // puis vide côté courant
        }
    

    
        if (entity.getFollowingUsers() != null) {
        for (UserProfile item : new ArrayList<>(entity.getFollowingUsers())) {
        
            item.getFollowedUsers().remove(entity); // retire côté inverse
        
        }
        entity.getFollowingUsers().clear(); // puis vide côté courant
        }
    

    
        if (entity.getFavoriteGenres() != null) {
        for (Genre item : new ArrayList<>(entity.getFavoriteGenres())) {
        
            item.getFavoriteUsers().remove(entity); // retire côté inverse
        
        }
        entity.getFavoriteGenres().clear(); // puis vide côté courant
        }
    

    

    
        if (entity.getWatchedEpisodes() != null) {
        for (Episode item : new ArrayList<>(entity.getWatchedEpisodes())) {
        
            item.getWatchedByUsers().remove(entity); // retire côté inverse
        
        }
        entity.getWatchedEpisodes().clear(); // puis vide côté courant
        }
    

    
        if (entity.getPlayedGames() != null) {
        for (VideoGame item : new ArrayList<>(entity.getPlayedGames())) {
        
            item.getPlayedBy().remove(entity); // retire côté inverse
        
        }
        entity.getPlayedGames().clear(); // puis vide côté courant
        }
    

    

    

    

    

    
        if (entity.getAttendedOnlineEvents() != null) {
        for (OnlineEvent item : new ArrayList<>(entity.getAttendedOnlineEvents())) {
        
            item.getAttendees().remove(entity); // retire côté inverse
        
        }
        entity.getAttendedOnlineEvents().clear(); // puis vide côté courant
        }
    

    
        if (entity.getOwnedMerchandise() != null) {
        for (Merchandise item : new ArrayList<>(entity.getOwnedMerchandise())) {
        
            item.getOwnedByUsers().remove(entity); // retire côté inverse
        
        }
        entity.getOwnedMerchandise().clear(); // puis vide côté courant
        }
    

    
        if (entity.getLibraryPodcasts() != null) {
        for (Podcast item : new ArrayList<>(entity.getLibraryPodcasts())) {
        
            item.getListeners().remove(entity); // retire côté inverse
        
        }
        entity.getLibraryPodcasts().clear(); // puis vide côté courant
        }
    

    
        if (entity.getListenedMusic() != null) {
        for (MusicTrack item : new ArrayList<>(entity.getListenedMusic())) {
        
            item.getListenedByUsers().remove(entity); // retire côté inverse
        
        }
        entity.getListenedMusic().clear(); // puis vide côté courant
        }
    

    

    

    

    

    

    

    

    

    

    

    

    
        if (entity.getUserRole() != null) {
        for (UserRole item : new ArrayList<>(entity.getUserRole())) {
        
        }
        entity.getUserRole().clear(); // puis vide côté courant
        }
    

    

    

    

    

    

    

    

    

    

    

    

    

    
        if (entity.getEnabledFeatureFlags() != null) {
        for (FeatureFlag item : new ArrayList<>(entity.getEnabledFeatureFlags())) {
        
            item.getEnabledForUsers().remove(entity); // retire côté inverse
        
        }
        entity.getEnabledFeatureFlags().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    
        if (entity.getWallet() != null) {
        // Dissocier côté inverse automatiquement
        entity.getWallet().setUser(null);
        // Dissocier côté direct
        entity.setWallet(null);
        }
    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    


repository.delete(entity);
return true;
}
}