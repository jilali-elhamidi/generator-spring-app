package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineEventDto {

    private Long id;

    private String name;

    private Date eventDate;

    private String platformUrl;

    private String description;

    private UserProfileSimpleDto host;

    private List<UserProfileSimpleDto> attendees;

    private OnlineEventTypeSimpleDto type;

}