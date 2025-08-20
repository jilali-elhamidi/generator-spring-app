package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseSaleSimpleDto {

    private Long id;

    private Date saleDate;

    private Integer quantity;

}