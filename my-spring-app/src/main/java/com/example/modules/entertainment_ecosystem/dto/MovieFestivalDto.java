package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieFestivalDto {

    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private String location;

    private List<MovieSimpleDto> movies;

}