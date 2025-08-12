package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.social_media.model.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "role_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 50)
    private String name;


// === Relations ===

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("roles")
            private List<Profile> profiles;
        

}