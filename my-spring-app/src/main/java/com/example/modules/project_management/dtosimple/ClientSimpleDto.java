package com.example.modules.project_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSimpleDto {

    private Long id;

    private String companyName;

    private String contactPerson;

    private String email;

    private String phoneNumber;

}