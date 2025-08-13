package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Movie;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "movieformat_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MovieFormat extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 50)
    private String name;


// === Relations ===

    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("")
            private List<Movie> movies;
        

}