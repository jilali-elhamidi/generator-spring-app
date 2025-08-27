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
@Table(name = "merchandise_tbl")
public class Merchandise extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private Double price;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("managedMerchandise")
    private Artist artist;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties("items")
    private MerchandiseType productType;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties("suppliedMerchandise")
    private MerchandiseSupplier supplier;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "collection_id")
    @JsonIgnoreProperties("items")
    private MerchandiseCollection collection;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("merchandise")
    private MerchandiseCategory category;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "merchandise", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("merchandise")
    private List<MerchandiseReview> reviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("merchandiseItem")
    private List<MerchandiseSale> sales = new ArrayList<>();
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("merchandiseItem")
    private List<MerchandiseShipping> shipments = new ArrayList<>();
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("merchandiseItem")
    private List<MerchandiseOrderItem> orderItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "merchandiseItem", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("merchandiseItem")
    private List<MerchandiseStockHistory> stockHistory = new ArrayList<>();
    

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "merchandiseItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("merchandiseItem")
    private MerchandiseInventory inventory;

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "merchandise_movies",
        joinColumns = @JoinColumn(name = "merchandise_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    @JsonIgnoreProperties("relatedMerchandise")
    private List<Movie> relatedMovies = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "merchandise_shows",
        joinColumns = @JoinColumn(name = "merchandise_id"),
        inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
    @JsonIgnoreProperties("relatedMerchandise")
    private List<TVShow> relatedShows = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "ownedMerchandise", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("ownedMerchandise")
    private List<UserProfile> ownedByUsers = new ArrayList<>();
    
}
