package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderItemSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseOrderDto {

    private Long id;

    private Date orderDate;

    private String status;

    private UserProfileSimpleDto user;

    private List<MerchandiseOrderItemSimpleDto> items;

    private MerchandiseShippingSimpleDto shippingDetails;

}