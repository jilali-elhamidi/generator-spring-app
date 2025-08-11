package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private Date commentDate;
    private ProfileSimpleDto author; // <-- Utilise un DTO simple
    private PostSimpleDto post; // <-- Utilise un DTO simple
    private CommentSimpleDto parentComment; // <-- Utilise un DTO simple
    private List<CommentSimpleDto> replies; // <-- Utilise un DTO simple

}