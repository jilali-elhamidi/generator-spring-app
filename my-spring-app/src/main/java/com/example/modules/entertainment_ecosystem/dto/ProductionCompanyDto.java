package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCompanyDto {

    private Long id;

    private String name;

    private Date foundedDate;

    private String location;

    private List<MovieSimpleDto> movies;

    private List<TVShowSimpleDto> tvShows;

    private List<EmployeeSimpleDto> staff;

}