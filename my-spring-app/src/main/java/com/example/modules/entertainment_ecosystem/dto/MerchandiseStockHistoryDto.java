package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseStockHistoryDto {

    private Long id;

    private Date changeDate;

    private Integer quantityChange;

    private String reason;

    private MerchandiseSimpleDto merchandiseItem;

}