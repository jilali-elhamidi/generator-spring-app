package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SocialMediaPlatformSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSocialMediaDto {

    private Long id;

    private String url;

    private ArtistSimpleDto artist;

    private SocialMediaPlatformSimpleDto platform;

}