package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.dto.TVShowStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowStudioSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TVShowStudioMapper {

    TVShowStudioMapper INSTANCE = Mappers.getMapper(TVShowStudioMapper.class);

    TVShowStudioDto toDto(TVShowStudio tvshowstudio);

    TVShowStudioSimpleDto toSimpleDto(TVShowStudio tvshowstudio);

    @InheritInverseConfiguration
    TVShowStudio toEntity(TVShowStudioDto tvshowstudioDto);

    List<TVShowStudioDto> toDtoList(List<TVShowStudio> tvshowstudioList);

    List<TVShowStudio> toEntityList(List<TVShowStudioDto> tvshowstudioDtoList);

}