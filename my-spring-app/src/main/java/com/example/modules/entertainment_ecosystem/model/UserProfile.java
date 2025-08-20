package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Subscription;import com.example.modules.entertainment_ecosystem.model.Episode;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.Achievement;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.Podcast;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.Playlist;import com.example.modules.entertainment_ecosystem.model.UserWallet;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;import com.example.modules.entertainment_ecosystem.model.GameSession;import com.example.modules.entertainment_ecosystem.model.GameReviewComment;import com.example.modules.entertainment_ecosystem.model.UserPlaylist;import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;import com.example.modules.entertainment_ecosystem.model.ReviewRating;import com.example.modules.entertainment_ecosystem.model.ReviewLike;import com.example.modules.entertainment_ecosystem.model.UserActivityLog;import com.example.modules.entertainment_ecosystem.model.UserSetting;import com.example.modules.entertainment_ecosystem.model.UserRole;import com.example.modules.entertainment_ecosystem.model.UserFollower;import com.example.modules.entertainment_ecosystem.model.UserFollower;import com.example.modules.entertainment_ecosystem.model.UserAchievement;import com.example.modules.entertainment_ecosystem.model.Notification;import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;import com.example.modules.entertainment_ecosystem.model.UserPreference;import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;import com.example.modules.entertainment_ecosystem.model.GamePlaySession;import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;import com.example.modules.entertainment_ecosystem.model.UserMessage;import com.example.modules.entertainment_ecosystem.model.UserMessage;import com.example.modules.entertainment_ecosystem.model.FeatureFlag;import com.example.modules.entertainment_ecosystem.model.MessageThread;import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;import com.example.modules.entertainment_ecosystem.model.LiveStream;import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;import com.example.modules.entertainment_ecosystem.model.UserBadge;import com.example.modules.entertainment_ecosystem.model.UserLevel;import com.example.modules.entertainment_ecosystem.model.UserRating;import com.example.modules.entertainment_ecosystem.model.UserRating;import com.example.modules.entertainment_ecosystem.model.ForumModerator;import com.example.modules.entertainment_ecosystem.model.UserConnection;import com.example.modules.entertainment_ecosystem.model.UserConnection;import com.example.modules.entertainment_ecosystem.model.UserBlockedList;import com.example.modules.entertainment_ecosystem.model.UserBlockedList;import com.example.modules.entertainment_ecosystem.model.ReportedContent;import com.example.modules.entertainment_ecosystem.model.ReviewReply;import com.example.modules.entertainment_ecosystem.model.EpisodeReview;import com.example.modules.entertainment_ecosystem.model.GameTransaction;import com.example.modules.entertainment_ecosystem.model.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "userprofile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserProfile extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 3, max = 50)
    private String username;

        @NotNull@Email
    private String email;

        @NotNull@Size(min = 8, max = 255)
    private String password;

        @NotNull
    private Date registrationDate;

        @Size(max = 255)
    private String profilePictureUrl;

    private Date lastActiveDate;

        
    private String userStatus;

        @Size(max = 500)
    private String bio;


// === Relations ===

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<Review> reviews;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_movie_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("watchlistUsers")
            private List<Movie> watchlistMovies;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_favorite_artists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("favoriteArtists")
            private List<Artist> favoriteArtists;
            
    @ManyToMany(mappedBy = "followingUsers", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("followingUsers")
            private List<UserProfile> followedUsers = new ArrayList<>();
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
            @JsonIgnoreProperties("followedUsers")
            private List<UserProfile> followingUsers;
            
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "user_favorite_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("favoriteUsers")
            private List<Genre> favoriteGenres;
            
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<Subscription> subscriptions;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_watched_episodes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
            @JsonIgnoreProperties("watchedByUsers")
            private List<Episode> watchedEpisodes;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
            @JsonIgnoreProperties("playedBy")
            private List<VideoGame> playedGames;
            
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("author")
        private List<ForumThread> forumThreads;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("author")
        private List<ForumPost> forumPosts;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<Achievement> achievements;
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("host")
        private List<OnlineEvent> hostedOnlineEvents;
    
    @ManyToMany(mappedBy = "attendees", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("attendees")
            private List<OnlineEvent> attendedOnlineEvents = new ArrayList<>();
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_merchandise",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id"))
            @JsonIgnoreProperties("ownedByUsers")
            private List<Merchandise> ownedMerchandise;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_podcast_library",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id"))
            @JsonIgnoreProperties("listeners")
            private List<Podcast> libraryPodcasts;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_music_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
            @JsonIgnoreProperties("listenedByUsers")
            private List<MusicTrack> listenedMusic;
            
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("owner")
        private List<Playlist> playlists;
    
    @OneToOne
            @JoinColumn(name = "wallet_id")
            @JsonIgnoreProperties("user")
            private UserWallet wallet;
            
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<DigitalPurchase> digitalPurchases;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GameSession> gameSessions;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GameReviewComment> gameReviewComments;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<UserPlaylist> userPlaylists;
    
    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("addedBy")
        private List<UserPlaylistItem> userPlaylistItems;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<ReviewRating> givenRatings;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<ReviewLike> likedReviews;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
        @JsonIgnoreProperties("user")
        private List<UserActivityLog> activityLogs;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<UserSetting> settings;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
            @JsonIgnoreProperties("users")
            private List<UserRole> userRoles;
            
    @OneToMany(mappedBy = "followed", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("followed")
        private List<UserFollower> followers;
    
    @OneToMany(mappedBy = "follower", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("follower")
        private List<UserFollower> following;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<UserAchievement> userAchievements;
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
        @JsonIgnoreProperties("recipient")
        private List<Notification> notifications;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<MerchandiseReview> merchandiseReviews;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<UserPreference> preferences;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<MerchandiseSale> merchandiseSales;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GamePlaySession> gamePlaySessions;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GameReviewUpvote> gameReviewUpvotes;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GameReviewDownvote> gameReviewDownvotes;
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("sender")
        private List<UserMessage> sentMessages;
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("receiver")
        private List<UserMessage> receivedMessages;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_feature_flags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_flag_id"))
            @JsonIgnoreProperties("enabledForUsers")
            private List<FeatureFlag> enabledFeatureFlags;
            
    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("participants")
            private List<MessageThread> messageThreads = new ArrayList<>();
        
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("user")
            private UserLoyaltyProgram loyaltyProgram;
        
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("host")
        private List<LiveStream> hostedLiveStreams;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<LiveStreamViewer> watchedLiveStreams;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_badges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id"))
            @JsonIgnoreProperties("users")
            private List<UserBadge> badges;
            
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_level_id")
        @JsonIgnoreProperties("users")
        private UserLevel userLevel;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<UserRating> givenUserRatings;
    
    @OneToMany(mappedBy = "ratedUser", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("ratedUser")
        private List<UserRating> receivedUserRatings;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("user")
            private ForumModerator moderator;
        
    @OneToMany(mappedBy = "user1", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user1")
        private List<UserConnection> connections;
    
    @OneToMany(mappedBy = "user2", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user2")
        private List<UserConnection> connectedBy;
    
    @OneToMany(mappedBy = "blocker", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("blocker")
        private List<UserBlockedList> blockedUsers;
    
    @OneToMany(mappedBy = "blocked", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("blocked")
        private List<UserBlockedList> blockedByUsers;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<ReportedContent> reportedContent;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<ReviewReply> reviewReplies;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<EpisodeReview> episodeReviews;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("user")
        private List<GameTransaction> gameTransactions;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "country_id")
        @JsonIgnoreProperties("userProfiles")
        private Country country;
    

}