package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingMethodSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingStatusSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseShippingDto {

    private Long id;

    private Date shippingDate;

    private String carrier;

    private String trackingNumber;

    private MerchandiseSimpleDto merchandiseItem;

    private MerchandiseOrderSimpleDto order;

    private MerchandiseShippingMethodSimpleDto shippingMethod;

    private List<MerchandiseShippingStatusSimpleDto> statusHistory;

}