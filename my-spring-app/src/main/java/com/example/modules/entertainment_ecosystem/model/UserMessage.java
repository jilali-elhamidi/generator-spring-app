package com.example.modules.entertainment_ecosystem.model;

import com.example.core.module.BaseEntity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.MessageThread;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usermessage_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserMessage extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 1, max = 100)
    private String subject;

    @NotNull@Size(min = 1, max = 2000)
    private String body;

    @NotNull
    private Date sentDate;

    @NotNull
    private Boolean isRead;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties("sentMessages")
    private UserProfile sender;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties("receivedMessages")
    private UserProfile receiver;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "thread_id")
    @JsonIgnoreProperties("messages")
    private MessageThread thread;
    

}