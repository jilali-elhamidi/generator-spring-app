package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionTypeDto {

    private Long id;

    private String name;

    private List<UserConnectionSimpleDto> userConnections;

}