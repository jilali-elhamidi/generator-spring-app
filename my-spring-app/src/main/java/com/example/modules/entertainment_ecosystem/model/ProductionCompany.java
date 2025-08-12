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
import com.example.modules.entertainment_ecosystem.model.Movie;import com.example.modules.entertainment_ecosystem.model.TVShow;import com.example.modules.entertainment_ecosystem.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "productioncompany_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductionCompany extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 100)
    private String name;

    private Date foundedDate;

    @Size(max = 255)
    private String location;


// === Relations ===

    @OneToMany(mappedBy = "productionCompany", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("productionCompany")
    private List<Movie> movies;
    
    @OneToMany(mappedBy = "productionCompany", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("productionCompany")
    private List<TVShow> tvShows;
    
    @OneToMany(mappedBy = "productionCompany", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("productionCompany")
    private List<Employee> staff;
    

}