package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagSimpleDto {

    private Long id;

    private String name;

    private Boolean enabled;

    private Integer rolloutPercentage;

}