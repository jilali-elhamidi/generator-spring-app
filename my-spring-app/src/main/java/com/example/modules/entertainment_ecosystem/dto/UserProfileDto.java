package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AchievementSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameSessionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistItemSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewLikeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserActivityLogSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserSettingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserRoleSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowerSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowerSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserAchievementSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.NotificationSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserPreferenceSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSaleSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.FeatureFlagSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MessageThreadSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserLoyaltyProgramSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamViewerSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserBadgeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserLevelSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumModeratorSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserBlockedListSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserBlockedListSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReportedContentSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewReplySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameTransactionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.CountrySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Date registrationDate;

    private String profilePictureUrl;

    private Date lastActiveDate;

    private String userStatus;

    private String bio;

    private List<ReviewSimpleDto> reviews;

    private List<MovieSimpleDto> watchlistMovies;

    private List<ArtistSimpleDto> favoriteArtists;

    private List<UserProfileSimpleDto> followedUsers;

    private List<UserProfileSimpleDto> followingUsers;

    private List<GenreSimpleDto> favoriteGenres;

    private List<SubscriptionSimpleDto> subscriptions;

    private List<EpisodeSimpleDto> watchedEpisodes;

    private List<VideoGameSimpleDto> playedGames;

    private List<ForumThreadSimpleDto> forumThreads;

    private List<ForumPostSimpleDto> forumPosts;

    private List<AchievementSimpleDto> achievements;

    private List<OnlineEventSimpleDto> hostedOnlineEvents;

    private List<OnlineEventSimpleDto> attendedOnlineEvents;

    private List<MerchandiseSimpleDto> ownedMerchandise;

    private List<PodcastSimpleDto> libraryPodcasts;

    private List<MusicTrackSimpleDto> listenedMusic;

    private List<PlaylistSimpleDto> playlists;

    private UserWalletSimpleDto wallet;

    private List<DigitalPurchaseSimpleDto> digitalPurchases;

    private List<GameSessionSimpleDto> gameSessions;

    private List<GameReviewCommentSimpleDto> gameReviewComments;

    private List<UserPlaylistSimpleDto> userPlaylists;

    private List<UserPlaylistItemSimpleDto> userPlaylistItems;

    private List<ReviewRatingSimpleDto> givenRatings;

    private List<ReviewLikeSimpleDto> likedReviews;

    private List<UserActivityLogSimpleDto> activityLogs;

    private List<UserSettingSimpleDto> settings;

    private List<UserRoleSimpleDto> userRoles;

    private List<UserFollowerSimpleDto> followers;

    private List<UserFollowerSimpleDto> following;

    private List<UserAchievementSimpleDto> userAchievements;

    private List<NotificationSimpleDto> notifications;

    private List<MerchandiseReviewSimpleDto> merchandiseReviews;

    private List<UserPreferenceSimpleDto> preferences;

    private List<MerchandiseSaleSimpleDto> merchandiseSales;

    private List<GamePlaySessionSimpleDto> gamePlaySessions;

    private List<GameReviewUpvoteSimpleDto> gameReviewUpvotes;

    private List<GameReviewDownvoteSimpleDto> gameReviewDownvotes;

    private List<UserMessageSimpleDto> sentMessages;

    private List<UserMessageSimpleDto> receivedMessages;

    private List<FeatureFlagSimpleDto> enabledFeatureFlags;

    private List<MessageThreadSimpleDto> messageThreads;

    private UserLoyaltyProgramSimpleDto loyaltyProgram;

    private List<LiveStreamSimpleDto> hostedLiveStreams;

    private List<LiveStreamViewerSimpleDto> watchedLiveStreams;

    private List<UserBadgeSimpleDto> badges;

    private UserLevelSimpleDto userLevel;

    private List<UserRatingSimpleDto> givenUserRatings;

    private List<UserRatingSimpleDto> receivedUserRatings;

    private ForumModeratorSimpleDto moderator;

    private List<UserConnectionSimpleDto> connections;

    private List<UserConnectionSimpleDto> connectedBy;

    private List<UserBlockedListSimpleDto> blockedUsers;

    private List<UserBlockedListSimpleDto> blockedByUsers;

    private List<ReportedContentSimpleDto> reportedContent;

    private List<ReviewReplySimpleDto> reviewReplies;

    private List<EpisodeReviewSimpleDto> episodeReviews;

    private List<GameTransactionSimpleDto> gameTransactions;

    private CountrySimpleDto country;

}