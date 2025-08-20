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
import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "gameexpansionpack_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GameExpansionPack extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 255)
    private String name;

        @NotNull
    private Date releaseDate;

        
    private Double price;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "video_game_id")
        @JsonIgnoreProperties("expansionPacks")
        private VideoGame baseGame;
    
    @OneToMany(mappedBy = "expansionPack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("expansionPack")
        private List<DigitalPurchase> purchases;
    

}