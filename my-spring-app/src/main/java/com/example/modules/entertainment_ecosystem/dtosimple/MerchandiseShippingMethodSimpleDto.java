package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseShippingMethodSimpleDto {

    private Long id;

    private String name;

    private Double cost;

    private String estimatedDeliveryTime;

}