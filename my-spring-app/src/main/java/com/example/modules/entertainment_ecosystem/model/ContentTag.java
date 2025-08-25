package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contenttag_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentTag extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tags")
    private List<Movie> movies = new ArrayList<>();
    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tags")
    private List<TVShow> tvShows = new ArrayList<>();
    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tags")
    private List<VideoGame> videoGames = new ArrayList<>();
    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tags")
    private List<LiveEvent> liveEvents = new ArrayList<>();
    
}
