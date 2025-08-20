package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.dto.GroupDto;
import com.example.modules.social_media.dtosimple.GroupSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDto toDto(Group group);

    GroupSimpleDto toSimpleDto(Group group);

    @InheritInverseConfiguration
    Group toEntity(GroupDto groupDto);

    List<GroupDto> toDtoList(List<Group> groupList);

    List<Group> toEntityList(List<GroupDto> groupDtoList);

    void updateEntityFromDto(GroupDto dto, @MappingTarget Group entity);

}