package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.dto.ArtistSocialMediaDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSocialMediaSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ArtistSocialMediaMapper extends BaseMapper<ArtistSocialMedia, ArtistSocialMediaDto, ArtistSocialMediaSimpleDto> {
}