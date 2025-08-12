package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.dtosimple.UserSimpleDto;

import com.example.modules.ecommerce.dtosimple.OrderItemSimpleDto;







@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private Date orderDate;

    private String status;

    private UserSimpleDto user;

    private List<OrderItemSimpleDto> orderItems;

    

    

}