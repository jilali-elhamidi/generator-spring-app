package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistItemSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlaylistDto {

    private Long id;

    private String name;

    private Boolean isPublic;

    private UserProfileSimpleDto user;

    private List<UserPlaylistItemSimpleDto> items;

}