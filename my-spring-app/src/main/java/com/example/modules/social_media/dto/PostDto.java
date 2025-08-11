package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String content;
    private Date postDate;
    private ProfileSimpleDto author; // <-- Utilise un DTO simple
    private List<CommentSimpleDto> comments; // <-- Utilise un DTO simple
    private List<TagSimpleDto> tags;
    private List<MediaFileDto> media;
    private GroupSimpleDto group; // <-- Utilise un DTO simple

}