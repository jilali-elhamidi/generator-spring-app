package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseOrderItemDto {

    private Long id;

    private Integer quantity;

    private Double priceAtPurchase;

    private MerchandiseSimpleDto merchandiseItem;

    private MerchandiseOrderSimpleDto order;

}