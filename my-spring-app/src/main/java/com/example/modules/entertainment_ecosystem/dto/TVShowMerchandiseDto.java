package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVShowMerchandiseDto {

    private Long id;

    private String name;

    private Double price;

    private TVShowSimpleDto tvShow;

}