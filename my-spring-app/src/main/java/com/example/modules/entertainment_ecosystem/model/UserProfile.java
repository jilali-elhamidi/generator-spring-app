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
import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Subscription;import com.example.modules.entertainment_ecosystem.model.Episode;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.Achievement;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.OnlineEvent;import com.example.modules.entertainment_ecosystem.model.Merchandise;import com.example.modules.entertainment_ecosystem.model.Podcast;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.Playlist;import com.example.modules.entertainment_ecosystem.model.UserWallet;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;import com.example.modules.entertainment_ecosystem.model.GameSession;import com.example.modules.entertainment_ecosystem.model.GameReviewComment;import com.example.modules.entertainment_ecosystem.model.UserPlaylist;import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;import com.example.modules.entertainment_ecosystem.model.ReviewRating;import com.example.modules.entertainment_ecosystem.model.ReviewLike;import com.example.modules.entertainment_ecosystem.model.UserActivityLog;import com.example.modules.entertainment_ecosystem.model.UserSetting;import com.example.modules.entertainment_ecosystem.model.UserFollower;import com.example.modules.entertainment_ecosystem.model.UserFollower;import com.example.modules.entertainment_ecosystem.model.UserAchievement;import com.example.modules.entertainment_ecosystem.model.Notification;import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;import com.example.modules.entertainment_ecosystem.model.UserPreference;import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;import com.example.modules.entertainment_ecosystem.model.GamePlaySession;import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;import com.example.modules.entertainment_ecosystem.model.UserMessage;import com.example.modules.entertainment_ecosystem.model.UserMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Size(max = 50)
    private String country;

    @Size(max = 255)
    private String profilePictureUrl;

    private Date lastActiveDate;

    
    private String userStatus;

    @Size(max = 500)
    private String bio;


// === Relations ===

    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Review> reviews;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_movie_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("")
            private List<Movie> watchlistMovies;
            
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_favorite_artists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("")
            private List<Artist> favoriteArtists;
            
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> followedUsers;
        
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
            @JsonIgnoreProperties("")
            private List<UserProfile> followingUsers;
            
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "user_favorite_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("")
            private List<Genre> favoriteGenres;
            
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Subscription> subscriptions;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_watched_episodes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
            @JsonIgnoreProperties("")
            private List<Episode> watchedEpisodes;
            
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
            @JsonIgnoreProperties("")
            private List<VideoGame> playedGames;
            
    
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumThread> forumThreads;
    
    
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumPost> forumPosts;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Achievement> achievements;
    
    
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<OnlineEvent> hostedOnlineEvents;
    
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<OnlineEvent> attendedOnlineEvents;
        
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_merchandise",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id"))
            @JsonIgnoreProperties("")
            private List<Merchandise> ownedMerchandise;
            
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_podcast_library",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id"))
            @JsonIgnoreProperties("")
            private List<Podcast> libraryPodcasts;
            
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "user_music_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
            @JsonIgnoreProperties("")
            private List<MusicTrack> listenedMusic;
            
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Playlist> playlists;
    
    
    @OneToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties("user")
    private UserWallet wallet;
            
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<DigitalPurchase> digitalPurchases;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameSession> gameSessions;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewComment> gameReviewComments;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserPlaylist> userPlaylists;
    
    
    @OneToMany(mappedBy = "addedBy", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserPlaylistItem> userPlaylistItems;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ReviewRating> givenRatings;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ReviewLike> likedReviews;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<UserActivityLog> activityLogs;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserSetting> settings;
    
    
    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserFollower> followers;
    
    
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserFollower> following;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserAchievement> userAchievements;
    
    
    @OneToMany(mappedBy = "recipient", fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<Notification> notifications;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<MerchandiseReview> merchandiseReviews;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserPreference> preferences;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<MerchandiseSale> merchandiseSales;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GamePlaySession> gamePlaySessions;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewUpvote> gameReviewUpvotes;
    
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewDownvote> gameReviewDownvotes;
    
    
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserMessage> sentMessages;
    
    
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<UserMessage> receivedMessages;
    

}