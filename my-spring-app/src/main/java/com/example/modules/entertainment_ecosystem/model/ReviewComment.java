package com.example.modules.entertainment_ecosystem.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reviewcomment_tbl")
public class ReviewComment extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true, nullable = false)
    private String commentText;

    @NotNull
    private Date commentDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("reviewComments")
    private UserProfile user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "review_id")
    @JsonIgnoreProperties("reviewComments")
    private Review review;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "reviewComment", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reviewComment")
    private List<ReviewReply> replies = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
