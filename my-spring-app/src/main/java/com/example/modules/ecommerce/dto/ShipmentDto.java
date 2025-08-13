package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDto {

    private Long id;

    private Date shipmentDate;

    private String carrier;

    private String trackingNumber;

    private OrderSimpleDto order;

}