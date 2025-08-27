package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.dto.UserFollowerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowerSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserFollowerMapper extends BaseMapper<UserFollower, UserFollowerDto, UserFollowerSimpleDto> {
}