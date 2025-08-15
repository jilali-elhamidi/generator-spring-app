package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.GameReviewComment;import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "gamereview_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GameReview extends BaseEntity {

// === Attributs simples ===

    @NotNull@Min(1)@Max(10)
    private Integer rating;

    @Size(max = 500)
    private String comment;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "game_id")
        
        private VideoGame game;
    
    
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewComment> comments;
    
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewUpvote> upvotes;
    
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewDownvote> downvotes;
    

}