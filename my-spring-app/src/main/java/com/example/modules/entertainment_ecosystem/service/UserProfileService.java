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
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.repository.MessageThreadRepository;
import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.repository.UserLoyaltyProgramRepository;
import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamRepository;
import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.repository.LiveStreamViewerRepository;
import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.repository.UserBadgeRepository;
import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.repository.UserLevelRepository;
import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.repository.UserRatingRepository;
import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.repository.UserRatingRepository;
import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.repository.ForumModeratorRepository;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.repository.UserConnectionRepository;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.repository.UserConnectionRepository;
import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.repository.UserBlockedListRepository;
import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.repository.UserBlockedListRepository;
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.repository.ReportedContentRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.repository.ReviewReplyRepository;
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.repository.EpisodeReviewRepository;
import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.repository.GameTransactionRepository;
import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.repository.CountryRepository;

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
    private final UserRoleRepository userRolesRepository;
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
    private final MessageThreadRepository messageThreadsRepository;
    private final UserLoyaltyProgramRepository loyaltyProgramRepository;
    private final LiveStreamRepository hostedLiveStreamsRepository;
    private final LiveStreamViewerRepository watchedLiveStreamsRepository;
    private final UserBadgeRepository badgesRepository;
    private final UserLevelRepository userLevelRepository;
    private final UserRatingRepository givenUserRatingsRepository;
    private final UserRatingRepository receivedUserRatingsRepository;
    private final ForumModeratorRepository moderatorRepository;
    private final UserConnectionRepository connectionsRepository;
    private final UserConnectionRepository connectedByRepository;
    private final UserBlockedListRepository blockedUsersRepository;
    private final UserBlockedListRepository blockedByUsersRepository;
    private final ReportedContentRepository reportedContentRepository;
    private final ReviewReplyRepository reviewRepliesRepository;
    private final EpisodeReviewRepository episodeReviewsRepository;
    private final GameTransactionRepository gameTransactionsRepository;
    private final CountryRepository countryRepository;

    public UserProfileService(UserProfileRepository repository, ReviewRepository reviewsRepository, MovieRepository watchlistMoviesRepository, ArtistRepository favoriteArtistsRepository, UserProfileRepository followedUsersRepository, UserProfileRepository followingUsersRepository, GenreRepository favoriteGenresRepository, SubscriptionRepository subscriptionsRepository, EpisodeRepository watchedEpisodesRepository, VideoGameRepository playedGamesRepository, ForumThreadRepository forumThreadsRepository, ForumPostRepository forumPostsRepository, AchievementRepository achievementsRepository, OnlineEventRepository hostedOnlineEventsRepository, OnlineEventRepository attendedOnlineEventsRepository, MerchandiseRepository ownedMerchandiseRepository, PodcastRepository libraryPodcastsRepository, MusicTrackRepository listenedMusicRepository, PlaylistRepository playlistsRepository, UserWalletRepository walletRepository, DigitalPurchaseRepository digitalPurchasesRepository, GameSessionRepository gameSessionsRepository, GameReviewCommentRepository gameReviewCommentsRepository, UserPlaylistRepository userPlaylistsRepository, UserPlaylistItemRepository userPlaylistItemsRepository, ReviewRatingRepository givenRatingsRepository, ReviewLikeRepository likedReviewsRepository, UserActivityLogRepository activityLogsRepository, UserSettingRepository settingsRepository, UserRoleRepository userRolesRepository, UserFollowerRepository followersRepository, UserFollowerRepository followingRepository, UserAchievementRepository userAchievementsRepository, NotificationRepository notificationsRepository, MerchandiseReviewRepository merchandiseReviewsRepository, UserPreferenceRepository preferencesRepository, MerchandiseSaleRepository merchandiseSalesRepository, GamePlaySessionRepository gamePlaySessionsRepository, GameReviewUpvoteRepository gameReviewUpvotesRepository, GameReviewDownvoteRepository gameReviewDownvotesRepository, UserMessageRepository sentMessagesRepository, UserMessageRepository receivedMessagesRepository, FeatureFlagRepository enabledFeatureFlagsRepository, MessageThreadRepository messageThreadsRepository, UserLoyaltyProgramRepository loyaltyProgramRepository, LiveStreamRepository hostedLiveStreamsRepository, LiveStreamViewerRepository watchedLiveStreamsRepository, UserBadgeRepository badgesRepository, UserLevelRepository userLevelRepository, UserRatingRepository givenUserRatingsRepository, UserRatingRepository receivedUserRatingsRepository, ForumModeratorRepository moderatorRepository, UserConnectionRepository connectionsRepository, UserConnectionRepository connectedByRepository, UserBlockedListRepository blockedUsersRepository, UserBlockedListRepository blockedByUsersRepository, ReportedContentRepository reportedContentRepository, ReviewReplyRepository reviewRepliesRepository, EpisodeReviewRepository episodeReviewsRepository, GameTransactionRepository gameTransactionsRepository, CountryRepository countryRepository)
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
        this.userRolesRepository = userRolesRepository;
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
        this.messageThreadsRepository = messageThreadsRepository;
        this.loyaltyProgramRepository = loyaltyProgramRepository;
        this.hostedLiveStreamsRepository = hostedLiveStreamsRepository;
        this.watchedLiveStreamsRepository = watchedLiveStreamsRepository;
        this.badgesRepository = badgesRepository;
        this.userLevelRepository = userLevelRepository;
        this.givenUserRatingsRepository = givenUserRatingsRepository;
        this.receivedUserRatingsRepository = receivedUserRatingsRepository;
        this.moderatorRepository = moderatorRepository;
        this.connectionsRepository = connectionsRepository;
        this.connectedByRepository = connectedByRepository;
        this.blockedUsersRepository = blockedUsersRepository;
        this.blockedByUsersRepository = blockedByUsersRepository;
        this.reportedContentRepository = reportedContentRepository;
        this.reviewRepliesRepository = reviewRepliesRepository;
        this.episodeReviewsRepository = episodeReviewsRepository;
        this.gameTransactionsRepository = gameTransactionsRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public UserProfile save(UserProfile userprofile) {
    // ---------- OneToMany ----------
        if (userprofile.getReviews() != null) {
            List<Review> managedReviews = new ArrayList<>();
            for (Review item : userprofile.getReviews()) {
                if (item.getId() != null) {
                    Review existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));

                     existingItem.setUser(userprofile);
                     managedReviews.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedReviews.add(item);
                }
            }
            userprofile.setReviews(managedReviews);
        }
    
        if (userprofile.getSubscriptions() != null) {
            List<Subscription> managedSubscriptions = new ArrayList<>();
            for (Subscription item : userprofile.getSubscriptions()) {
                if (item.getId() != null) {
                    Subscription existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));

                     existingItem.setUser(userprofile);
                     managedSubscriptions.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedSubscriptions.add(item);
                }
            }
            userprofile.setSubscriptions(managedSubscriptions);
        }
    
        if (userprofile.getForumThreads() != null) {
            List<ForumThread> managedForumThreads = new ArrayList<>();
            for (ForumThread item : userprofile.getForumThreads()) {
                if (item.getId() != null) {
                    ForumThread existingItem = forumThreadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumThread not found"));

                     existingItem.setAuthor(userprofile);
                     managedForumThreads.add(existingItem);
                } else {
                    item.setAuthor(userprofile);
                    managedForumThreads.add(item);
                }
            }
            userprofile.setForumThreads(managedForumThreads);
        }
    
        if (userprofile.getForumPosts() != null) {
            List<ForumPost> managedForumPosts = new ArrayList<>();
            for (ForumPost item : userprofile.getForumPosts()) {
                if (item.getId() != null) {
                    ForumPost existingItem = forumPostsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));

                     existingItem.setAuthor(userprofile);
                     managedForumPosts.add(existingItem);
                } else {
                    item.setAuthor(userprofile);
                    managedForumPosts.add(item);
                }
            }
            userprofile.setForumPosts(managedForumPosts);
        }
    
        if (userprofile.getAchievements() != null) {
            List<Achievement> managedAchievements = new ArrayList<>();
            for (Achievement item : userprofile.getAchievements()) {
                if (item.getId() != null) {
                    Achievement existingItem = achievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Achievement not found"));

                     existingItem.setUser(userprofile);
                     managedAchievements.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedAchievements.add(item);
                }
            }
            userprofile.setAchievements(managedAchievements);
        }
    
        if (userprofile.getHostedOnlineEvents() != null) {
            List<OnlineEvent> managedHostedOnlineEvents = new ArrayList<>();
            for (OnlineEvent item : userprofile.getHostedOnlineEvents()) {
                if (item.getId() != null) {
                    OnlineEvent existingItem = hostedOnlineEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));

                     existingItem.setHost(userprofile);
                     managedHostedOnlineEvents.add(existingItem);
                } else {
                    item.setHost(userprofile);
                    managedHostedOnlineEvents.add(item);
                }
            }
            userprofile.setHostedOnlineEvents(managedHostedOnlineEvents);
        }
    
        if (userprofile.getPlaylists() != null) {
            List<Playlist> managedPlaylists = new ArrayList<>();
            for (Playlist item : userprofile.getPlaylists()) {
                if (item.getId() != null) {
                    Playlist existingItem = playlistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Playlist not found"));

                     existingItem.setOwner(userprofile);
                     managedPlaylists.add(existingItem);
                } else {
                    item.setOwner(userprofile);
                    managedPlaylists.add(item);
                }
            }
            userprofile.setPlaylists(managedPlaylists);
        }
    
        if (userprofile.getDigitalPurchases() != null) {
            List<DigitalPurchase> managedDigitalPurchases = new ArrayList<>();
            for (DigitalPurchase item : userprofile.getDigitalPurchases()) {
                if (item.getId() != null) {
                    DigitalPurchase existingItem = digitalPurchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

                     existingItem.setUser(userprofile);
                     managedDigitalPurchases.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedDigitalPurchases.add(item);
                }
            }
            userprofile.setDigitalPurchases(managedDigitalPurchases);
        }
    
        if (userprofile.getGameSessions() != null) {
            List<GameSession> managedGameSessions = new ArrayList<>();
            for (GameSession item : userprofile.getGameSessions()) {
                if (item.getId() != null) {
                    GameSession existingItem = gameSessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameSession not found"));

                     existingItem.setUser(userprofile);
                     managedGameSessions.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGameSessions.add(item);
                }
            }
            userprofile.setGameSessions(managedGameSessions);
        }
    
        if (userprofile.getGameReviewComments() != null) {
            List<GameReviewComment> managedGameReviewComments = new ArrayList<>();
            for (GameReviewComment item : userprofile.getGameReviewComments()) {
                if (item.getId() != null) {
                    GameReviewComment existingItem = gameReviewCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));

                     existingItem.setUser(userprofile);
                     managedGameReviewComments.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGameReviewComments.add(item);
                }
            }
            userprofile.setGameReviewComments(managedGameReviewComments);
        }
    
        if (userprofile.getUserPlaylists() != null) {
            List<UserPlaylist> managedUserPlaylists = new ArrayList<>();
            for (UserPlaylist item : userprofile.getUserPlaylists()) {
                if (item.getId() != null) {
                    UserPlaylist existingItem = userPlaylistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

                     existingItem.setUser(userprofile);
                     managedUserPlaylists.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedUserPlaylists.add(item);
                }
            }
            userprofile.setUserPlaylists(managedUserPlaylists);
        }
    
        if (userprofile.getUserPlaylistItems() != null) {
            List<UserPlaylistItem> managedUserPlaylistItems = new ArrayList<>();
            for (UserPlaylistItem item : userprofile.getUserPlaylistItems()) {
                if (item.getId() != null) {
                    UserPlaylistItem existingItem = userPlaylistItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));

                     existingItem.setAddedBy(userprofile);
                     managedUserPlaylistItems.add(existingItem);
                } else {
                    item.setAddedBy(userprofile);
                    managedUserPlaylistItems.add(item);
                }
            }
            userprofile.setUserPlaylistItems(managedUserPlaylistItems);
        }
    
        if (userprofile.getGivenRatings() != null) {
            List<ReviewRating> managedGivenRatings = new ArrayList<>();
            for (ReviewRating item : userprofile.getGivenRatings()) {
                if (item.getId() != null) {
                    ReviewRating existingItem = givenRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));

                     existingItem.setUser(userprofile);
                     managedGivenRatings.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGivenRatings.add(item);
                }
            }
            userprofile.setGivenRatings(managedGivenRatings);
        }
    
        if (userprofile.getLikedReviews() != null) {
            List<ReviewLike> managedLikedReviews = new ArrayList<>();
            for (ReviewLike item : userprofile.getLikedReviews()) {
                if (item.getId() != null) {
                    ReviewLike existingItem = likedReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));

                     existingItem.setUser(userprofile);
                     managedLikedReviews.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedLikedReviews.add(item);
                }
            }
            userprofile.setLikedReviews(managedLikedReviews);
        }
    
        if (userprofile.getActivityLogs() != null) {
            List<UserActivityLog> managedActivityLogs = new ArrayList<>();
            for (UserActivityLog item : userprofile.getActivityLogs()) {
                if (item.getId() != null) {
                    UserActivityLog existingItem = activityLogsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));

                     existingItem.setUser(userprofile);
                     managedActivityLogs.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedActivityLogs.add(item);
                }
            }
            userprofile.setActivityLogs(managedActivityLogs);
        }
    
        if (userprofile.getSettings() != null) {
            List<UserSetting> managedSettings = new ArrayList<>();
            for (UserSetting item : userprofile.getSettings()) {
                if (item.getId() != null) {
                    UserSetting existingItem = settingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserSetting not found"));

                     existingItem.setUser(userprofile);
                     managedSettings.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedSettings.add(item);
                }
            }
            userprofile.setSettings(managedSettings);
        }
    
        if (userprofile.getFollowers() != null) {
            List<UserFollower> managedFollowers = new ArrayList<>();
            for (UserFollower item : userprofile.getFollowers()) {
                if (item.getId() != null) {
                    UserFollower existingItem = followersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserFollower not found"));

                     existingItem.setFollowed(userprofile);
                     managedFollowers.add(existingItem);
                } else {
                    item.setFollowed(userprofile);
                    managedFollowers.add(item);
                }
            }
            userprofile.setFollowers(managedFollowers);
        }
    
        if (userprofile.getFollowing() != null) {
            List<UserFollower> managedFollowing = new ArrayList<>();
            for (UserFollower item : userprofile.getFollowing()) {
                if (item.getId() != null) {
                    UserFollower existingItem = followingRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserFollower not found"));

                     existingItem.setFollower(userprofile);
                     managedFollowing.add(existingItem);
                } else {
                    item.setFollower(userprofile);
                    managedFollowing.add(item);
                }
            }
            userprofile.setFollowing(managedFollowing);
        }
    
        if (userprofile.getUserAchievements() != null) {
            List<UserAchievement> managedUserAchievements = new ArrayList<>();
            for (UserAchievement item : userprofile.getUserAchievements()) {
                if (item.getId() != null) {
                    UserAchievement existingItem = userAchievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));

                     existingItem.setUser(userprofile);
                     managedUserAchievements.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedUserAchievements.add(item);
                }
            }
            userprofile.setUserAchievements(managedUserAchievements);
        }
    
        if (userprofile.getNotifications() != null) {
            List<Notification> managedNotifications = new ArrayList<>();
            for (Notification item : userprofile.getNotifications()) {
                if (item.getId() != null) {
                    Notification existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));

                     existingItem.setRecipient(userprofile);
                     managedNotifications.add(existingItem);
                } else {
                    item.setRecipient(userprofile);
                    managedNotifications.add(item);
                }
            }
            userprofile.setNotifications(managedNotifications);
        }
    
        if (userprofile.getMerchandiseReviews() != null) {
            List<MerchandiseReview> managedMerchandiseReviews = new ArrayList<>();
            for (MerchandiseReview item : userprofile.getMerchandiseReviews()) {
                if (item.getId() != null) {
                    MerchandiseReview existingItem = merchandiseReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));

                     existingItem.setUser(userprofile);
                     managedMerchandiseReviews.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedMerchandiseReviews.add(item);
                }
            }
            userprofile.setMerchandiseReviews(managedMerchandiseReviews);
        }
    
        if (userprofile.getPreferences() != null) {
            List<UserPreference> managedPreferences = new ArrayList<>();
            for (UserPreference item : userprofile.getPreferences()) {
                if (item.getId() != null) {
                    UserPreference existingItem = preferencesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPreference not found"));

                     existingItem.setUser(userprofile);
                     managedPreferences.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedPreferences.add(item);
                }
            }
            userprofile.setPreferences(managedPreferences);
        }
    
        if (userprofile.getMerchandiseSales() != null) {
            List<MerchandiseSale> managedMerchandiseSales = new ArrayList<>();
            for (MerchandiseSale item : userprofile.getMerchandiseSales()) {
                if (item.getId() != null) {
                    MerchandiseSale existingItem = merchandiseSalesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));

                     existingItem.setUser(userprofile);
                     managedMerchandiseSales.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedMerchandiseSales.add(item);
                }
            }
            userprofile.setMerchandiseSales(managedMerchandiseSales);
        }
    
        if (userprofile.getGamePlaySessions() != null) {
            List<GamePlaySession> managedGamePlaySessions = new ArrayList<>();
            for (GamePlaySession item : userprofile.getGamePlaySessions()) {
                if (item.getId() != null) {
                    GamePlaySession existingItem = gamePlaySessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

                     existingItem.setUser(userprofile);
                     managedGamePlaySessions.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGamePlaySessions.add(item);
                }
            }
            userprofile.setGamePlaySessions(managedGamePlaySessions);
        }
    
        if (userprofile.getGameReviewUpvotes() != null) {
            List<GameReviewUpvote> managedGameReviewUpvotes = new ArrayList<>();
            for (GameReviewUpvote item : userprofile.getGameReviewUpvotes()) {
                if (item.getId() != null) {
                    GameReviewUpvote existingItem = gameReviewUpvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

                     existingItem.setUser(userprofile);
                     managedGameReviewUpvotes.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGameReviewUpvotes.add(item);
                }
            }
            userprofile.setGameReviewUpvotes(managedGameReviewUpvotes);
        }
    
        if (userprofile.getGameReviewDownvotes() != null) {
            List<GameReviewDownvote> managedGameReviewDownvotes = new ArrayList<>();
            for (GameReviewDownvote item : userprofile.getGameReviewDownvotes()) {
                if (item.getId() != null) {
                    GameReviewDownvote existingItem = gameReviewDownvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));

                     existingItem.setUser(userprofile);
                     managedGameReviewDownvotes.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGameReviewDownvotes.add(item);
                }
            }
            userprofile.setGameReviewDownvotes(managedGameReviewDownvotes);
        }
    
        if (userprofile.getSentMessages() != null) {
            List<UserMessage> managedSentMessages = new ArrayList<>();
            for (UserMessage item : userprofile.getSentMessages()) {
                if (item.getId() != null) {
                    UserMessage existingItem = sentMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));

                     existingItem.setSender(userprofile);
                     managedSentMessages.add(existingItem);
                } else {
                    item.setSender(userprofile);
                    managedSentMessages.add(item);
                }
            }
            userprofile.setSentMessages(managedSentMessages);
        }
    
        if (userprofile.getReceivedMessages() != null) {
            List<UserMessage> managedReceivedMessages = new ArrayList<>();
            for (UserMessage item : userprofile.getReceivedMessages()) {
                if (item.getId() != null) {
                    UserMessage existingItem = receivedMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));

                     existingItem.setReceiver(userprofile);
                     managedReceivedMessages.add(existingItem);
                } else {
                    item.setReceiver(userprofile);
                    managedReceivedMessages.add(item);
                }
            }
            userprofile.setReceivedMessages(managedReceivedMessages);
        }
    
        if (userprofile.getHostedLiveStreams() != null) {
            List<LiveStream> managedHostedLiveStreams = new ArrayList<>();
            for (LiveStream item : userprofile.getHostedLiveStreams()) {
                if (item.getId() != null) {
                    LiveStream existingItem = hostedLiveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStream not found"));

                     existingItem.setHost(userprofile);
                     managedHostedLiveStreams.add(existingItem);
                } else {
                    item.setHost(userprofile);
                    managedHostedLiveStreams.add(item);
                }
            }
            userprofile.setHostedLiveStreams(managedHostedLiveStreams);
        }
    
        if (userprofile.getWatchedLiveStreams() != null) {
            List<LiveStreamViewer> managedWatchedLiveStreams = new ArrayList<>();
            for (LiveStreamViewer item : userprofile.getWatchedLiveStreams()) {
                if (item.getId() != null) {
                    LiveStreamViewer existingItem = watchedLiveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));

                     existingItem.setUser(userprofile);
                     managedWatchedLiveStreams.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedWatchedLiveStreams.add(item);
                }
            }
            userprofile.setWatchedLiveStreams(managedWatchedLiveStreams);
        }
    
        if (userprofile.getGivenUserRatings() != null) {
            List<UserRating> managedGivenUserRatings = new ArrayList<>();
            for (UserRating item : userprofile.getGivenUserRatings()) {
                if (item.getId() != null) {
                    UserRating existingItem = givenUserRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserRating not found"));

                     existingItem.setUser(userprofile);
                     managedGivenUserRatings.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGivenUserRatings.add(item);
                }
            }
            userprofile.setGivenUserRatings(managedGivenUserRatings);
        }
    
        if (userprofile.getReceivedUserRatings() != null) {
            List<UserRating> managedReceivedUserRatings = new ArrayList<>();
            for (UserRating item : userprofile.getReceivedUserRatings()) {
                if (item.getId() != null) {
                    UserRating existingItem = receivedUserRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserRating not found"));

                     existingItem.setRatedUser(userprofile);
                     managedReceivedUserRatings.add(existingItem);
                } else {
                    item.setRatedUser(userprofile);
                    managedReceivedUserRatings.add(item);
                }
            }
            userprofile.setReceivedUserRatings(managedReceivedUserRatings);
        }
    
        if (userprofile.getConnections() != null) {
            List<UserConnection> managedConnections = new ArrayList<>();
            for (UserConnection item : userprofile.getConnections()) {
                if (item.getId() != null) {
                    UserConnection existingItem = connectionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));

                     existingItem.setUser1(userprofile);
                     managedConnections.add(existingItem);
                } else {
                    item.setUser1(userprofile);
                    managedConnections.add(item);
                }
            }
            userprofile.setConnections(managedConnections);
        }
    
        if (userprofile.getConnectedBy() != null) {
            List<UserConnection> managedConnectedBy = new ArrayList<>();
            for (UserConnection item : userprofile.getConnectedBy()) {
                if (item.getId() != null) {
                    UserConnection existingItem = connectedByRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));

                     existingItem.setUser2(userprofile);
                     managedConnectedBy.add(existingItem);
                } else {
                    item.setUser2(userprofile);
                    managedConnectedBy.add(item);
                }
            }
            userprofile.setConnectedBy(managedConnectedBy);
        }
    
        if (userprofile.getBlockedUsers() != null) {
            List<UserBlockedList> managedBlockedUsers = new ArrayList<>();
            for (UserBlockedList item : userprofile.getBlockedUsers()) {
                if (item.getId() != null) {
                    UserBlockedList existingItem = blockedUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserBlockedList not found"));

                     existingItem.setBlocker(userprofile);
                     managedBlockedUsers.add(existingItem);
                } else {
                    item.setBlocker(userprofile);
                    managedBlockedUsers.add(item);
                }
            }
            userprofile.setBlockedUsers(managedBlockedUsers);
        }
    
        if (userprofile.getBlockedByUsers() != null) {
            List<UserBlockedList> managedBlockedByUsers = new ArrayList<>();
            for (UserBlockedList item : userprofile.getBlockedByUsers()) {
                if (item.getId() != null) {
                    UserBlockedList existingItem = blockedByUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserBlockedList not found"));

                     existingItem.setBlocked(userprofile);
                     managedBlockedByUsers.add(existingItem);
                } else {
                    item.setBlocked(userprofile);
                    managedBlockedByUsers.add(item);
                }
            }
            userprofile.setBlockedByUsers(managedBlockedByUsers);
        }
    
        if (userprofile.getReportedContent() != null) {
            List<ReportedContent> managedReportedContent = new ArrayList<>();
            for (ReportedContent item : userprofile.getReportedContent()) {
                if (item.getId() != null) {
                    ReportedContent existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));

                     existingItem.setUser(userprofile);
                     managedReportedContent.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedReportedContent.add(item);
                }
            }
            userprofile.setReportedContent(managedReportedContent);
        }
    
        if (userprofile.getReviewReplies() != null) {
            List<ReviewReply> managedReviewReplies = new ArrayList<>();
            for (ReviewReply item : userprofile.getReviewReplies()) {
                if (item.getId() != null) {
                    ReviewReply existingItem = reviewRepliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewReply not found"));

                     existingItem.setUser(userprofile);
                     managedReviewReplies.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedReviewReplies.add(item);
                }
            }
            userprofile.setReviewReplies(managedReviewReplies);
        }
    
        if (userprofile.getEpisodeReviews() != null) {
            List<EpisodeReview> managedEpisodeReviews = new ArrayList<>();
            for (EpisodeReview item : userprofile.getEpisodeReviews()) {
                if (item.getId() != null) {
                    EpisodeReview existingItem = episodeReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeReview not found"));

                     existingItem.setUser(userprofile);
                     managedEpisodeReviews.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedEpisodeReviews.add(item);
                }
            }
            userprofile.setEpisodeReviews(managedEpisodeReviews);
        }
    
        if (userprofile.getGameTransactions() != null) {
            List<GameTransaction> managedGameTransactions = new ArrayList<>();
            for (GameTransaction item : userprofile.getGameTransactions()) {
                if (item.getId() != null) {
                    GameTransaction existingItem = gameTransactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameTransaction not found"));

                     existingItem.setUser(userprofile);
                     managedGameTransactions.add(existingItem);
                } else {
                    item.setUser(userprofile);
                    managedGameTransactions.add(item);
                }
            }
            userprofile.setGameTransactions(managedGameTransactions);
        }
    
    // ---------- ManyToMany ----------
        if (userprofile.getWatchlistMovies() != null &&
            !userprofile.getWatchlistMovies().isEmpty()) {

            List<Movie> attachedWatchlistMovies = new ArrayList<>();
            for (Movie item : userprofile.getWatchlistMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = watchlistMoviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId()));
                    attachedWatchlistMovies.add(existingItem);
                } else {

                    Movie newItem = watchlistMoviesRepository.save(item);
                    attachedWatchlistMovies.add(newItem);
                }
            }

            userprofile.setWatchlistMovies(attachedWatchlistMovies);

            // côté propriétaire (Movie → UserProfile)
            attachedWatchlistMovies.forEach(it -> it.getWatchlistUsers().add(userprofile));
        }
        
        if (userprofile.getFavoriteArtists() != null &&
            !userprofile.getFavoriteArtists().isEmpty()) {

            List<Artist> attachedFavoriteArtists = new ArrayList<>();
            for (Artist item : userprofile.getFavoriteArtists()) {
                if (item.getId() != null) {
                    Artist existingItem = favoriteArtistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId()));
                    attachedFavoriteArtists.add(existingItem);
                } else {

                    Artist newItem = favoriteArtistsRepository.save(item);
                    attachedFavoriteArtists.add(newItem);
                }
            }

            userprofile.setFavoriteArtists(attachedFavoriteArtists);

            // côté propriétaire (Artist → UserProfile)
            attachedFavoriteArtists.forEach(it -> it.getFavoriteArtists().add(userprofile));
        }
        
        if (userprofile.getFollowedUsers() != null &&
            !userprofile.getFollowedUsers().isEmpty()) {

            List<UserProfile> attachedFollowedUsers = new ArrayList<>();
            for (UserProfile item : userprofile.getFollowedUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = followedUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedFollowedUsers.add(existingItem);
                } else {

                    UserProfile newItem = followedUsersRepository.save(item);
                    attachedFollowedUsers.add(newItem);
                }
            }

            userprofile.setFollowedUsers(attachedFollowedUsers);

            // côté propriétaire (UserProfile → UserProfile)
            attachedFollowedUsers.forEach(it -> it.getFollowingUsers().add(userprofile));
        }
        
        if (userprofile.getFollowingUsers() != null &&
            !userprofile.getFollowingUsers().isEmpty()) {

            List<UserProfile> attachedFollowingUsers = new ArrayList<>();
            for (UserProfile item : userprofile.getFollowingUsers()) {
                if (item.getId() != null) {
                    UserProfile existingItem = followingUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedFollowingUsers.add(existingItem);
                } else {

                    UserProfile newItem = followingUsersRepository.save(item);
                    attachedFollowingUsers.add(newItem);
                }
            }

            userprofile.setFollowingUsers(attachedFollowingUsers);

            // côté propriétaire (UserProfile → UserProfile)
            attachedFollowingUsers.forEach(it -> it.getFollowedUsers().add(userprofile));
        }
        
        if (userprofile.getFavoriteGenres() != null &&
            !userprofile.getFavoriteGenres().isEmpty()) {

            List<Genre> attachedFavoriteGenres = new ArrayList<>();
            for (Genre item : userprofile.getFavoriteGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = favoriteGenresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedFavoriteGenres.add(existingItem);
                } else {

                    Genre newItem = favoriteGenresRepository.save(item);
                    attachedFavoriteGenres.add(newItem);
                }
            }

            userprofile.setFavoriteGenres(attachedFavoriteGenres);

            // côté propriétaire (Genre → UserProfile)
            attachedFavoriteGenres.forEach(it -> it.getFavoriteUsers().add(userprofile));
        }
        
        if (userprofile.getWatchedEpisodes() != null &&
            !userprofile.getWatchedEpisodes().isEmpty()) {

            List<Episode> attachedWatchedEpisodes = new ArrayList<>();
            for (Episode item : userprofile.getWatchedEpisodes()) {
                if (item.getId() != null) {
                    Episode existingItem = watchedEpisodesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Episode not found with id " + item.getId()));
                    attachedWatchedEpisodes.add(existingItem);
                } else {

                    Episode newItem = watchedEpisodesRepository.save(item);
                    attachedWatchedEpisodes.add(newItem);
                }
            }

            userprofile.setWatchedEpisodes(attachedWatchedEpisodes);

            // côté propriétaire (Episode → UserProfile)
            attachedWatchedEpisodes.forEach(it -> it.getWatchedByUsers().add(userprofile));
        }
        
        if (userprofile.getPlayedGames() != null &&
            !userprofile.getPlayedGames().isEmpty()) {

            List<VideoGame> attachedPlayedGames = new ArrayList<>();
            for (VideoGame item : userprofile.getPlayedGames()) {
                if (item.getId() != null) {
                    VideoGame existingItem = playedGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found with id " + item.getId()));
                    attachedPlayedGames.add(existingItem);
                } else {

                    VideoGame newItem = playedGamesRepository.save(item);
                    attachedPlayedGames.add(newItem);
                }
            }

            userprofile.setPlayedGames(attachedPlayedGames);

            // côté propriétaire (VideoGame → UserProfile)
            attachedPlayedGames.forEach(it -> it.getPlayedBy().add(userprofile));
        }
        
        if (userprofile.getAttendedOnlineEvents() != null &&
            !userprofile.getAttendedOnlineEvents().isEmpty()) {

            List<OnlineEvent> attachedAttendedOnlineEvents = new ArrayList<>();
            for (OnlineEvent item : userprofile.getAttendedOnlineEvents()) {
                if (item.getId() != null) {
                    OnlineEvent existingItem = attendedOnlineEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OnlineEvent not found with id " + item.getId()));
                    attachedAttendedOnlineEvents.add(existingItem);
                } else {

                    OnlineEvent newItem = attendedOnlineEventsRepository.save(item);
                    attachedAttendedOnlineEvents.add(newItem);
                }
            }

            userprofile.setAttendedOnlineEvents(attachedAttendedOnlineEvents);

            // côté propriétaire (OnlineEvent → UserProfile)
            attachedAttendedOnlineEvents.forEach(it -> it.getAttendees().add(userprofile));
        }
        
        if (userprofile.getOwnedMerchandise() != null &&
            !userprofile.getOwnedMerchandise().isEmpty()) {

            List<Merchandise> attachedOwnedMerchandise = new ArrayList<>();
            for (Merchandise item : userprofile.getOwnedMerchandise()) {
                if (item.getId() != null) {
                    Merchandise existingItem = ownedMerchandiseRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Merchandise not found with id " + item.getId()));
                    attachedOwnedMerchandise.add(existingItem);
                } else {

                    Merchandise newItem = ownedMerchandiseRepository.save(item);
                    attachedOwnedMerchandise.add(newItem);
                }
            }

            userprofile.setOwnedMerchandise(attachedOwnedMerchandise);

            // côté propriétaire (Merchandise → UserProfile)
            attachedOwnedMerchandise.forEach(it -> it.getOwnedByUsers().add(userprofile));
        }
        
        if (userprofile.getLibraryPodcasts() != null &&
            !userprofile.getLibraryPodcasts().isEmpty()) {

            List<Podcast> attachedLibraryPodcasts = new ArrayList<>();
            for (Podcast item : userprofile.getLibraryPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = libraryPodcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId()));
                    attachedLibraryPodcasts.add(existingItem);
                } else {

                    Podcast newItem = libraryPodcastsRepository.save(item);
                    attachedLibraryPodcasts.add(newItem);
                }
            }

            userprofile.setLibraryPodcasts(attachedLibraryPodcasts);

            // côté propriétaire (Podcast → UserProfile)
            attachedLibraryPodcasts.forEach(it -> it.getListeners().add(userprofile));
        }
        
        if (userprofile.getListenedMusic() != null &&
            !userprofile.getListenedMusic().isEmpty()) {

            List<MusicTrack> attachedListenedMusic = new ArrayList<>();
            for (MusicTrack item : userprofile.getListenedMusic()) {
                if (item.getId() != null) {
                    MusicTrack existingItem = listenedMusicRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MusicTrack not found with id " + item.getId()));
                    attachedListenedMusic.add(existingItem);
                } else {

                    MusicTrack newItem = listenedMusicRepository.save(item);
                    attachedListenedMusic.add(newItem);
                }
            }

            userprofile.setListenedMusic(attachedListenedMusic);

            // côté propriétaire (MusicTrack → UserProfile)
            attachedListenedMusic.forEach(it -> it.getListenedByUsers().add(userprofile));
        }
        
        if (userprofile.getUserRoles() != null &&
            !userprofile.getUserRoles().isEmpty()) {

            List<UserRole> attachedUserRoles = new ArrayList<>();
            for (UserRole item : userprofile.getUserRoles()) {
                if (item.getId() != null) {
                    UserRole existingItem = userRolesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserRole not found with id " + item.getId()));
                    attachedUserRoles.add(existingItem);
                } else {

                    UserRole newItem = userRolesRepository.save(item);
                    attachedUserRoles.add(newItem);
                }
            }

            userprofile.setUserRoles(attachedUserRoles);

            // côté propriétaire (UserRole → UserProfile)
            attachedUserRoles.forEach(it -> it.getUsers().add(userprofile));
        }
        
        if (userprofile.getEnabledFeatureFlags() != null &&
            !userprofile.getEnabledFeatureFlags().isEmpty()) {

            List<FeatureFlag> attachedEnabledFeatureFlags = new ArrayList<>();
            for (FeatureFlag item : userprofile.getEnabledFeatureFlags()) {
                if (item.getId() != null) {
                    FeatureFlag existingItem = enabledFeatureFlagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("FeatureFlag not found with id " + item.getId()));
                    attachedEnabledFeatureFlags.add(existingItem);
                } else {

                    FeatureFlag newItem = enabledFeatureFlagsRepository.save(item);
                    attachedEnabledFeatureFlags.add(newItem);
                }
            }

            userprofile.setEnabledFeatureFlags(attachedEnabledFeatureFlags);

            // côté propriétaire (FeatureFlag → UserProfile)
            attachedEnabledFeatureFlags.forEach(it -> it.getEnabledForUsers().add(userprofile));
        }
        
        if (userprofile.getMessageThreads() != null &&
            !userprofile.getMessageThreads().isEmpty()) {

            List<MessageThread> attachedMessageThreads = new ArrayList<>();
            for (MessageThread item : userprofile.getMessageThreads()) {
                if (item.getId() != null) {
                    MessageThread existingItem = messageThreadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MessageThread not found with id " + item.getId()));
                    attachedMessageThreads.add(existingItem);
                } else {

                    MessageThread newItem = messageThreadsRepository.save(item);
                    attachedMessageThreads.add(newItem);
                }
            }

            userprofile.setMessageThreads(attachedMessageThreads);

            // côté propriétaire (MessageThread → UserProfile)
            attachedMessageThreads.forEach(it -> it.getParticipants().add(userprofile));
        }
        
        if (userprofile.getBadges() != null &&
            !userprofile.getBadges().isEmpty()) {

            List<UserBadge> attachedBadges = new ArrayList<>();
            for (UserBadge item : userprofile.getBadges()) {
                if (item.getId() != null) {
                    UserBadge existingItem = badgesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserBadge not found with id " + item.getId()));
                    attachedBadges.add(existingItem);
                } else {

                    UserBadge newItem = badgesRepository.save(item);
                    attachedBadges.add(newItem);
                }
            }

            userprofile.setBadges(attachedBadges);

            // côté propriétaire (UserBadge → UserProfile)
            attachedBadges.forEach(it -> it.getUsers().add(userprofile));
        }
        
    // ---------- ManyToOne ----------
        if (userprofile.getUserLevel() != null) {
            if (userprofile.getUserLevel().getId() != null) {
                UserLevel existingUserLevel = userLevelRepository.findById(
                    userprofile.getUserLevel().getId()
                ).orElseThrow(() -> new RuntimeException("UserLevel not found with id "
                    + userprofile.getUserLevel().getId()));
                userprofile.setUserLevel(existingUserLevel);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserLevel newUserLevel = userLevelRepository.save(userprofile.getUserLevel());
                userprofile.setUserLevel(newUserLevel);
            }
        }
        
        if (userprofile.getCountry() != null) {
            if (userprofile.getCountry().getId() != null) {
                Country existingCountry = countryRepository.findById(
                    userprofile.getCountry().getId()
                ).orElseThrow(() -> new RuntimeException("Country not found with id "
                    + userprofile.getCountry().getId()));
                userprofile.setCountry(existingCountry);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Country newCountry = countryRepository.save(userprofile.getCountry());
                userprofile.setCountry(newCountry);
            }
        }
        
    // ---------- OneToOne ----------
        if (userprofile.getWallet() != null) {
            if (userprofile.getWallet().getId() != null) {
                UserWallet existingWallet = walletRepository.findById(userprofile.getWallet().getId())
                    .orElseThrow(() -> new RuntimeException("UserWallet not found with id "
                        + userprofile.getWallet().getId()));
                userprofile.setWallet(existingWallet);
            } else {
                // Nouvel objet → sauvegarde d'abord
                UserWallet newWallet = walletRepository.save(userprofile.getWallet());
                userprofile.setWallet(newWallet);
            }

            userprofile.getWallet().setUser(userprofile);
        }
        
        if (userprofile.getLoyaltyProgram() != null) {
            if (userprofile.getLoyaltyProgram().getId() != null) {
                UserLoyaltyProgram existingLoyaltyProgram = loyaltyProgramRepository.findById(userprofile.getLoyaltyProgram().getId())
                    .orElseThrow(() -> new RuntimeException("UserLoyaltyProgram not found with id "
                        + userprofile.getLoyaltyProgram().getId()));
                userprofile.setLoyaltyProgram(existingLoyaltyProgram);
            } else {
                // Nouvel objet → sauvegarde d'abord
                UserLoyaltyProgram newLoyaltyProgram = loyaltyProgramRepository.save(userprofile.getLoyaltyProgram());
                userprofile.setLoyaltyProgram(newLoyaltyProgram);
            }

            userprofile.getLoyaltyProgram().setUser(userprofile);
        }
        
        if (userprofile.getModerator() != null) {
            if (userprofile.getModerator().getId() != null) {
                ForumModerator existingModerator = moderatorRepository.findById(userprofile.getModerator().getId())
                    .orElseThrow(() -> new RuntimeException("ForumModerator not found with id "
                        + userprofile.getModerator().getId()));
                userprofile.setModerator(existingModerator);
            } else {
                // Nouvel objet → sauvegarde d'abord
                ForumModerator newModerator = moderatorRepository.save(userprofile.getModerator());
                userprofile.setModerator(newModerator);
            }

            userprofile.getModerator().setUser(userprofile);
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
        existing.setProfilePictureUrl(userprofileRequest.getProfilePictureUrl());
        existing.setLastActiveDate(userprofileRequest.getLastActiveDate());
        existing.setUserStatus(userprofileRequest.getUserStatus());
        existing.setBio(userprofileRequest.getBio());

    // ---------- Relations ManyToOne ----------
        if (userprofileRequest.getUserLevel() != null &&
            userprofileRequest.getUserLevel().getId() != null) {

            UserLevel existingUserLevel = userLevelRepository.findById(
                userprofileRequest.getUserLevel().getId()
            ).orElseThrow(() -> new RuntimeException("UserLevel not found"));

            existing.setUserLevel(existingUserLevel);
        } else {
            existing.setUserLevel(null);
        }
        
        if (userprofileRequest.getCountry() != null &&
            userprofileRequest.getCountry().getId() != null) {

            Country existingCountry = countryRepository.findById(
                userprofileRequest.getCountry().getId()
            ).orElseThrow(() -> new RuntimeException("Country not found"));

            existing.setCountry(existingCountry);
        } else {
            existing.setCountry(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (userprofileRequest.getWatchlistMovies() != null) {
            existing.getWatchlistMovies().clear();

            List<Movie> watchlistMoviesList = userprofileRequest.getWatchlistMovies().stream()
                .map(item -> watchlistMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getWatchlistMovies().addAll(watchlistMoviesList);

            // Mettre à jour le côté inverse
            watchlistMoviesList.forEach(it -> {
                if (!it.getWatchlistUsers().contains(existing)) {
                    it.getWatchlistUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getFavoriteArtists() != null) {
            existing.getFavoriteArtists().clear();

            List<Artist> favoriteArtistsList = userprofileRequest.getFavoriteArtists().stream()
                .map(item -> favoriteArtistsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Artist not found")))
                .collect(Collectors.toList());

            existing.getFavoriteArtists().addAll(favoriteArtistsList);

            // Mettre à jour le côté inverse
            favoriteArtistsList.forEach(it -> {
                if (!it.getFavoriteArtists().contains(existing)) {
                    it.getFavoriteArtists().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getFollowedUsers() != null) {
            existing.getFollowedUsers().clear();

            List<UserProfile> followedUsersList = userprofileRequest.getFollowedUsers().stream()
                .map(item -> followedUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getFollowedUsers().addAll(followedUsersList);

            // Mettre à jour le côté inverse
            followedUsersList.forEach(it -> {
                if (!it.getFollowingUsers().contains(existing)) {
                    it.getFollowingUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getFollowingUsers() != null) {
            existing.getFollowingUsers().clear();

            List<UserProfile> followingUsersList = userprofileRequest.getFollowingUsers().stream()
                .map(item -> followingUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getFollowingUsers().addAll(followingUsersList);

            // Mettre à jour le côté inverse
            followingUsersList.forEach(it -> {
                if (!it.getFollowedUsers().contains(existing)) {
                    it.getFollowedUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getFavoriteGenres() != null) {
            existing.getFavoriteGenres().clear();

            List<Genre> favoriteGenresList = userprofileRequest.getFavoriteGenres().stream()
                .map(item -> favoriteGenresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getFavoriteGenres().addAll(favoriteGenresList);

            // Mettre à jour le côté inverse
            favoriteGenresList.forEach(it -> {
                if (!it.getFavoriteUsers().contains(existing)) {
                    it.getFavoriteUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getWatchedEpisodes() != null) {
            existing.getWatchedEpisodes().clear();

            List<Episode> watchedEpisodesList = userprofileRequest.getWatchedEpisodes().stream()
                .map(item -> watchedEpisodesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Episode not found")))
                .collect(Collectors.toList());

            existing.getWatchedEpisodes().addAll(watchedEpisodesList);

            // Mettre à jour le côté inverse
            watchedEpisodesList.forEach(it -> {
                if (!it.getWatchedByUsers().contains(existing)) {
                    it.getWatchedByUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getPlayedGames() != null) {
            existing.getPlayedGames().clear();

            List<VideoGame> playedGamesList = userprofileRequest.getPlayedGames().stream()
                .map(item -> playedGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());

            existing.getPlayedGames().addAll(playedGamesList);

            // Mettre à jour le côté inverse
            playedGamesList.forEach(it -> {
                if (!it.getPlayedBy().contains(existing)) {
                    it.getPlayedBy().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getAttendedOnlineEvents() != null) {
            existing.getAttendedOnlineEvents().clear();

            List<OnlineEvent> attendedOnlineEventsList = userprofileRequest.getAttendedOnlineEvents().stream()
                .map(item -> attendedOnlineEventsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("OnlineEvent not found")))
                .collect(Collectors.toList());

            existing.getAttendedOnlineEvents().addAll(attendedOnlineEventsList);

            // Mettre à jour le côté inverse
            attendedOnlineEventsList.forEach(it -> {
                if (!it.getAttendees().contains(existing)) {
                    it.getAttendees().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getOwnedMerchandise() != null) {
            existing.getOwnedMerchandise().clear();

            List<Merchandise> ownedMerchandiseList = userprofileRequest.getOwnedMerchandise().stream()
                .map(item -> ownedMerchandiseRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Merchandise not found")))
                .collect(Collectors.toList());

            existing.getOwnedMerchandise().addAll(ownedMerchandiseList);

            // Mettre à jour le côté inverse
            ownedMerchandiseList.forEach(it -> {
                if (!it.getOwnedByUsers().contains(existing)) {
                    it.getOwnedByUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getLibraryPodcasts() != null) {
            existing.getLibraryPodcasts().clear();

            List<Podcast> libraryPodcastsList = userprofileRequest.getLibraryPodcasts().stream()
                .map(item -> libraryPodcastsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Podcast not found")))
                .collect(Collectors.toList());

            existing.getLibraryPodcasts().addAll(libraryPodcastsList);

            // Mettre à jour le côté inverse
            libraryPodcastsList.forEach(it -> {
                if (!it.getListeners().contains(existing)) {
                    it.getListeners().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getListenedMusic() != null) {
            existing.getListenedMusic().clear();

            List<MusicTrack> listenedMusicList = userprofileRequest.getListenedMusic().stream()
                .map(item -> listenedMusicRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MusicTrack not found")))
                .collect(Collectors.toList());

            existing.getListenedMusic().addAll(listenedMusicList);

            // Mettre à jour le côté inverse
            listenedMusicList.forEach(it -> {
                if (!it.getListenedByUsers().contains(existing)) {
                    it.getListenedByUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getUserRoles() != null) {
            existing.getUserRoles().clear();

            List<UserRole> userRolesList = userprofileRequest.getUserRoles().stream()
                .map(item -> userRolesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserRole not found")))
                .collect(Collectors.toList());

            existing.getUserRoles().addAll(userRolesList);

            // Mettre à jour le côté inverse
            userRolesList.forEach(it -> {
                if (!it.getUsers().contains(existing)) {
                    it.getUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getEnabledFeatureFlags() != null) {
            existing.getEnabledFeatureFlags().clear();

            List<FeatureFlag> enabledFeatureFlagsList = userprofileRequest.getEnabledFeatureFlags().stream()
                .map(item -> enabledFeatureFlagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("FeatureFlag not found")))
                .collect(Collectors.toList());

            existing.getEnabledFeatureFlags().addAll(enabledFeatureFlagsList);

            // Mettre à jour le côté inverse
            enabledFeatureFlagsList.forEach(it -> {
                if (!it.getEnabledForUsers().contains(existing)) {
                    it.getEnabledForUsers().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getMessageThreads() != null) {
            existing.getMessageThreads().clear();

            List<MessageThread> messageThreadsList = userprofileRequest.getMessageThreads().stream()
                .map(item -> messageThreadsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("MessageThread not found")))
                .collect(Collectors.toList());

            existing.getMessageThreads().addAll(messageThreadsList);

            // Mettre à jour le côté inverse
            messageThreadsList.forEach(it -> {
                if (!it.getParticipants().contains(existing)) {
                    it.getParticipants().add(existing);
                }
            });
        }
        
        if (userprofileRequest.getBadges() != null) {
            existing.getBadges().clear();

            List<UserBadge> badgesList = userprofileRequest.getBadges().stream()
                .map(item -> badgesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserBadge not found")))
                .collect(Collectors.toList());

            existing.getBadges().addAll(badgesList);

            // Mettre à jour le côté inverse
            badgesList.forEach(it -> {
                if (!it.getUsers().contains(existing)) {
                    it.getUsers().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getReviews().clear();

        if (userprofileRequest.getReviews() != null) {
            for (var item : userprofileRequest.getReviews()) {
                Review existingItem;
                if (item.getId() != null) {
                    existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getReviews().add(existingItem);
            }
        }
        
        existing.getSubscriptions().clear();

        if (userprofileRequest.getSubscriptions() != null) {
            for (var item : userprofileRequest.getSubscriptions()) {
                Subscription existingItem;
                if (item.getId() != null) {
                    existingItem = subscriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getSubscriptions().add(existingItem);
            }
        }
        
        existing.getForumThreads().clear();

        if (userprofileRequest.getForumThreads() != null) {
            for (var item : userprofileRequest.getForumThreads()) {
                ForumThread existingItem;
                if (item.getId() != null) {
                    existingItem = forumThreadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumThread not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getForumThreads().add(existingItem);
            }
        }
        
        existing.getForumPosts().clear();

        if (userprofileRequest.getForumPosts() != null) {
            for (var item : userprofileRequest.getForumPosts()) {
                ForumPost existingItem;
                if (item.getId() != null) {
                    existingItem = forumPostsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getForumPosts().add(existingItem);
            }
        }
        
        existing.getAchievements().clear();

        if (userprofileRequest.getAchievements() != null) {
            for (var item : userprofileRequest.getAchievements()) {
                Achievement existingItem;
                if (item.getId() != null) {
                    existingItem = achievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Achievement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getAchievements().add(existingItem);
            }
        }
        
        existing.getHostedOnlineEvents().clear();

        if (userprofileRequest.getHostedOnlineEvents() != null) {
            for (var item : userprofileRequest.getHostedOnlineEvents()) {
                OnlineEvent existingItem;
                if (item.getId() != null) {
                    existingItem = hostedOnlineEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setHost(existing);
                existing.getHostedOnlineEvents().add(existingItem);
            }
        }
        
        existing.getPlaylists().clear();

        if (userprofileRequest.getPlaylists() != null) {
            for (var item : userprofileRequest.getPlaylists()) {
                Playlist existingItem;
                if (item.getId() != null) {
                    existingItem = playlistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Playlist not found"));
                } else {
                existingItem = item;
                }

                existingItem.setOwner(existing);
                existing.getPlaylists().add(existingItem);
            }
        }
        
        existing.getDigitalPurchases().clear();

        if (userprofileRequest.getDigitalPurchases() != null) {
            for (var item : userprofileRequest.getDigitalPurchases()) {
                DigitalPurchase existingItem;
                if (item.getId() != null) {
                    existingItem = digitalPurchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getDigitalPurchases().add(existingItem);
            }
        }
        
        existing.getGameSessions().clear();

        if (userprofileRequest.getGameSessions() != null) {
            for (var item : userprofileRequest.getGameSessions()) {
                GameSession existingItem;
                if (item.getId() != null) {
                    existingItem = gameSessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameSession not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGameSessions().add(existingItem);
            }
        }
        
        existing.getGameReviewComments().clear();

        if (userprofileRequest.getGameReviewComments() != null) {
            for (var item : userprofileRequest.getGameReviewComments()) {
                GameReviewComment existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewComment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGameReviewComments().add(existingItem);
            }
        }
        
        existing.getUserPlaylists().clear();

        if (userprofileRequest.getUserPlaylists() != null) {
            for (var item : userprofileRequest.getUserPlaylists()) {
                UserPlaylist existingItem;
                if (item.getId() != null) {
                    existingItem = userPlaylistsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getUserPlaylists().add(existingItem);
            }
        }
        
        existing.getUserPlaylistItems().clear();

        if (userprofileRequest.getUserPlaylistItems() != null) {
            for (var item : userprofileRequest.getUserPlaylistItems()) {
                UserPlaylistItem existingItem;
                if (item.getId() != null) {
                    existingItem = userPlaylistItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPlaylistItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAddedBy(existing);
                existing.getUserPlaylistItems().add(existingItem);
            }
        }
        
        existing.getGivenRatings().clear();

        if (userprofileRequest.getGivenRatings() != null) {
            for (var item : userprofileRequest.getGivenRatings()) {
                ReviewRating existingItem;
                if (item.getId() != null) {
                    existingItem = givenRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGivenRatings().add(existingItem);
            }
        }
        
        existing.getLikedReviews().clear();

        if (userprofileRequest.getLikedReviews() != null) {
            for (var item : userprofileRequest.getLikedReviews()) {
                ReviewLike existingItem;
                if (item.getId() != null) {
                    existingItem = likedReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewLike not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getLikedReviews().add(existingItem);
            }
        }
        
        existing.getActivityLogs().clear();

        if (userprofileRequest.getActivityLogs() != null) {
            for (var item : userprofileRequest.getActivityLogs()) {
                UserActivityLog existingItem;
                if (item.getId() != null) {
                    existingItem = activityLogsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserActivityLog not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getActivityLogs().add(existingItem);
            }
        }
        
        existing.getSettings().clear();

        if (userprofileRequest.getSettings() != null) {
            for (var item : userprofileRequest.getSettings()) {
                UserSetting existingItem;
                if (item.getId() != null) {
                    existingItem = settingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserSetting not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getSettings().add(existingItem);
            }
        }
        
        existing.getFollowers().clear();

        if (userprofileRequest.getFollowers() != null) {
            for (var item : userprofileRequest.getFollowers()) {
                UserFollower existingItem;
                if (item.getId() != null) {
                    existingItem = followersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
                } else {
                existingItem = item;
                }

                existingItem.setFollowed(existing);
                existing.getFollowers().add(existingItem);
            }
        }
        
        existing.getFollowing().clear();

        if (userprofileRequest.getFollowing() != null) {
            for (var item : userprofileRequest.getFollowing()) {
                UserFollower existingItem;
                if (item.getId() != null) {
                    existingItem = followingRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserFollower not found"));
                } else {
                existingItem = item;
                }

                existingItem.setFollower(existing);
                existing.getFollowing().add(existingItem);
            }
        }
        
        existing.getUserAchievements().clear();

        if (userprofileRequest.getUserAchievements() != null) {
            for (var item : userprofileRequest.getUserAchievements()) {
                UserAchievement existingItem;
                if (item.getId() != null) {
                    existingItem = userAchievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getUserAchievements().add(existingItem);
            }
        }
        
        existing.getNotifications().clear();

        if (userprofileRequest.getNotifications() != null) {
            for (var item : userprofileRequest.getNotifications()) {
                Notification existingItem;
                if (item.getId() != null) {
                    existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRecipient(existing);
                existing.getNotifications().add(existingItem);
            }
        }
        
        existing.getMerchandiseReviews().clear();

        if (userprofileRequest.getMerchandiseReviews() != null) {
            for (var item : userprofileRequest.getMerchandiseReviews()) {
                MerchandiseReview existingItem;
                if (item.getId() != null) {
                    existingItem = merchandiseReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getMerchandiseReviews().add(existingItem);
            }
        }
        
        existing.getPreferences().clear();

        if (userprofileRequest.getPreferences() != null) {
            for (var item : userprofileRequest.getPreferences()) {
                UserPreference existingItem;
                if (item.getId() != null) {
                    existingItem = preferencesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserPreference not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getPreferences().add(existingItem);
            }
        }
        
        existing.getMerchandiseSales().clear();

        if (userprofileRequest.getMerchandiseSales() != null) {
            for (var item : userprofileRequest.getMerchandiseSales()) {
                MerchandiseSale existingItem;
                if (item.getId() != null) {
                    existingItem = merchandiseSalesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getMerchandiseSales().add(existingItem);
            }
        }
        
        existing.getGamePlaySessions().clear();

        if (userprofileRequest.getGamePlaySessions() != null) {
            for (var item : userprofileRequest.getGamePlaySessions()) {
                GamePlaySession existingItem;
                if (item.getId() != null) {
                    existingItem = gamePlaySessionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGamePlaySessions().add(existingItem);
            }
        }
        
        existing.getGameReviewUpvotes().clear();

        if (userprofileRequest.getGameReviewUpvotes() != null) {
            for (var item : userprofileRequest.getGameReviewUpvotes()) {
                GameReviewUpvote existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewUpvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGameReviewUpvotes().add(existingItem);
            }
        }
        
        existing.getGameReviewDownvotes().clear();

        if (userprofileRequest.getGameReviewDownvotes() != null) {
            for (var item : userprofileRequest.getGameReviewDownvotes()) {
                GameReviewDownvote existingItem;
                if (item.getId() != null) {
                    existingItem = gameReviewDownvotesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameReviewDownvote not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGameReviewDownvotes().add(existingItem);
            }
        }
        
        existing.getSentMessages().clear();

        if (userprofileRequest.getSentMessages() != null) {
            for (var item : userprofileRequest.getSentMessages()) {
                UserMessage existingItem;
                if (item.getId() != null) {
                    existingItem = sentMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSender(existing);
                existing.getSentMessages().add(existingItem);
            }
        }
        
        existing.getReceivedMessages().clear();

        if (userprofileRequest.getReceivedMessages() != null) {
            for (var item : userprofileRequest.getReceivedMessages()) {
                UserMessage existingItem;
                if (item.getId() != null) {
                    existingItem = receivedMessagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
                } else {
                existingItem = item;
                }

                existingItem.setReceiver(existing);
                existing.getReceivedMessages().add(existingItem);
            }
        }
        
        existing.getHostedLiveStreams().clear();

        if (userprofileRequest.getHostedLiveStreams() != null) {
            for (var item : userprofileRequest.getHostedLiveStreams()) {
                LiveStream existingItem;
                if (item.getId() != null) {
                    existingItem = hostedLiveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStream not found"));
                } else {
                existingItem = item;
                }

                existingItem.setHost(existing);
                existing.getHostedLiveStreams().add(existingItem);
            }
        }
        
        existing.getWatchedLiveStreams().clear();

        if (userprofileRequest.getWatchedLiveStreams() != null) {
            for (var item : userprofileRequest.getWatchedLiveStreams()) {
                LiveStreamViewer existingItem;
                if (item.getId() != null) {
                    existingItem = watchedLiveStreamsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveStreamViewer not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getWatchedLiveStreams().add(existingItem);
            }
        }
        
        existing.getGivenUserRatings().clear();

        if (userprofileRequest.getGivenUserRatings() != null) {
            for (var item : userprofileRequest.getGivenUserRatings()) {
                UserRating existingItem;
                if (item.getId() != null) {
                    existingItem = givenUserRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGivenUserRatings().add(existingItem);
            }
        }
        
        existing.getReceivedUserRatings().clear();

        if (userprofileRequest.getReceivedUserRatings() != null) {
            for (var item : userprofileRequest.getReceivedUserRatings()) {
                UserRating existingItem;
                if (item.getId() != null) {
                    existingItem = receivedUserRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRatedUser(existing);
                existing.getReceivedUserRatings().add(existingItem);
            }
        }
        
        existing.getConnections().clear();

        if (userprofileRequest.getConnections() != null) {
            for (var item : userprofileRequest.getConnections()) {
                UserConnection existingItem;
                if (item.getId() != null) {
                    existingItem = connectionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser1(existing);
                existing.getConnections().add(existingItem);
            }
        }
        
        existing.getConnectedBy().clear();

        if (userprofileRequest.getConnectedBy() != null) {
            for (var item : userprofileRequest.getConnectedBy()) {
                UserConnection existingItem;
                if (item.getId() != null) {
                    existingItem = connectedByRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserConnection not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser2(existing);
                existing.getConnectedBy().add(existingItem);
            }
        }
        
        existing.getBlockedUsers().clear();

        if (userprofileRequest.getBlockedUsers() != null) {
            for (var item : userprofileRequest.getBlockedUsers()) {
                UserBlockedList existingItem;
                if (item.getId() != null) {
                    existingItem = blockedUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserBlockedList not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBlocker(existing);
                existing.getBlockedUsers().add(existingItem);
            }
        }
        
        existing.getBlockedByUsers().clear();

        if (userprofileRequest.getBlockedByUsers() != null) {
            for (var item : userprofileRequest.getBlockedByUsers()) {
                UserBlockedList existingItem;
                if (item.getId() != null) {
                    existingItem = blockedByUsersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserBlockedList not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBlocked(existing);
                existing.getBlockedByUsers().add(existingItem);
            }
        }
        
        existing.getReportedContent().clear();

        if (userprofileRequest.getReportedContent() != null) {
            for (var item : userprofileRequest.getReportedContent()) {
                ReportedContent existingItem;
                if (item.getId() != null) {
                    existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getReportedContent().add(existingItem);
            }
        }
        
        existing.getReviewReplies().clear();

        if (userprofileRequest.getReviewReplies() != null) {
            for (var item : userprofileRequest.getReviewReplies()) {
                ReviewReply existingItem;
                if (item.getId() != null) {
                    existingItem = reviewRepliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReviewReply not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getReviewReplies().add(existingItem);
            }
        }
        
        existing.getEpisodeReviews().clear();

        if (userprofileRequest.getEpisodeReviews() != null) {
            for (var item : userprofileRequest.getEpisodeReviews()) {
                EpisodeReview existingItem;
                if (item.getId() != null) {
                    existingItem = episodeReviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeReview not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getEpisodeReviews().add(existingItem);
            }
        }
        
        existing.getGameTransactions().clear();

        if (userprofileRequest.getGameTransactions() != null) {
            for (var item : userprofileRequest.getGameTransactions()) {
                GameTransaction existingItem;
                if (item.getId() != null) {
                    existingItem = gameTransactionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GameTransaction not found"));
                } else {
                existingItem = item;
                }

                existingItem.setUser(existing);
                existing.getGameTransactions().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (userprofileRequest.getWallet() != null &&userprofileRequest.getWallet().getId() != null) {

        UserWallet wallet = walletRepository.findById(userprofileRequest.getWallet().getId())
                .orElseThrow(() -> new RuntimeException("UserWallet not found"));

        existing.setWallet(wallet);
        wallet.setUser(existing);
        }
    
        if (userprofileRequest.getLoyaltyProgram() != null &&userprofileRequest.getLoyaltyProgram().getId() != null) {

        UserLoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(userprofileRequest.getLoyaltyProgram().getId())
                .orElseThrow(() -> new RuntimeException("UserLoyaltyProgram not found"));

        existing.setLoyaltyProgram(loyaltyProgram);
        loyaltyProgram.setUser(existing);
        
        }
    
        if (userprofileRequest.getModerator() != null &&userprofileRequest.getModerator().getId() != null) {

        ForumModerator moderator = moderatorRepository.findById(userprofileRequest.getModerator().getId())
                .orElseThrow(() -> new RuntimeException("ForumModerator not found"));

        existing.setModerator(moderator);
        moderator.setUser(existing);
        
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
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getReviews().clear();
        }
        
        if (entity.getSubscriptions() != null) {
            for (var child : entity.getSubscriptions()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getSubscriptions().clear();
        }
        
        if (entity.getForumThreads() != null) {
            for (var child : entity.getForumThreads()) {
                // retirer la référence inverse
                child.setAuthor(null);
            }
            entity.getForumThreads().clear();
        }
        
        if (entity.getForumPosts() != null) {
            for (var child : entity.getForumPosts()) {
                // retirer la référence inverse
                child.setAuthor(null);
            }
            entity.getForumPosts().clear();
        }
        
        if (entity.getAchievements() != null) {
            for (var child : entity.getAchievements()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getAchievements().clear();
        }
        
        if (entity.getHostedOnlineEvents() != null) {
            for (var child : entity.getHostedOnlineEvents()) {
                // retirer la référence inverse
                child.setHost(null);
            }
            entity.getHostedOnlineEvents().clear();
        }
        
        if (entity.getPlaylists() != null) {
            for (var child : entity.getPlaylists()) {
                // retirer la référence inverse
                child.setOwner(null);
            }
            entity.getPlaylists().clear();
        }
        
        if (entity.getDigitalPurchases() != null) {
            for (var child : entity.getDigitalPurchases()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getDigitalPurchases().clear();
        }
        
        if (entity.getGameSessions() != null) {
            for (var child : entity.getGameSessions()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGameSessions().clear();
        }
        
        if (entity.getGameReviewComments() != null) {
            for (var child : entity.getGameReviewComments()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGameReviewComments().clear();
        }
        
        if (entity.getUserPlaylists() != null) {
            for (var child : entity.getUserPlaylists()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getUserPlaylists().clear();
        }
        
        if (entity.getUserPlaylistItems() != null) {
            for (var child : entity.getUserPlaylistItems()) {
                // retirer la référence inverse
                child.setAddedBy(null);
            }
            entity.getUserPlaylistItems().clear();
        }
        
        if (entity.getGivenRatings() != null) {
            for (var child : entity.getGivenRatings()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGivenRatings().clear();
        }
        
        if (entity.getLikedReviews() != null) {
            for (var child : entity.getLikedReviews()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getLikedReviews().clear();
        }
        
        if (entity.getActivityLogs() != null) {
            for (var child : entity.getActivityLogs()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getActivityLogs().clear();
        }
        
        if (entity.getSettings() != null) {
            for (var child : entity.getSettings()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getSettings().clear();
        }
        
        if (entity.getFollowers() != null) {
            for (var child : entity.getFollowers()) {
                // retirer la référence inverse
                child.setFollowed(null);
            }
            entity.getFollowers().clear();
        }
        
        if (entity.getFollowing() != null) {
            for (var child : entity.getFollowing()) {
                // retirer la référence inverse
                child.setFollower(null);
            }
            entity.getFollowing().clear();
        }
        
        if (entity.getUserAchievements() != null) {
            for (var child : entity.getUserAchievements()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getUserAchievements().clear();
        }
        
        if (entity.getNotifications() != null) {
            for (var child : entity.getNotifications()) {
                // retirer la référence inverse
                child.setRecipient(null);
            }
            entity.getNotifications().clear();
        }
        
        if (entity.getMerchandiseReviews() != null) {
            for (var child : entity.getMerchandiseReviews()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getMerchandiseReviews().clear();
        }
        
        if (entity.getPreferences() != null) {
            for (var child : entity.getPreferences()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getPreferences().clear();
        }
        
        if (entity.getMerchandiseSales() != null) {
            for (var child : entity.getMerchandiseSales()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getMerchandiseSales().clear();
        }
        
        if (entity.getGamePlaySessions() != null) {
            for (var child : entity.getGamePlaySessions()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGamePlaySessions().clear();
        }
        
        if (entity.getGameReviewUpvotes() != null) {
            for (var child : entity.getGameReviewUpvotes()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGameReviewUpvotes().clear();
        }
        
        if (entity.getGameReviewDownvotes() != null) {
            for (var child : entity.getGameReviewDownvotes()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGameReviewDownvotes().clear();
        }
        
        if (entity.getSentMessages() != null) {
            for (var child : entity.getSentMessages()) {
                // retirer la référence inverse
                child.setSender(null);
            }
            entity.getSentMessages().clear();
        }
        
        if (entity.getReceivedMessages() != null) {
            for (var child : entity.getReceivedMessages()) {
                // retirer la référence inverse
                child.setReceiver(null);
            }
            entity.getReceivedMessages().clear();
        }
        
        if (entity.getHostedLiveStreams() != null) {
            for (var child : entity.getHostedLiveStreams()) {
                // retirer la référence inverse
                child.setHost(null);
            }
            entity.getHostedLiveStreams().clear();
        }
        
        if (entity.getWatchedLiveStreams() != null) {
            for (var child : entity.getWatchedLiveStreams()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getWatchedLiveStreams().clear();
        }
        
        if (entity.getGivenUserRatings() != null) {
            for (var child : entity.getGivenUserRatings()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGivenUserRatings().clear();
        }
        
        if (entity.getReceivedUserRatings() != null) {
            for (var child : entity.getReceivedUserRatings()) {
                // retirer la référence inverse
                child.setRatedUser(null);
            }
            entity.getReceivedUserRatings().clear();
        }
        
        if (entity.getConnections() != null) {
            for (var child : entity.getConnections()) {
                // retirer la référence inverse
                child.setUser1(null);
            }
            entity.getConnections().clear();
        }
        
        if (entity.getConnectedBy() != null) {
            for (var child : entity.getConnectedBy()) {
                // retirer la référence inverse
                child.setUser2(null);
            }
            entity.getConnectedBy().clear();
        }
        
        if (entity.getBlockedUsers() != null) {
            for (var child : entity.getBlockedUsers()) {
                // retirer la référence inverse
                child.setBlocker(null);
            }
            entity.getBlockedUsers().clear();
        }
        
        if (entity.getBlockedByUsers() != null) {
            for (var child : entity.getBlockedByUsers()) {
                // retirer la référence inverse
                child.setBlocked(null);
            }
            entity.getBlockedByUsers().clear();
        }
        
        if (entity.getReportedContent() != null) {
            for (var child : entity.getReportedContent()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getReportedContent().clear();
        }
        
        if (entity.getReviewReplies() != null) {
            for (var child : entity.getReviewReplies()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getReviewReplies().clear();
        }
        
        if (entity.getEpisodeReviews() != null) {
            for (var child : entity.getEpisodeReviews()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getEpisodeReviews().clear();
        }
        
        if (entity.getGameTransactions() != null) {
            for (var child : entity.getGameTransactions()) {
                // retirer la référence inverse
                child.setUser(null);
            }
            entity.getGameTransactions().clear();
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
        
        if (entity.getUserRoles() != null) {
            for (UserRole item : new ArrayList<>(entity.getUserRoles())) {
                
                item.getUsers().remove(entity); // retire côté inverse
            }
            entity.getUserRoles().clear(); // puis vide côté courant
        }
        
        if (entity.getEnabledFeatureFlags() != null) {
            for (FeatureFlag item : new ArrayList<>(entity.getEnabledFeatureFlags())) {
                
                item.getEnabledForUsers().remove(entity); // retire côté inverse
            }
            entity.getEnabledFeatureFlags().clear(); // puis vide côté courant
        }
        
        if (entity.getMessageThreads() != null) {
            for (MessageThread item : new ArrayList<>(entity.getMessageThreads())) {
                
                item.getParticipants().remove(entity); // retire côté inverse
            }
            entity.getMessageThreads().clear(); // puis vide côté courant
        }
        
        if (entity.getBadges() != null) {
            for (UserBadge item : new ArrayList<>(entity.getBadges())) {
                
                item.getUsers().remove(entity); // retire côté inverse
            }
            entity.getBadges().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getWallet() != null) {
            entity.getWallet().setUser(null);
            entity.setWallet(null);
        }
        
        if (entity.getLoyaltyProgram() != null) {
            entity.getLoyaltyProgram().setUser(null);
            entity.setLoyaltyProgram(null);
        }
        
        if (entity.getModerator() != null) {
            entity.getModerator().setUser(null);
            entity.setModerator(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getUserLevel() != null) {
            entity.setUserLevel(null);
        }
        
        if (entity.getCountry() != null) {
            entity.setCountry(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<UserProfile> saveAll(List<UserProfile> userprofileList) {

        return userprofileRepository.saveAll(userprofileList);
    }

}