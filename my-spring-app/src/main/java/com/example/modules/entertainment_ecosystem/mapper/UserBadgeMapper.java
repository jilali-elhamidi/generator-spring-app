package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.dto.UserBadgeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBadgeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserBadgeMapper extends BaseMapper<UserBadge, UserBadgeDto, UserBadgeSimpleDto> {
}