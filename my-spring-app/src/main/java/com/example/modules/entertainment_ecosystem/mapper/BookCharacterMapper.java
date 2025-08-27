package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.dto.BookCharacterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookCharacterSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface BookCharacterMapper extends BaseMapper<BookCharacter, BookCharacterDto, BookCharacterSimpleDto> {
}