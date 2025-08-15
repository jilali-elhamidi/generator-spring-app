package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumCategory;import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumThread> threads;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "parentCategory_id")
        
        private ForumCategory parentCategory;
    
    
    
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumCategory> childCategories;
    

}