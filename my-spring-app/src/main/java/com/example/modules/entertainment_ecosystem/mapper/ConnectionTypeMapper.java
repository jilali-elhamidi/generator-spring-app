package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.dto.ConnectionTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConnectionTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ConnectionTypeMapper {

    ConnectionTypeMapper INSTANCE = Mappers.getMapper(ConnectionTypeMapper.class);

    ConnectionTypeDto toDto(ConnectionType connectiontype);

    ConnectionTypeSimpleDto toSimpleDto(ConnectionType connectiontype);

    @InheritInverseConfiguration
    ConnectionType toEntity(ConnectionTypeDto connectiontypeDto);

    List<ConnectionTypeDto> toDtoList(List<ConnectionType> connectiontypeList);

    List<ConnectionType> toEntityList(List<ConnectionTypeDto> connectiontypeDtoList);

    void updateEntityFromDto(ConnectionTypeDto dto, @MappingTarget ConnectionType entity);

}