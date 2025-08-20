package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamViewerSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveStreamDto {

    private Long id;

    private String title;

    private Date startDate;

    private String platformUrl;

    private UserProfileSimpleDto host;

    private VideoGameSimpleDto game;

    private List<LiveStreamViewerSimpleDto> viewers;

}