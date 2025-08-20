package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.MerchandiseType;import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "merchandise_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Merchandise extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @Size(max = 500)
    private String description;

        @NotNull
    private Double price;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "artist_id")
        @JsonIgnoreProperties("managedMerchandise")
        private Artist artist;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "merchandise_movies",
            joinColumns = @JoinColumn(name = "merchandise_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("relatedMerchandise")
            private List<Movie> relatedMovies;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "merchandise_shows",
            joinColumns = @JoinColumn(name = "merchandise_id"),
            inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
            @JsonIgnoreProperties("relatedMerchandise")
            private List<TVShow> relatedShows;
            
    @ManyToMany(mappedBy = "ownedMerchandise", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("ownedMerchandise")
            private List<UserProfile> ownedByUsers = new ArrayList<>();
        
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "type_id")
        @JsonIgnoreProperties("items")
        private MerchandiseType productType;
    
    @OneToOne(mappedBy = "merchandiseItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("merchandiseItem")
            private MerchandiseInventory inventory;
        
    @OneToMany(mappedBy = "merchandise", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("merchandise")
        private List<MerchandiseReview> reviews;
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("merchandiseItem")
        private List<MerchandiseSale> sales;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "supplier_id")
        @JsonIgnoreProperties("suppliedMerchandise")
        private MerchandiseSupplier supplier;
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("merchandiseItem")
        private List<MerchandiseShipping> shipments;
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("merchandiseItem")
        private List<MerchandiseOrderItem> orderItems;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "collection_id")
        @JsonIgnoreProperties("items")
        private MerchandiseCollection collection;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "category_id")
        @JsonIgnoreProperties("merchandise")
        private MerchandiseCategory category;
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("merchandiseItem")
        private List<MerchandiseStockHistory> stockHistory;
    

}