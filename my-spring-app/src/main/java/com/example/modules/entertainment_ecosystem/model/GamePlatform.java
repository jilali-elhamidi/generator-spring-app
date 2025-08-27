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
@Table(name = "gameplatform_tbl")
public class GamePlatform extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "platform_type_id")
    @JsonIgnoreProperties("gamePlatforms")
    private GamePlatformType type;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platforms")
    private List<VideoGame> videoGames = new ArrayList<>();
    
}
