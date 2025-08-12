package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlinePlatformDto {

    private Long id;

    private String name;

    private String url;

    private List<OnlineEventSimpleDto> liveEvents;

    private List<StreamingPlatformSimpleDto> streams;

}