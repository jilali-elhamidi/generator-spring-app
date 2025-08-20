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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.LiveStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "livestreamviewer_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LiveStreamViewer extends BaseEntity {

// === Attributs simples ===

        @NotNull
    private Date joinTime;

        @NotNull
    private Date leaveTime;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties("watchedLiveStreams")
        private UserProfile user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "live_stream_id")
        @JsonIgnoreProperties("viewers")
        private LiveStream liveStream;
    

}