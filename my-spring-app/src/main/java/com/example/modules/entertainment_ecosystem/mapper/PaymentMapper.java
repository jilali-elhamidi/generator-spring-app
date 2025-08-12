package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.dto.PaymentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto toDto(Payment payment);

    PaymentSimpleDto toSimpleDto(Payment payment);

    @InheritInverseConfiguration
    Payment toEntity(PaymentDto paymentDto);

    List<PaymentDto> toDtoList(List<Payment> paymentList);

    List<Payment> toEntityList(List<PaymentDto> paymentDtoList);

}