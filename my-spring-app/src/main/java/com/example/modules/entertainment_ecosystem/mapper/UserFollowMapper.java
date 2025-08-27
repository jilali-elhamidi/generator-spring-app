package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.dto.UserFollowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserFollowMapper extends BaseMapper<UserFollow, UserFollowDto, UserFollowSimpleDto> {
}