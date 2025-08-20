package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCharacterDto {

    private Long id;

    private String name;

    private String description;

    private List<BookSimpleDto> books;

}