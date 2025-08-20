package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.Podcast;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "contentlanguage_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentLanguage extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 50)
    private String name;

        @NotNull@Size(min = 2, max = 10)
    private String code;


// === Relations ===

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