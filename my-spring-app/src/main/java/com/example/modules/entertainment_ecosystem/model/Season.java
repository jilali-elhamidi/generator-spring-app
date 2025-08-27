package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "season_tbl")
public class Season extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Min(1)
    @Column(unique = true, nullable = false)
    private Integer seasonNumber;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tvshow_id")
    @JsonIgnoreProperties("seasons")
    private TVShow show;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "season", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("season")
    private List<Episode> episodes = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
