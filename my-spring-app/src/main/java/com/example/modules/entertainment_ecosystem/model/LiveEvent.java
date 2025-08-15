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
import com.example.modules.entertainment_ecosystem.model.Artist;import com.example.modules.entertainment_ecosystem.model.Ticket;import com.example.modules.entertainment_ecosystem.model.EventType;import com.example.modules.entertainment_ecosystem.model.EventLocation;import com.example.modules.entertainment_ecosystem.model.Sponsor;import com.example.modules.entertainment_ecosystem.model.EventAudience;import com.example.modules.entertainment_ecosystem.model.EventSponsorship;import com.example.modules.entertainment_ecosystem.model.ContentTag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "liveevent_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LiveEvent extends BaseEntity {

// === Attributs simples ===

    @NotNull@Size(min = 2, max = 255)
    private String name;

    @NotNull
    private Date eventDate;

    @Size(max = 1000)
    private String description;


// === Relations ===

    
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "event_performers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
            @JsonIgnoreProperties("")
            private List<Artist> performers;
            
    
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Ticket> tickets;
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "eventType_id")
        
        private EventType eventType;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "location_id")
        
        private EventLocation location;
    
    
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "sponsor_id")
        
        private Sponsor sponsor;
    
    
    
    @OneToOne
    @JoinColumn(name = "audience_id")
    @JsonIgnoreProperties("event")
    private EventAudience audience;
            
    
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<EventSponsorship> sponsorships;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
            @JsonIgnoreProperties("")
            private List<ContentTag> tags;
            

}