package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {

    private Long id;

    private Date startDate;

    private Date endDate;

    private String status;

    private UserProfileSimpleDto user;

    private StreamingPlatformSimpleDto platform;

    private SubscriptionPlanSimpleDto plan;

}