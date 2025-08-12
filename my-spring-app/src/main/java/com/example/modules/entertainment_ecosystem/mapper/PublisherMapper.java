package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.dto.PublisherDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherDto toDto(Publisher publisher);

    PublisherSimpleDto toSimpleDto(Publisher publisher);

    @InheritInverseConfiguration
    Publisher toEntity(PublisherDto publisherDto);

    List<PublisherDto> toDtoList(List<Publisher> publisherList);

    List<Publisher> toEntityList(List<PublisherDto> publisherDtoList);

}