package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.dto.PodcastGuestDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastGuestSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PodcastGuestMapper extends BaseMapper<PodcastGuest, PodcastGuestDto, PodcastGuestSimpleDto> {
}