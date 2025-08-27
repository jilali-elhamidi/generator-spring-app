package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.dto.UserConnectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserConnectionMapper extends BaseMapper<UserConnection, UserConnectionDto, UserConnectionSimpleDto> {
}