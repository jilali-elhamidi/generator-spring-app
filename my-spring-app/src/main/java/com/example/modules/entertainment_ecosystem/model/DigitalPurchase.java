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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.MusicTrack;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.Transaction;import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("digitalPurchases")
        private UserProfile user;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "movie_id")
        @JsonIgnoreProperties("purchases")
        private Movie movie;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "track_id")
        @JsonIgnoreProperties("purchases")
        private MusicTrack musicTrack;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "game_id")
        @JsonIgnoreProperties("purchases")
        private VideoGame videoGame;
    
    @OneToOne
            @JoinColumn(name = "transaction_id")
            @JsonIgnoreProperties("digitalPurchase")
            private Transaction transaction;
            
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "expansion_pack_id")
        @JsonIgnoreProperties("purchases")
        private GameExpansionPack expansionPack;
    

}