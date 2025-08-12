package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Comment;import com.example.modules.social_media.model.Tag;import com.example.modules.social_media.model.MediaFile;import com.example.modules.social_media.model.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "post_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Date postDate;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "profile_id")
    @JsonIgnoreProperties("posts")
    private Profile author;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("post")
    private List<Comment> comments;
    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
            @JsonIgnoreProperties("")
            private List<Tag> tags;
            
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("post")
    private List<MediaFile> media;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("posts")
    private Group group;
    

}