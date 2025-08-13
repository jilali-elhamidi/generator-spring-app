package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.dto.ContractDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContractSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    ContractDto toDto(Contract contract);

    ContractSimpleDto toSimpleDto(Contract contract);

    @InheritInverseConfiguration
    Contract toEntity(ContractDto contractDto);

    List<ContractDto> toDtoList(List<Contract> contractList);

    List<Contract> toEntityList(List<ContractDto> contractDtoList);

}