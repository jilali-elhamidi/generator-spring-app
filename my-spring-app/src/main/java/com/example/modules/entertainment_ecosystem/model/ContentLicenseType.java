package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "contentlicensetype_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContentLicenseType extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 50)
    private String name;

        @Size(max = 255)
    private String description;


// === Relations ===

    @OneToMany(mappedBy = "licenseType", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("licenseType")
        private List<StreamingContentLicense> licenses;
    

}