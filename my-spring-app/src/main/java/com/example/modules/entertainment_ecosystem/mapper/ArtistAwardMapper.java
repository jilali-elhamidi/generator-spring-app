package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.dto.ArtistAwardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistAwardSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ArtistAwardMapper extends BaseMapper<ArtistAward, ArtistAwardDto, ArtistAwardSimpleDto> {
}