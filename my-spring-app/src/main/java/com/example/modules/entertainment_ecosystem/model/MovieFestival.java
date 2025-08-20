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
import com.example.modules.entertainment_ecosystem.model.Movie;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "moviefestival_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MovieFestival extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 255)
    private String name;

        @NotNull
    private Date startDate;

        @NotNull
    private Date endDate;

        @Size(max = 255)
    private String location;


// === Relations ===

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "festival_movies",
            joinColumns = @JoinColumn(name = "festival_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
            @JsonIgnoreProperties("festivals")
            private List<Movie> movies;
            

}