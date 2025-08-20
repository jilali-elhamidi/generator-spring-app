package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBadgeDto {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private List<UserProfileSimpleDto> users;

}