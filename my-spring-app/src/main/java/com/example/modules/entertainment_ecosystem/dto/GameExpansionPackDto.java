package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameExpansionPackDto {

    private Long id;

    private String name;

    private Date releaseDate;

    private Double price;

    private VideoGameSimpleDto baseGame;

    private List<DigitalPurchaseSimpleDto> purchases;

}