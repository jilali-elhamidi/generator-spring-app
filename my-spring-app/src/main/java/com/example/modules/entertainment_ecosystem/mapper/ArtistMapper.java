package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.dto.ArtistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ArtistMapper extends BaseMapper<Artist, ArtistDto, ArtistSimpleDto> {
}