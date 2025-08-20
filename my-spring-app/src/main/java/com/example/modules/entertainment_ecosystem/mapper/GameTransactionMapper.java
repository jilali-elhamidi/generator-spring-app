package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.dto.GameTransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameTransactionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameTransactionMapper {

    GameTransactionMapper INSTANCE = Mappers.getMapper(GameTransactionMapper.class);

    GameTransactionDto toDto(GameTransaction gametransaction);

    GameTransactionSimpleDto toSimpleDto(GameTransaction gametransaction);

    @InheritInverseConfiguration
    GameTransaction toEntity(GameTransactionDto gametransactionDto);

    List<GameTransactionDto> toDtoList(List<GameTransaction> gametransactionList);

    List<GameTransaction> toEntityList(List<GameTransactionDto> gametransactionDtoList);

    void updateEntityFromDto(GameTransactionDto dto, @MappingTarget GameTransaction entity);

}