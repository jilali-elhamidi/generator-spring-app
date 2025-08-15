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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "review_id")
        
        private GameReview review;
    
    
    
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<GameReviewComment> replies;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "parentComment_id")
        
        private GameReviewComment parentComment;
    
    

}