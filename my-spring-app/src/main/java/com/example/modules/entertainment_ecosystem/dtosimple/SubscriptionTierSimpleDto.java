package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionTierSimpleDto {

    private Long id;

    private String name;

    private String features;

    private Double price;

    private String billingPeriod;

}