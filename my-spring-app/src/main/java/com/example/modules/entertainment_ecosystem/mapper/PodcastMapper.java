package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.dto.PodcastDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PodcastMapper extends BaseMapper<Podcast, PodcastDto, PodcastSimpleDto> {
}