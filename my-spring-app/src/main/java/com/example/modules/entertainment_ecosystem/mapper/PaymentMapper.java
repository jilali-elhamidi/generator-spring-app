package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Payment;
import com.example.modules.entertainment_ecosystem.dto.PaymentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentDto, PaymentSimpleDto> {
}