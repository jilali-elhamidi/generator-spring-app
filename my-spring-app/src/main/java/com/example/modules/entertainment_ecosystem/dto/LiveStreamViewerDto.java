package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveStreamViewerDto {

    private Long id;

    private Date joinTime;

    private Date leaveTime;

    private UserProfileSimpleDto user;

    private LiveStreamSimpleDto liveStream;

}