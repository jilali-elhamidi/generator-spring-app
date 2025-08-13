package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.dto.ArtistSocialMediaDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSocialMediaSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ArtistSocialMediaMapper {

    ArtistSocialMediaMapper INSTANCE = Mappers.getMapper(ArtistSocialMediaMapper.class);

    ArtistSocialMediaDto toDto(ArtistSocialMedia artistsocialmedia);

    ArtistSocialMediaSimpleDto toSimpleDto(ArtistSocialMedia artistsocialmedia);

    @InheritInverseConfiguration
    ArtistSocialMedia toEntity(ArtistSocialMediaDto artistsocialmediaDto);

    List<ArtistSocialMediaDto> toDtoList(List<ArtistSocialMedia> artistsocialmediaList);

    List<ArtistSocialMedia> toEntityList(List<ArtistSocialMediaDto> artistsocialmediaDtoList);

}