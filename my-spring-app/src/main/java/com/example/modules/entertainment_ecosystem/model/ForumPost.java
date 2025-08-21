package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

// === Jackson ===
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "forumpost_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ForumPost extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 1, max = 2000)
        @Column(unique = true, nullable = false)
    private String content;

    @NotNull
    private Date postDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("forumPosts")
    private UserProfile author;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "thread_id")
    @JsonIgnoreProperties("forumPosts")
    private ForumThread thread;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_post_id")
    @JsonIgnoreProperties("replies")
    private ForumPost parentPost;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parentPost")
    private List<ForumPost> replies = new ArrayList<>();
    
    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("forumPost")
    private List<ReportedContent> reportedContent = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
