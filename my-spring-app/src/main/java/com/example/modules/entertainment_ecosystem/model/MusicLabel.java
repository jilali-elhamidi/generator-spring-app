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
@Table(name = "musiclabel_tbl")
public class MusicLabel extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "musicLabel", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("musicLabel")
    private List<Album> albums = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
