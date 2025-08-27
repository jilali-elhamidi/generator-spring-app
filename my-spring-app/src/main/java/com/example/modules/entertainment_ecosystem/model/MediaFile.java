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
@Table(name = "mediafile_tbl")
public class MediaFile extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(max = 255)
    @Column(unique = true, nullable = false)
    private String url;

    @NotNull
    @Size(max = 50)
    private String type;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "mediaFile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("mediaFile")
    private Review review;

    // === Relations ManyToMany ===
}
