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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.Book;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.ReviewComment;import com.example.modules.entertainment_ecosystem.model.MediaFile;import com.example.modules.entertainment_ecosystem.model.ReviewRating;import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "review_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Review extends BaseEntity {

// === Attributs simples ===

    @NotNull@Min(1)@Max(10)
    private Integer rating;

    @Size(max = 500)
    private String comment;

    @NotNull
    private Date reviewDate;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "movie_id")
        
        private Movie movie;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "book_id")
        
        private Book book;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "videoGame_id")
        
        private VideoGame videoGame;
    
    
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ReviewComment> reviewComments;
    
    
    @OneToOne
    @JoinColumn(name = "media_file_id")
    @JsonIgnoreProperties("review")
    private MediaFile mediaFile;
            
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ReviewRating> ratings;
    
    
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ReviewLike> likes;
    

}