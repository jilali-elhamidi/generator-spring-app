package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PaymentMethod;
import com.example.modules.entertainment_ecosystem.dto.PaymentMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PaymentMethodSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PaymentMethodMapper extends BaseMapper<PaymentMethod, PaymentMethodDto, PaymentMethodSimpleDto> {
}