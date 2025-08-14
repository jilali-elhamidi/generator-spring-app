package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.dto.TransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TransactionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toDto(Transaction transaction);

    TransactionSimpleDto toSimpleDto(Transaction transaction);

    @InheritInverseConfiguration
    Transaction toEntity(TransactionDto transactionDto);

    List<TransactionDto> toDtoList(List<Transaction> transactionList);

    List<Transaction> toEntityList(List<TransactionDto> transactionDtoList);

    void updateEntityFromDto(TransactionDto dto, @MappingTarget Transaction entity);

}