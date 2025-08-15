package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;import com.example.modules.entertainment_ecosystem.model.Genre;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Publisher;import com.example.modules.entertainment_ecosystem.model.PodcastCategory;import com.example.modules.entertainment_ecosystem.model.PodcastGuest;import com.example.modules.entertainment_ecosystem.model.ContentProvider;import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "podcast_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Podcast extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @Min(1)
    private Integer totalEpisodes;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "host_id")
        
        private Artist host;
    
    
    
    @OneToMany(mappedBy = "podcast", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<PodcastEpisode> episodes;
    
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Genre> genres;
        
    
    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<UserProfile> listeners;
        
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "publisher_id")
        
        private Publisher publisher;
    
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "podcast_categories",
            joinColumns = @JoinColumn(name = "podcast_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
            @JsonIgnoreProperties("")
            private List<PodcastCategory> categories;
            
    
    @OneToMany(mappedBy = "podcast", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<PodcastGuest> guests;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provider_id")
        
        private ContentProvider provider;
    
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "podcast_languages",
            joinColumns = @JoinColumn(name = "podcast_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
            @JsonIgnoreProperties("")
            private List<ContentLanguage> languages;
            

}