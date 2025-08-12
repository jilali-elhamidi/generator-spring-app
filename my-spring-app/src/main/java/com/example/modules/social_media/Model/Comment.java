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
import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Post;import com.example.modules.social_media.model.Comment;import com.example.modules.social_media.model.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comment_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 255)
    private String content;

    @NotNull
    private Date commentDate;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "profile_id")
    @JsonIgnoreProperties("comments")
    private Profile author;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties("comments")
    private Post post;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnoreProperties("replies")
    private Comment parentComment;
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parentComment")
    private List<Comment> replies;
    

}