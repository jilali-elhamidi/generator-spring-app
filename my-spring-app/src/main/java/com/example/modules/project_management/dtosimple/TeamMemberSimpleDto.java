package com.example.modules.project_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberSimpleDto {

    private Long id;

    private String name;

    private String email;

    private String role;

}