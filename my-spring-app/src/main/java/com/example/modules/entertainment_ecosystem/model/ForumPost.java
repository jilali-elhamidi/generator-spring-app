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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.ForumPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "forumpost_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ForumPost extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 2000)
    private String content;

    @NotNull
    private Date postDate;


// === Relations ===

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id")
        
        private UserProfile author;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "thread_id")
        
        private ForumThread thread;
    
    
    
    @OneToMany(mappedBy = "parentPost", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<ForumPost> replies;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "parentPost_id")
        
        private ForumPost parentPost;
    
    

}