package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionTierDto {

    private Long id;

    private String name;

    private String features;

    private Double price;

    private String billingPeriod;

    private SubscriptionPlanSimpleDto subscriptionPlan;

}