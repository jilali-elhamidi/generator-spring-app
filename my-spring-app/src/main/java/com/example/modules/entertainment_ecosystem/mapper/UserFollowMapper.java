package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.dto.UserFollowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserFollowMapper {

    UserFollowMapper INSTANCE = Mappers.getMapper(UserFollowMapper.class);

    UserFollowDto toDto(UserFollow userfollow);

    UserFollowSimpleDto toSimpleDto(UserFollow userfollow);

    @InheritInverseConfiguration
    UserFollow toEntity(UserFollowDto userfollowDto);

    List<UserFollowDto> toDtoList(List<UserFollow> userfollowList);

    List<UserFollow> toEntityList(List<UserFollowDto> userfollowDtoList);

}