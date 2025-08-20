package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSocialMediaSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaPlatformDto {

    private Long id;

    private String name;

    private String url;

    private List<ArtistSocialMediaSimpleDto> artistSocialMedia;

}