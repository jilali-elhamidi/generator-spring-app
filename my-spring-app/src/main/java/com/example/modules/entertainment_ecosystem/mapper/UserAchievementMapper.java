package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.dto.UserAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserAchievementSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserAchievementMapper extends BaseMapper<UserAchievement, UserAchievementDto, UserAchievementSimpleDto> {
}