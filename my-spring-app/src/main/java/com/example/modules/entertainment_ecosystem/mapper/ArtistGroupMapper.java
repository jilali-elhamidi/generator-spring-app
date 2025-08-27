package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.dto.ArtistGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistGroupSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ArtistGroupMapper extends BaseMapper<ArtistGroup, ArtistGroupDto, ArtistGroupSimpleDto> {
}