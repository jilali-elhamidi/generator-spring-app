package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gamereview_tbl")
public class GameReview extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Min(1)
    @Max(10)
    @Column(unique = true, nullable = false)
    private Integer rating;

    @Size(max = 500)
    private String comment;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("gameReviews")
    private UserProfile user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties("gameReviews")
    private VideoGame game;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("review")
    private List<GameReviewComment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("review")
    private List<GameReviewUpvote> upvotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("review")
    private List<GameReviewDownvote> downvotes = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
