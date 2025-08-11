package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;
    private String name;
    private String description;
    private List<ProfileSimpleDto> members; // <-- Utilise un DTO simple
    private List<PostDto> posts;
    private ProfileSimpleDto owner; // <-- Utilise un DTO simple

}