package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.GameTransactionSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCurrencyDto {

    private Long id;

    private String name;

    private String symbol;

    private List<GameTransactionSimpleDto> transactions;

}