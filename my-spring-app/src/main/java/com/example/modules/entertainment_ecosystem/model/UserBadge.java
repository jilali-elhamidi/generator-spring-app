package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "userbadge_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserBadge extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 100)
    private String name;

        @Size(max = 255)
    private String description;

        @Size(max = 255)
    private String imageUrl;


// === Relations ===

    @ManyToMany(mappedBy = "", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("badges")
            private List<UserProfile> users = new ArrayList<>();
        

}