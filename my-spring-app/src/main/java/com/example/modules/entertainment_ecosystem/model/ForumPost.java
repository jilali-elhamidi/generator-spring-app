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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.ForumThread;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.ForumPost;import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "author_id")
        @JsonIgnoreProperties("forumPosts")
        private UserProfile author;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "thread_id")
        @JsonIgnoreProperties("forumPosts")
        private ForumThread thread;
    
    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("parentPost")
        private List<ForumPost> replies;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "parent_post_id")
        @JsonIgnoreProperties("replies")
        private ForumPost parentPost;
    
    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("forumPost")
        private List<ReportedContent> reportedContent;
    

}