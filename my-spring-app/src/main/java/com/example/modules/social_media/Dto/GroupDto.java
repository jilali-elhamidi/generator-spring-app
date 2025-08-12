package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.PostSimpleDto;

import com.example.modules.social_media.dtosimple.ProfileSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private String description;

    private List<ProfileSimpleDto> members;

    private List<PostSimpleDto> posts;

    private ProfileSimpleDto owner;

}