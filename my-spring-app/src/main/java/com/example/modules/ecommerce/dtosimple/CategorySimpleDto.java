package com.example.modules.ecommerce.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySimpleDto {

    private Long id;

    private String name;

    private String description;

}