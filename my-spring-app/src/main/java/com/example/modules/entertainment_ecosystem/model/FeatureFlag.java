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
@Table(name = "featureflag_tbl")
public class FeatureFlag extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Boolean enabled;

    @Max(100)
    private Integer rolloutPercentage;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "enabledFeatureFlags", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("enabledFeatureFlags")
    private List<UserProfile> enabledForUsers = new ArrayList<>();
    
}
