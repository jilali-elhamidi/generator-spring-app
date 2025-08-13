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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.GameReview;import com.example.modules.entertainment_ecosystem.model.GameReviewComment;import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "gamereviewcomment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GameReviewComment extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 255)
    private String commentText;

    @NotNull
    private Date commentDate;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("gameReviewComments")
    private UserProfile user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "review_id")
    @JsonIgnoreProperties("comments")
    private GameReview review;
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parentComment")
    private List<GameReviewComment> replies;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnoreProperties("replies")
    private GameReviewComment parentComment;
    

}