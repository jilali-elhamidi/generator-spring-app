package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.dto.BookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto toDto(Book book);

    BookSimpleDto toSimpleDto(Book book);

    @InheritInverseConfiguration
    Book toEntity(BookDto bookDto);

    List<BookDto> toDtoList(List<Book> bookList);

    List<Book> toEntityList(List<BookDto> bookDtoList);

}