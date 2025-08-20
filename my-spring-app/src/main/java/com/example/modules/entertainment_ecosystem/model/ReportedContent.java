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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.Review;import com.example.modules.entertainment_ecosystem.model.ForumPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "reportedcontent_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReportedContent extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 5, max = 500)
    private String reason;

        @NotNull
    private Date reportDate;

        @NotNull
    private String status;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("reportedContent")
        private UserProfile user;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "review_id")
        @JsonIgnoreProperties("reportedContent")
        private Review review;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "post_id")
        @JsonIgnoreProperties("reportedContent")
        private ForumPost forumPost;
    

}