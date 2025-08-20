package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.social_media.dtosimple.PostSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id;

    private String name;

    private List<PostSimpleDto> posts;

}