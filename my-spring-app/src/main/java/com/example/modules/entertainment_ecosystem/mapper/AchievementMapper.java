package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.dto.AchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AchievementSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AchievementMapper extends BaseMapper<Achievement, AchievementDto, AchievementSimpleDto> {
}