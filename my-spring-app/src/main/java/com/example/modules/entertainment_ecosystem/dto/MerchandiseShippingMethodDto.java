package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseShippingMethodDto {

    private Long id;

    private String name;

    private Double cost;

    private String estimatedDeliveryTime;

    private List<MerchandiseShippingSimpleDto> shipments;

}