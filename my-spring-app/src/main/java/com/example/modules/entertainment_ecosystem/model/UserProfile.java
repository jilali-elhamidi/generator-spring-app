package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "userprofile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserProfile extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 50)
        @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 255)
    private String password;

    @NotNull
    private Date registrationDate;

    @Size(max = 255)
    private String profilePictureUrl;

    private Date lastActiveDate;

    private String userStatus;

    @Size(max = 500)
    private String bio;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_level_id")
    @JsonIgnoreProperties("users")
    private UserLevel userLevel;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties("userProfiles")
    private Country country;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Review> reviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Subscription> subscriptions = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<ForumThread> forumThreads = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<ForumPost> forumPosts = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Achievement> achievements = new ArrayList<>();
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("host")
    private List<OnlineEvent> hostedOnlineEvents = new ArrayList<>();
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("owner")
    private List<Playlist> playlists = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<DigitalPurchase> digitalPurchases = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GameSession> gameSessions = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GameReviewComment> gameReviewComments = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<UserPlaylist> userPlaylists = new ArrayList<>();
    
    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("addedBy")
    private List<UserPlaylistItem> userPlaylistItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<ReviewRating> givenRatings = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<ReviewLike> likedReviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private List<UserActivityLog> activityLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<UserSetting> settings = new ArrayList<>();
    
    @OneToMany(mappedBy = "followed", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("followed")
    private List<UserFollower> followers = new ArrayList<>();
    
    @OneToMany(mappedBy = "follower", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("follower")
    private List<UserFollower> following = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<UserAchievement> userAchievements = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("recipient")
    private List<Notification> notifications = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<MerchandiseReview> merchandiseReviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<UserPreference> preferences = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<MerchandiseSale> merchandiseSales = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GamePlaySession> gamePlaySessions = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GameReviewUpvote> gameReviewUpvotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GameReviewDownvote> gameReviewDownvotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sender")
    private List<UserMessage> sentMessages = new ArrayList<>();
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("receiver")
    private List<UserMessage> receivedMessages = new ArrayList<>();
    
    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("host")
    private List<LiveStream> hostedLiveStreams = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<LiveStreamViewer> watchedLiveStreams = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<UserRating> givenUserRatings = new ArrayList<>();
    
    @OneToMany(mappedBy = "ratedUser", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("ratedUser")
    private List<UserRating> receivedUserRatings = new ArrayList<>();
    
    @OneToMany(mappedBy = "user1", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user1")
    private List<UserConnection> connections = new ArrayList<>();
    
    @OneToMany(mappedBy = "user2", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user2")
    private List<UserConnection> connectedBy = new ArrayList<>();
    
    @OneToMany(mappedBy = "blocker", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blocker")
    private List<UserBlockedList> blockedUsers = new ArrayList<>();
    
    @OneToMany(mappedBy = "blocked", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blocked")
    private List<UserBlockedList> blockedByUsers = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<ReportedContent> reportedContent = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<ReviewReply> reviewReplies = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<EpisodeReview> episodeReviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<GameTransaction> gameTransactions = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties("user")
    private UserWallet wallet;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private UserLoyaltyProgram loyaltyProgram;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private ForumModerator moderator;

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_movie_watchlist",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    @JsonIgnoreProperties("watchlistUsers")
    private List<Movie> watchlistMovies = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favorite_artists",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @JsonIgnoreProperties("favoriteArtists")
    private List<Artist> favoriteArtists = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "followingUsers", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("followingUsers")
    private List<UserProfile> followedUsers = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_followers",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "followed_id"))
    @JsonIgnoreProperties("followedUsers")
    private List<UserProfile> followingUsers = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_favorite_genres",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("favoriteUsers")
    private List<Genre> favoriteGenres = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_watched_episodes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "episode_id"))
    @JsonIgnoreProperties("watchedByUsers")
    private List<Episode> watchedEpisodes = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_games",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    @JsonIgnoreProperties("playedBy")
    private List<VideoGame> playedGames = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "attendees", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("attendees")
    private List<OnlineEvent> attendedOnlineEvents = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_merchandise",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "merchandise_id"))
    @JsonIgnoreProperties("ownedByUsers")
    private List<Merchandise> ownedMerchandise = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_podcast_library",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "podcast_id"))
    @JsonIgnoreProperties("listeners")
    private List<Podcast> libraryPodcasts = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_music_history",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "track_id"))
    @JsonIgnoreProperties("listenedByUsers")
    private List<MusicTrack> listenedMusic = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("users")
    private List<UserRole> userRoles = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_feature_flags",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_flag_id"))
    @JsonIgnoreProperties("enabledForUsers")
    private List<FeatureFlag> enabledFeatureFlags = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("participants")
    private List<MessageThread> messageThreads = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_badges",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "badge_id"))
    @JsonIgnoreProperties("users")
    private List<UserBadge> badges = new ArrayList<>();
    
    
}
