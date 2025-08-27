package com.example.modules.social_media.model;

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
@Table(name = "profile_tbl")
public class Profile extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 255)
    private String bio;

    @NotNull
    @Size(min = 3, max = 30)
    private String handle;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Date registrationDate;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Post> posts = new ArrayList<>();
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("recipient")
    private List<Message> receivedMessages = new ArrayList<>();
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sender")
    private List<Message> sentMessages = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("recipient")
    private List<Notification> notifications = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("following")
    private List<Profile> followers = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "",
        joinColumns = @JoinColumn(name = ""),
        inverseJoinColumns = @JoinColumn(name = ""))
    @JsonIgnoreProperties("followers")
    private List<Profile> following = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_roles",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("profiles")
    private List<Role> roles = new ArrayList<>();
    
    
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("members")
    private List<Group> groups = new ArrayList<>();
    
}
