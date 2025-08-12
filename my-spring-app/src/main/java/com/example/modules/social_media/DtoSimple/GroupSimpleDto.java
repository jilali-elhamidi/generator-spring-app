package com.example.modules.social_media.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSimpleDto {
    private Long id;

    private String name;

    private String description;

}