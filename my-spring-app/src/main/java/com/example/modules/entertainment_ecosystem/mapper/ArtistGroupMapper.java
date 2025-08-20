package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.dto.ArtistGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistGroupSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ArtistGroupMapper {

    ArtistGroupMapper INSTANCE = Mappers.getMapper(ArtistGroupMapper.class);

    ArtistGroupDto toDto(ArtistGroup artistgroup);

    ArtistGroupSimpleDto toSimpleDto(ArtistGroup artistgroup);

    @InheritInverseConfiguration
    ArtistGroup toEntity(ArtistGroupDto artistgroupDto);

    List<ArtistGroupDto> toDtoList(List<ArtistGroup> artistgroupList);

    List<ArtistGroup> toEntityList(List<ArtistGroupDto> artistgroupDtoList);

    void updateEntityFromDto(ArtistGroupDto dto, @MappingTarget ArtistGroup entity);

}