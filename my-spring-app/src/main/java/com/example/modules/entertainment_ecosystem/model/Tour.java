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
@Table(name = "tour_tbl")
public class Tour extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("tours")
    private Artist artist;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "tour", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tour")
    private List<LiveEvent> liveEvents = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
