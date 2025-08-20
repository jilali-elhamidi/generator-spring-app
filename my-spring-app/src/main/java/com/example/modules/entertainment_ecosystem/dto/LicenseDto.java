package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDto {

    private Long id;

    private String licenseKey;

    private Date startDate;

    private Date endDate;

    private DigitalAssetSimpleDto asset;

}