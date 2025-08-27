package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.dto.EpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EpisodeMapper extends BaseMapper<Episode, EpisodeDto, EpisodeSimpleDto> {
}