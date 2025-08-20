package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {

    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private ArtistSimpleDto artist;

    private List<LiveEventSimpleDto> liveEvents;

}