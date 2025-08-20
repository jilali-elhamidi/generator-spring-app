package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseInventorySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {

    private Long id;

    private String name;

    private String address;

    private List<MerchandiseInventorySimpleDto> inventoryItems;

}