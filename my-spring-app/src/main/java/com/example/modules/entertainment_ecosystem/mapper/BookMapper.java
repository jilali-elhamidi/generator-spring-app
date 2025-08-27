package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.dto.BookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<Book, BookDto, BookSimpleDto> {
}