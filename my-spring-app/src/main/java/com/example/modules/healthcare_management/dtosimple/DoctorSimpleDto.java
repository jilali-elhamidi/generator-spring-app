package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSimpleDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String specialty;

    private String email;

    private String phoneNumber;

}