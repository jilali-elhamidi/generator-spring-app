package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.License;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "digitalasset_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DigitalAsset extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 255)
    private String name;

        @NotNull@Size(max = 500)
    private String url;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "type_id")
        @JsonIgnoreProperties("assets")
        private DigitalAssetType assetType;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "artist_id")
        @JsonIgnoreProperties("managedAssets")
        private Artist artist;
    
    @OneToOne(mappedBy = "asset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
            @JsonIgnoreProperties("asset")
            private License license;
        

}