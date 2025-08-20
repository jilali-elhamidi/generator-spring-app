package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLevelSimpleDto {

    private Long id;

    private Integer levelNumber;

    private String name;

    private Integer pointsRequired;

}