package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.dto.PaymentMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentMethodSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    PaymentMethodDto toDto(PaymentMethod paymentmethod);

    PaymentMethodSimpleDto toSimpleDto(PaymentMethod paymentmethod);

    @InheritInverseConfiguration
    PaymentMethod toEntity(PaymentMethodDto paymentmethodDto);

    List<PaymentMethodDto> toDtoList(List<PaymentMethod> paymentmethodList);

    List<PaymentMethod> toEntityList(List<PaymentMethodDto> paymentmethodDtoList);

    void updateEntityFromDto(PaymentMethodDto dto, @MappingTarget PaymentMethod entity);

}