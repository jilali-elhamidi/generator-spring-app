package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "forummoderator_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ForumModerator extends BaseEntity {

// === Attributs simples ===

        @NotNull
    private Date moderatorSince;


// === Relations ===

    @OneToOne
            @JoinColumn(name = "user_id")
            @JsonIgnoreProperties("moderator")
            private UserProfile user;
            
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "moderator_categories",
            joinColumns = @JoinColumn(name = "moderator_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
            @JsonIgnoreProperties("moderators")
            private List<ForumCategory> moderatedCategories;
            

}