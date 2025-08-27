package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.dto.EpisodeCreditDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeCreditSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EpisodeCreditMapper extends BaseMapper<EpisodeCredit, EpisodeCreditDto, EpisodeCreditSimpleDto> {
}