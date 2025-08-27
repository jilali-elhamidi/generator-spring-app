package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.dto.PaymentDto;
import com.example.modules.ecommerce.dtosimple.PaymentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentDto, PaymentSimpleDto> {
}