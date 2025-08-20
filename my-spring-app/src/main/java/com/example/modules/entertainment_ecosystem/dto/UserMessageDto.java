package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MessageThreadSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDto {

    private Long id;

    private String subject;

    private String body;

    private Date sentDate;

    private Boolean isRead;

    private UserProfileSimpleDto sender;

    private UserProfileSimpleDto receiver;

    private MessageThreadSimpleDto thread;

}