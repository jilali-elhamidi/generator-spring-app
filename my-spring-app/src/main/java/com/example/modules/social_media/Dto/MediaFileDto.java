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
public class MediaFileDto {

    private Long id;

    private String url;

    private String type;

    private PostSimpleDto post;

}