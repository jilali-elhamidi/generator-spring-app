package com.example.modules.ecommerce.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentSimpleDto {
    private Long id;

    private Date shipmentDate;

    private String carrier;

    private String trackingNumber;

}