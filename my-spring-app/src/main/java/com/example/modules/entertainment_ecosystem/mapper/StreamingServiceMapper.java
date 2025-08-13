package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.dto.StreamingServiceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingServiceSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface StreamingServiceMapper {

    StreamingServiceMapper INSTANCE = Mappers.getMapper(StreamingServiceMapper.class);

    StreamingServiceDto toDto(StreamingService streamingservice);

    StreamingServiceSimpleDto toSimpleDto(StreamingService streamingservice);

    @InheritInverseConfiguration
    StreamingService toEntity(StreamingServiceDto streamingserviceDto);

    List<StreamingServiceDto> toDtoList(List<StreamingService> streamingserviceList);

    List<StreamingService> toEntityList(List<StreamingServiceDto> streamingserviceDtoList);

}