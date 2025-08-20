package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.dto.ProfileDto;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    ProfileDto toDto(Profile profile);

    ProfileSimpleDto toSimpleDto(Profile profile);

    @InheritInverseConfiguration
    Profile toEntity(ProfileDto profileDto);

    List<ProfileDto> toDtoList(List<Profile> profileList);

    List<Profile> toEntityList(List<ProfileDto> profileDtoList);

    void updateEntityFromDto(ProfileDto dto, @MappingTarget Profile entity);

}