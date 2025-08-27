package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.dto.PodcastEpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastEpisodeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PodcastEpisodeMapper extends BaseMapper<PodcastEpisode, PodcastEpisodeDto, PodcastEpisodeSimpleDto> {
}