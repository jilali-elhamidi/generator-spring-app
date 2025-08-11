package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.model.Post;import com.example.modules.social_media.model.Comment;import com.example.modules.social_media.model.Message;import com.example.modules.social_media.model.Message;import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Role;import com.example.modules.social_media.model.Group;import com.example.modules.social_media.model.Notification;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "profile_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Profile extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 3, max = 50)
    private String name;

    @Size(max = 255)
    private String bio;

    @NotNull@Size(min = 3, max = 30)
    private String handle;

    @NotNull@Email
    private String email;

    @NotNull
    private Date registrationDate;


// === Relations ===

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Post> posts;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("author")
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("recipient")
    private List<Message> receivedMessages;
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sender")
    private List<Message> sentMessages;
    
    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("following")
            private List<Profile> followers;
        
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "profile_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
            @JsonIgnoreProperties("follower")
            private List<Profile> following;
            
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "profile_roles",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
            @JsonIgnoreProperties("")
            private List<Role> roles;
            
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
            @JsonIgnoreProperties("members")
            private List<Group> groups;
        
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("recipient")
    private List<Notification> notifications;
    

}