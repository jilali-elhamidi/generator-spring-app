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
@Table(name = "podcast_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Podcast extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
        @Column(unique = true, nullable = false)
    private String title;

    @Size(max = 1000)
    private String description;

    @Min(1)
    private Integer totalEpisodes;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "host_id")
    @JsonIgnoreProperties("hostedPodcasts")
    private Artist host;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties("podcasts")
    private Publisher publisher;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "provider_id")
    @JsonIgnoreProperties("providedPodcasts")
    private ContentProvider provider;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "podcast", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("podcast")
    private List<PodcastEpisode> episodes = new ArrayList<>();
    
    @OneToMany(mappedBy = "podcast", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("podcast")
    private List<PodcastGuest> guests = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "podcasts", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("podcasts")
    private List<Genre> genres = new ArrayList<>();
    
    @ManyToMany(mappedBy = "libraryPodcasts", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("libraryPodcasts")
    private List<UserProfile> listeners = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "podcast_categories",
        joinColumns = @JoinColumn(name = "podcast_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("podcasts")
    private List<PodcastCategory> categories = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "podcast_languages",
        joinColumns = @JoinColumn(name = "podcast_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id"))
    @JsonIgnoreProperties("podcasts")
    private List<ContentLanguage> languages = new ArrayList<>();
    
    
}
