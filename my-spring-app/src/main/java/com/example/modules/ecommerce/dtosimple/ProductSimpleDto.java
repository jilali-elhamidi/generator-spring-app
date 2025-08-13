package com.example.modules.ecommerce.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSimpleDto {

    private Long id;

    private String name;

    private Integer stock;

    private Double price;

    private String description;

}