package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;

import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Post;import com.example.modules.social_media.model.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "group_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Group extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 3, max = 50)
    private String name;

    @Size(max = 255)
    private String description;


// === Relations ===

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
            @JsonIgnoreProperties("")
            private List<Profile> members;
            
    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("group")
    private List<Post> posts;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties("ownedGroups")
    private Profile owner;
    

}