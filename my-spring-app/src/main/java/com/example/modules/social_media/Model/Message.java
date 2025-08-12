package com.example.modules.social_media.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.social_media.model.Profile;import com.example.modules.social_media.model.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "message_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Date sentDate;

    @NotNull
    private Boolean read;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties("sentMessages")
    private Profile sender;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipient_id")
    @JsonIgnoreProperties("receivedMessages")
    private Profile recipient;
    

}