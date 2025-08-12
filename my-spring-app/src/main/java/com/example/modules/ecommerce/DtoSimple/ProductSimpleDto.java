package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


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