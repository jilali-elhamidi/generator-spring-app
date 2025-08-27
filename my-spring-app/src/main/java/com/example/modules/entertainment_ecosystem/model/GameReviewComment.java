package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gamereviewcomment_tbl")
public class GameReviewComment extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true, nullable = false)
    private String commentText;

    @NotNull
    private Date commentDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("gameReviewComments")
    private UserProfile user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "review_id")
    @JsonIgnoreProperties("comments")
    private GameReview review;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnoreProperties("replies")
    private GameReviewComment parentComment;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parentComment")
    private List<GameReviewComment> replies = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
