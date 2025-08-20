package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSimpleDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String position;

}