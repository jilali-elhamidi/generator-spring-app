package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.dto.ManagerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ManagerSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    ManagerDto toDto(Manager manager);

    ManagerSimpleDto toSimpleDto(Manager manager);

    @InheritInverseConfiguration
    Manager toEntity(ManagerDto managerDto);

    List<ManagerDto> toDtoList(List<Manager> managerList);

    List<Manager> toEntityList(List<ManagerDto> managerDtoList);

}