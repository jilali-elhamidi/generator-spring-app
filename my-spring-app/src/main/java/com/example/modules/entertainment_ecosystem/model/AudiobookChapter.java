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
@Table(name = "audiobookchapter_tbl")
public class AudiobookChapter extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String title;

    @NotNull
    @Min(1)
    private Integer chapterNumber;

    @Min(1)
    private Integer durationMinutes;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "audiobook_id")
    @JsonIgnoreProperties("chapters")
    private Audiobook audiobook;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
