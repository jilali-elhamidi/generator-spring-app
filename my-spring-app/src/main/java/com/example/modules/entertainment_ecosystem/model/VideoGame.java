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
@Table(name = "videogame_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VideoGame extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
        @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    private Date releaseDate;

    private Artist developer;

    private Publisher publisher;

    @NotNull
    private String platform;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "studio_id")
    @JsonIgnoreProperties("games")
    private DevelopmentStudio developerStudio;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "content_rating_id")
    @JsonIgnoreProperties("ratedVideoGames")
    private ContentRating contentRating;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rating_board_id")
    @JsonIgnoreProperties("ratedVideoGames")
    private ContentRatingBoard contentRatingBoard;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "videoGame", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("videoGame")
    private List<Review> generalReviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReview> gameReviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameAchievement> achievements = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameSession> sessions = new ArrayList<>();
    
    @OneToMany(mappedBy = "videoGame", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("videoGame")
    private List<DigitalPurchase> purchases = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<VideoGameRating> ratings = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GamePlaySession> gamePlaySessions = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReviewUpvote> gameReviewUpvotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReviewDownvote> gameReviewDownvotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "baseGame", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("baseGame")
    private List<GameExpansionPack> expansionPacks = new ArrayList<>();
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<LiveStream> liveStreams = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "video_game_genres",
        joinColumns = @JoinColumn(name = "video_game_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("videoGames")
    private List<Genre> genres = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "playedGames", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("playedGames")
    private List<UserProfile> playedBy = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "video_game_platforms",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "platform_id"))
    @JsonIgnoreProperties("videoGames")
    private List<GamePlatform> platforms = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "videogame_tags",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    @JsonIgnoreProperties("videoGames")
    private List<ContentTag> tags = new ArrayList<>();
    
    
}
