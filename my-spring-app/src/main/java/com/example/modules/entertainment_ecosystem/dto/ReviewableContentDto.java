package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TagSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewableContentDto {

    private Long id;

    private String title;

    private String contentType;

    private List<ReviewSimpleDto> reviews;

    private List<TagSimpleDto> tags;

}