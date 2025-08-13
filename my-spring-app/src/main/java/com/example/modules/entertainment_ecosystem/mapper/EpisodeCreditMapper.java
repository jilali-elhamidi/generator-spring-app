package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.dto.EpisodeCreditDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeCreditSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EpisodeCreditMapper {

    EpisodeCreditMapper INSTANCE = Mappers.getMapper(EpisodeCreditMapper.class);

    EpisodeCreditDto toDto(EpisodeCredit episodecredit);

    EpisodeCreditSimpleDto toSimpleDto(EpisodeCredit episodecredit);

    @InheritInverseConfiguration
    EpisodeCredit toEntity(EpisodeCreditDto episodecreditDto);

    List<EpisodeCreditDto> toDtoList(List<EpisodeCredit> episodecreditList);

    List<EpisodeCredit> toEntityList(List<EpisodeCreditDto> episodecreditDtoList);

}