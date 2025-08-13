package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.dto.UserPreferenceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPreferenceSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserPreferenceMapper {

    UserPreferenceMapper INSTANCE = Mappers.getMapper(UserPreferenceMapper.class);

    UserPreferenceDto toDto(UserPreference userpreference);

    UserPreferenceSimpleDto toSimpleDto(UserPreference userpreference);

    @InheritInverseConfiguration
    UserPreference toEntity(UserPreferenceDto userpreferenceDto);

    List<UserPreferenceDto> toDtoList(List<UserPreference> userpreferenceList);

    List<UserPreference> toEntityList(List<UserPreferenceDto> userpreferenceDtoList);

}