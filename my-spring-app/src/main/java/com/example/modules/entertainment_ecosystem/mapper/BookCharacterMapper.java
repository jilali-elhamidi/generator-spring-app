package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.dto.BookCharacterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookCharacterSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookCharacterMapper {

    BookCharacterMapper INSTANCE = Mappers.getMapper(BookCharacterMapper.class);

    BookCharacterDto toDto(BookCharacter bookcharacter);

    BookCharacterSimpleDto toSimpleDto(BookCharacter bookcharacter);

    @InheritInverseConfiguration
    BookCharacter toEntity(BookCharacterDto bookcharacterDto);

    List<BookCharacterDto> toDtoList(List<BookCharacter> bookcharacterList);

    List<BookCharacter> toEntityList(List<BookCharacterDto> bookcharacterDtoList);

    void updateEntityFromDto(BookCharacterDto dto, @MappingTarget BookCharacter entity);

}