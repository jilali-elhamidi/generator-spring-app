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
public class UserLevelDto {

    private Long id;

    private Integer levelNumber;

    private String name;

    private Integer pointsRequired;

    private List<UserProfileSimpleDto> users;

}