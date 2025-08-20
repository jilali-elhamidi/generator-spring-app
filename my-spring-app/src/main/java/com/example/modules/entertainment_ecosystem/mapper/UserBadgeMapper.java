package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.dto.UserBadgeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBadgeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserBadgeMapper {

    UserBadgeMapper INSTANCE = Mappers.getMapper(UserBadgeMapper.class);

    UserBadgeDto toDto(UserBadge userbadge);

    UserBadgeSimpleDto toSimpleDto(UserBadge userbadge);

    @InheritInverseConfiguration
    UserBadge toEntity(UserBadgeDto userbadgeDto);

    List<UserBadgeDto> toDtoList(List<UserBadge> userbadgeList);

    List<UserBadge> toEntityList(List<UserBadgeDto> userbadgeDtoList);

    void updateEntityFromDto(UserBadgeDto dto, @MappingTarget UserBadge entity);

}