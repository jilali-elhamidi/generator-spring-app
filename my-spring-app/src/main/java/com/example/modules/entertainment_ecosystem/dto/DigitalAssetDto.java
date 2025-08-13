package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LicenseSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalAssetDto {

    private Long id;

    private String name;

    private String type;

    private String url;

    private ArtistSimpleDto artist;

    private LicenseSimpleDto license;

}