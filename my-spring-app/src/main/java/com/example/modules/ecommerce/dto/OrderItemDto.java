package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.ecommerce.dtosimple.ProductSimpleDto;

import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private Double price;

    private ProductSimpleDto product;

    private OrderSimpleDto order;

}