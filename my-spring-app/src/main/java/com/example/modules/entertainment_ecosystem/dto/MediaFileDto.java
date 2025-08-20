package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaFileDto {

    private Long id;

    private String url;

    private String type;

    private ReviewSimpleDto review;

}