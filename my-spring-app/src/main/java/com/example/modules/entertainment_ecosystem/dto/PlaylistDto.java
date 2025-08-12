package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistItemSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {

    private Long id;

    private String name;

    private Date creationDate;

    private Boolean isPublic;

    private UserProfileSimpleDto owner;

    private List<PlaylistItemSimpleDto> items;

}