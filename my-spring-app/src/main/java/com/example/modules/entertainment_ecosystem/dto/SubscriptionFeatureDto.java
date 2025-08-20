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
public class SubscriptionFeatureDto {

    private Long id;

    private String name;

    private String description;

    private List<SubscriptionPlanSimpleDto> subscriptionPlans;

}