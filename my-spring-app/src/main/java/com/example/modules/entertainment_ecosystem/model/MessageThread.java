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
@Table(name = "messagethread_tbl")
public class MessageThread extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 1, max = 100)
    @Column(unique = true, nullable = false)
    private String subject;

    @NotNull
    private Date lastUpdated;


    // === Relations ManyToOne ===

    // === Relations OneToMany ===
    @OneToMany(mappedBy = "thread", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("thread")
    private List<UserMessage> messages = new ArrayList<>();
    

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "message_thread_participants",
        joinColumns = @JoinColumn(name = "thread_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties("MessageThreads")
    private List<UserProfile> participants = new ArrayList<>();
    
    
}
