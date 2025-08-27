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
@Table(name = "userlevel_tbl")
public class UserLevel extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Min(1)
    @Column(unique = true, nullable = false)
    private Integer levelNumber;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    private Integer pointsRequired;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "userLevel", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("userLevel")
    private List<UserProfile> users = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
