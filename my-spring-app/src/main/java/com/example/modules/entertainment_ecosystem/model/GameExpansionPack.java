package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gameexpansionpack_tbl")
public class GameExpansionPack extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Date releaseDate;

    private Double price;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "video_game_id")
    @JsonIgnoreProperties("expansionPacks")
    private VideoGame baseGame;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "expansionPack", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("expansionPack")
    private List<DigitalPurchase> purchases = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
