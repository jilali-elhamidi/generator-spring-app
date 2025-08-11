package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


    import com.example.modules.social_media.dto.PostDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

private Long id;

    private String name;

    private List<PostDto> posts;
    
}