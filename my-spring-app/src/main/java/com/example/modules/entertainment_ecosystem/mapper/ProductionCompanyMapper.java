package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.dto.ProductionCompanyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ProductionCompanySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductionCompanyMapper {

    ProductionCompanyMapper INSTANCE = Mappers.getMapper(ProductionCompanyMapper.class);

    ProductionCompanyDto toDto(ProductionCompany productioncompany);

    ProductionCompanySimpleDto toSimpleDto(ProductionCompany productioncompany);

    @InheritInverseConfiguration
    ProductionCompany toEntity(ProductionCompanyDto productioncompanyDto);

    List<ProductionCompanyDto> toDtoList(List<ProductionCompany> productioncompanyList);

    List<ProductionCompany> toEntityList(List<ProductionCompanyDto> productioncompanyDtoList);

    void updateEntityFromDto(ProductionCompanyDto dto, @MappingTarget ProductionCompany entity);

}