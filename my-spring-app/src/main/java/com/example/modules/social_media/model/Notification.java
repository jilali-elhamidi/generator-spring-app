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
@Table(name = "notification_tbl")
public class Notification extends BaseEntity {

    // === Attributs simples ===
    @NotNull
    @Size(min = 2, max = 30)
    @Column(unique = true, nullable = false)
    private String type;

    @NotNull
    @Size(min = 1, max = 255)
    private String content;

    @NotNull
    private Date creationDate;

    @NotNull
    private Boolean isRead;


    // === Relations ManyToOne ===
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "profile_id")
    @JsonIgnoreProperties("notifications")
    private Profile recipient;
    

    // === Relations OneToMany ===

    // === Relations OneToOne ===

    // === Relations ManyToMany ===
}
