package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.dto.UserActivityLogDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserActivityLogSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserActivityLogMapper extends BaseMapper<UserActivityLog, UserActivityLogDto, UserActivityLogSimpleDto> {
}