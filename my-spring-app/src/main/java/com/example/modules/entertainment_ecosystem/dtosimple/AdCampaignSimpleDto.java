package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCampaignSimpleDto {

    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private Double budget;

}