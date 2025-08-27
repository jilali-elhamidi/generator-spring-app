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
@Table(name = "socialmediaplatform_tbl")
public class SocialMediaPlatform extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    private String url;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "platform", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("platform")
    private List<ArtistSocialMedia> artistSocialMedia = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
