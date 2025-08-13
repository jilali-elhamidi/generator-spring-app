package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.dto.ArtistAwardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistAwardSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ArtistAwardMapper {

    ArtistAwardMapper INSTANCE = Mappers.getMapper(ArtistAwardMapper.class);

    ArtistAwardDto toDto(ArtistAward artistaward);

    ArtistAwardSimpleDto toSimpleDto(ArtistAward artistaward);

    @InheritInverseConfiguration
    ArtistAward toEntity(ArtistAwardDto artistawardDto);

    List<ArtistAwardDto> toDtoList(List<ArtistAward> artistawardList);

    List<ArtistAward> toEntityList(List<ArtistAwardDto> artistawardDtoList);

}