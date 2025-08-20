package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistGroupDto {

    private Long id;

    private String name;

    private Date formationDate;

    private List<ArtistSimpleDto> members;

}