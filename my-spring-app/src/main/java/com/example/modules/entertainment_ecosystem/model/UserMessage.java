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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "sender_id")
        
        private UserProfile sender;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "receiver_id")
        
        private UserProfile receiver;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "thread_id")
        
        private MessageThread thread;
    
    

}