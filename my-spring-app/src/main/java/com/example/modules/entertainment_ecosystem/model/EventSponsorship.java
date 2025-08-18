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
import com.example.modules.entertainment_ecosystem.model.LiveEvent;import com.example.modules.entertainment_ecosystem.model.Sponsor;import com.example.modules.entertainment_ecosystem.model.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Entity
@Table(name = "eventsponsorship_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EventSponsorship extends BaseEntity {

// === Attributs simples ===

        @NotNull
    private Double amount;

        @NotNull
    private Date startDate;

        @NotNull
    private Date endDate;


// === Relations ===

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "event_id")
        @JsonIgnoreProperties("sponsorships")
        private LiveEvent event;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinColumn(name = "sponsor_id")
        @JsonIgnoreProperties("sponsorships")
        private Sponsor sponsor;
    
    @OneToOne
            @JoinColumn(name = "contract_id")
            @JsonIgnoreProperties("sponsorship")
            private Contract contract;
            

}