package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineEventTypeDto {

    private Long id;

    private String name;

    private List<OnlineEventSimpleDto> events;

}