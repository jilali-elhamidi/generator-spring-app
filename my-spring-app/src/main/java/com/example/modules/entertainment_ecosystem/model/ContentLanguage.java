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
@Table(name = "contentlanguage_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentLanguage extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
        @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 10)
    private String code;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "languages", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("languages")
    private List<Movie> movies = new ArrayList<>();
    
    @ManyToMany(mappedBy = "languages", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("languages")
    private List<TVShow> tvShows = new ArrayList<>();
    
    @ManyToMany(mappedBy = "languages", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("languages")
    private List<Podcast> podcasts = new ArrayList<>();
    
}
