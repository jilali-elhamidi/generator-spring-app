package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageThreadDto {

    private Long id;

    private String subject;

    private Date lastUpdated;

    private List<UserProfileSimpleDto> participants;

    private List<UserMessageSimpleDto> messages;

}