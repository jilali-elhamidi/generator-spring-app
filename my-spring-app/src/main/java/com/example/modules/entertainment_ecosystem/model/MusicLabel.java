package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.Album;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "musiclabel_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MusicLabel extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;


// === Relations ===

    @OneToMany(mappedBy = "musicLabel", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("musicLabel")
        private List<Album> albums;
    

}