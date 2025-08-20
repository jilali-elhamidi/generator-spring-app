package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingServiceSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionTierSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionFeatureSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanDto {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private List<SubscriptionSimpleDto> subscriptions;

    private StreamingServiceSimpleDto service;

    private List<StreamingContentLicenseSimpleDto> includedStreamingContentLicenses;

    private SubscriptionTierSimpleDto tier;

    private List<SubscriptionFeatureSimpleDto> features;

}