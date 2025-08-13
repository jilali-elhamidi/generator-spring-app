package com.example.modules.ecommerce.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDto {

    private Long id;

    private String username;

    private String email;

    private String phone;

}