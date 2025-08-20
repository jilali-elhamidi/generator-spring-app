package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentRatingBoardDto {

    private Long id;

    private String name;

    private String country;

    private List<ContentRatingSimpleDto> ratings;

}