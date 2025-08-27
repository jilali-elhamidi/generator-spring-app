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
@Table(name = "userfollow_tbl")
public class UserFollow extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Column(unique = true, nullable = false)
    private Date followDate;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "follower_id")
    @JsonIgnoreProperties("following")
    private UserProfile follower;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "followed_id")
    @JsonIgnoreProperties("followers")
    private UserProfile followed;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
