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
import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.GameReview;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.GameAchievement;import com.example.modules.entertainment_ecosystem.model.GameSession;import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;import com.example.modules.entertainment_ecosystem.model.GamePlatform;import com.example.modules.entertainment_ecosystem.model.ContentRating;import com.example.modules.entertainment_ecosystem.model.ContentTag;import com.example.modules.entertainment_ecosystem.model.VideoGameRating;import com.example.modules.entertainment_ecosystem.model.GamePlaySession;import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "videogame_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VideoGame extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @NotNull
    private Date releaseDate;

    private Artist developer;

    private Publisher publisher;

    @NotNull
    private String platform;


// === Relations ===

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "video_game_genres",
            joinColumns = @JoinColumn(name = "video_game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
            @JsonIgnoreProperties("")
            private List<Genre> genres;
            
    @OneToMany(mappedBy = "videoGame", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("videoGame")
    private List<Review> generalReviews;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReview> gameReviews;
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> playedBy;
        
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameAchievement> achievements;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameSession> sessions;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "studio_id")
    @JsonIgnoreProperties("games")
    private DevelopmentStudio developerStudio;
    
    @OneToMany(mappedBy = "videoGame", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("videoGame")
    private List<DigitalPurchase> purchases;
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "video_game_platforms",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
            @JsonIgnoreProperties("")
            private List<GamePlatform> platforms;
            
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "content_rating_id")
    @JsonIgnoreProperties("ratedVideoGames")
    private ContentRating contentRating;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "videogame_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
            @JsonIgnoreProperties("")
            private List<ContentTag> tags;
            
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<VideoGameRating> ratings;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GamePlaySession> gamePlaySessions;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReviewUpvote> gameReviewUpvotes;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game")
    private List<GameReviewDownvote> gameReviewDownvotes;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rating_board_id")
    @JsonIgnoreProperties("ratedVideoGames")
    private ContentRatingBoard contentRatingBoard;
    

}