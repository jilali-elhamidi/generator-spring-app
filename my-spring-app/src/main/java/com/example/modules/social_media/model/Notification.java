package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.model.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notification_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 30)
    private String type;

    @NotNull@Size(min = 1, max = 255)
    private String content;

    @NotNull
    private Date creationDate;

    @NotNull
    private Boolean isRead;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "profile_id")
    @JsonIgnoreProperties("notifications")
    private Profile recipient;
    

}