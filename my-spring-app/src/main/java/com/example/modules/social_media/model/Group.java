package com.example.modules.social_media.model;

// === Java / Jakarta ===
import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// === Lombok ===
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "group_tbl")
public class Group extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 255)
    private String description;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties("ownedGroups")
    private Profile owner;
    

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("group")
    private List<Post> posts = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_members",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id"))
    @JsonIgnoreProperties("groups")
    private List<Profile> members = new ArrayList<>();
    
    
}
