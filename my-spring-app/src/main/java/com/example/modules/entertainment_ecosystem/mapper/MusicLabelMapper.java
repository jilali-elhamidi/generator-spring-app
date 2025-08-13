package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.dto.MusicLabelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicLabelSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MusicLabelMapper {

    MusicLabelMapper INSTANCE = Mappers.getMapper(MusicLabelMapper.class);

    MusicLabelDto toDto(MusicLabel musiclabel);

    MusicLabelSimpleDto toSimpleDto(MusicLabel musiclabel);

    @InheritInverseConfiguration
    MusicLabel toEntity(MusicLabelDto musiclabelDto);

    List<MusicLabelDto> toDtoList(List<MusicLabel> musiclabelList);

    List<MusicLabel> toEntityList(List<MusicLabelDto> musiclabelDtoList);

}