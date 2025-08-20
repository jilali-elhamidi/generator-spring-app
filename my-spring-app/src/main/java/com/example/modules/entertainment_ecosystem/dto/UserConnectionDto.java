package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ConnectionTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionDto {

    private Long id;

    private Date connectionDate;

    private UserProfileSimpleDto user1;

    private UserProfileSimpleDto user2;

    private ConnectionTypeSimpleDto type;

}