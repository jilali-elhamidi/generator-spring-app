package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumCategory;import com.example.modules.entertainment_ecosystem.model.ForumCategory;import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "forumcategory_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ForumCategory extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 50)
    private String name;


// === Relations ===

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("category")
        private List<ForumThread> threads;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "parent_category_id")
        @JsonIgnoreProperties("childCategories")
        private ForumCategory parentCategory;
    
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("parentCategory")
        private List<ForumCategory> childCategories;
    
    @ManyToMany(mappedBy = "moderatedCategories", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("moderatedCategories")
            private List<ForumModerator> moderators = new ArrayList<>();
        

}