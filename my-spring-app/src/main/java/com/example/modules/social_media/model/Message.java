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
@Table(name = "message_tbl")
public class Message extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 1, max = 500)
    @Column(unique = true, nullable = false)
    private String content;

    @NotNull
    private Date sentDate;

    @NotNull
    private Boolean read;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties("sentMessages")
    private Profile sender;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipient_id")
    @JsonIgnoreProperties("receivedMessages")
    private Profile recipient;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
