package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.dto.GameAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameAchievementMapper extends BaseMapper<GameAchievement, GameAchievementDto, GameAchievementSimpleDto> {
}