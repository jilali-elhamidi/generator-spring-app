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
@Table(name = "forummoderator_tbl")
public class ForumModerator extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Date moderatorSince;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===

    // === Relations OneToOne ===
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("moderator")
    private UserProfile user;
    

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "moderator_categories",
        joinColumns = @JoinColumn(name = "moderator_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("moderators")
    private List<ForumCategory> moderatedCategories = new ArrayList<>();
    
    
}
