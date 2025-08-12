package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.ProfileSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;

    private String content;

    private Date sentDate;

    private Boolean read;

    private ProfileSimpleDto sender;

    private ProfileSimpleDto recipient;

}