package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamingServiceDto {

    private Long id;

    private String name;

    private String logoUrl;

    private List<StreamingPlatformSimpleDto> platforms;

    private List<SubscriptionPlanSimpleDto> plans;

}