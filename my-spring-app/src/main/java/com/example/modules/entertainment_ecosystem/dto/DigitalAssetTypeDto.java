package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalAssetTypeDto {

    private Long id;

    private String name;

    private List<DigitalAssetSimpleDto> assets;

}