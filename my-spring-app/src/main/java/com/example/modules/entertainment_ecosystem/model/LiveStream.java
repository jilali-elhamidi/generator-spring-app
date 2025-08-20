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
import com.example.modules.entertainment_ecosystem.model.UserProfile;import com.example.modules.entertainment_ecosystem.model.VideoGame;import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "livestream_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LiveStream extends BaseEntity {

// === Attributs simples ===

        @NotNull@Size(min = 2, max = 255)
    private String title;

        @NotNull
    private Date startDate;

        @NotNull@Size(max = 255)
    private String platformUrl;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "host_id")
        @JsonIgnoreProperties("hostedLiveStreams")
        private UserProfile host;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "game_id")
        @JsonIgnoreProperties("liveStreams")
        private VideoGame game;
    
    @OneToMany(mappedBy = "liveStream", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
        @JsonIgnoreProperties("liveStream")
        private List<LiveStreamViewer> viewers;
    

}