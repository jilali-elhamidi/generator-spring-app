package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseSaleDto {

    private Long id;

    private Date saleDate;

    private Integer quantity;

    private MerchandiseSimpleDto merchandiseItem;

    private UserProfileSimpleDto user;

}