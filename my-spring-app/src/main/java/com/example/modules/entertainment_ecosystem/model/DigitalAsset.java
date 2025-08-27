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
@Table(name = "digitalasset_tbl")
public class DigitalAsset extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 255)
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(max = 500)
    private String url;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties("assets")
    private DigitalAssetType assetType;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("managedAssets")
    private Artist artist;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne(mappedBy = "asset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("asset")
    private License license;

    // === Relations ManyToMany ===
}
