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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "forumthread_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ForumThread extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 5, max = 255)
    private String title;

    @NotNull
    private Date creationDate;

    private Date lastPostDate;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id")
        
        private UserProfile author;
    
    
    
    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumPost> forumPosts;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id")
        
        private ForumCategory category;
    
    

}