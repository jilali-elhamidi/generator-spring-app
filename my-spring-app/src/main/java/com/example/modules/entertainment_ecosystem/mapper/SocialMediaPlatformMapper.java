package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.dto.SocialMediaPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SocialMediaPlatformSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SocialMediaPlatformMapper {

    SocialMediaPlatformMapper INSTANCE = Mappers.getMapper(SocialMediaPlatformMapper.class);

    SocialMediaPlatformDto toDto(SocialMediaPlatform socialmediaplatform);

    SocialMediaPlatformSimpleDto toSimpleDto(SocialMediaPlatform socialmediaplatform);

    @InheritInverseConfiguration
    SocialMediaPlatform toEntity(SocialMediaPlatformDto socialmediaplatformDto);

    List<SocialMediaPlatformDto> toDtoList(List<SocialMediaPlatform> socialmediaplatformList);

    List<SocialMediaPlatform> toEntityList(List<SocialMediaPlatformDto> socialmediaplatformDtoList);

    void updateEntityFromDto(SocialMediaPlatformDto dto, @MappingTarget SocialMediaPlatform entity);

}