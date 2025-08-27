package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.dto.UserLevelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLevelSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserLevelMapper extends BaseMapper<UserLevel, UserLevelDto, UserLevelSimpleDto> {
}