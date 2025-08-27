package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.dto.PodcastCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastCategorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PodcastCategoryMapper extends BaseMapper<PodcastCategory, PodcastCategoryDto, PodcastCategorySimpleDto> {
}