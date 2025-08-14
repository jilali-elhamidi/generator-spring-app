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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "digitalpurchase_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DigitalPurchase extends BaseEntity {

// === Attributs simples ===

    @NotNull
    private Date purchaseDate;

    @NotNull
    private Double price;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        
        private UserProfile user;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "movie_id")
        
        private Movie movie;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "musicTrack_id")
        
        private MusicTrack musicTrack;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "videoGame_id")
        
        private VideoGame videoGame;
    
    
    
    @OneToOne
    @JoinColumn(name = "transaction_id")
    @JsonIgnoreProperties("digitalPurchase")
    private Transaction transaction;
            

}