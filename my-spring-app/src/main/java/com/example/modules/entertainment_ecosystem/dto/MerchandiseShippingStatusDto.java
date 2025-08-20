package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseShippingStatusDto {

    private Long id;

    private String status;

    private Date date;

    private MerchandiseShippingSimpleDto shipment;

}