package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.dto.ClientDto;
import com.example.modules.project_management.dtosimple.ClientSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto toDto(Client client);

    ClientSimpleDto toSimpleDto(Client client);

    @InheritInverseConfiguration
    Client toEntity(ClientDto clientDto);

    List<ClientDto> toDtoList(List<Client> clientList);

    List<Client> toEntityList(List<ClientDto> clientDtoList);

    void updateEntityFromDto(ClientDto dto, @MappingTarget Client entity);

}