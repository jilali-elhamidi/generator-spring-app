package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.WarehouseSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseInventoryDto {

    private Long id;

    private Integer stockQuantity;

    private Date lastUpdated;

    private MerchandiseSimpleDto merchandiseItem;

    private WarehouseSimpleDto warehouse;

}