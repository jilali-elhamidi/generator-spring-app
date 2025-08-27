package com.example.core.mapper;

import org.mapstruct.MappingTarget;
import java.util.List;

public interface BaseMapper<E, D, SD> {
    D toDto(E entity);
    SD toSimpleDto(E entity);
    E toEntity(D dto);

    List<D> toDtoList(List<E> entities);
    List<E> toEntityList(List<D> dtos);

    void updateEntityFromDto(D dto, @MappingTarget E entity);
}
