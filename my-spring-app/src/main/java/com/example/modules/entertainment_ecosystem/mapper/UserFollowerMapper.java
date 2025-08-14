package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.dto.UserFollowerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowerSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserFollowerMapper {

    UserFollowerMapper INSTANCE = Mappers.getMapper(UserFollowerMapper.class);

    UserFollowerDto toDto(UserFollower userfollower);

    UserFollowerSimpleDto toSimpleDto(UserFollower userfollower);

    @InheritInverseConfiguration
    UserFollower toEntity(UserFollowerDto userfollowerDto);

    List<UserFollowerDto> toDtoList(List<UserFollower> userfollowerList);

    List<UserFollower> toEntityList(List<UserFollowerDto> userfollowerDtoList);

    void updateEntityFromDto(UserFollowerDto dto, @MappingTarget UserFollower entity);

}