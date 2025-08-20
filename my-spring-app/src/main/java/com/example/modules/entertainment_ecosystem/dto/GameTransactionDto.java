package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameCurrencySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTransactionDto {

    private Long id;

    private Double amount;

    private Date transactionDate;

    private String description;

    private UserProfileSimpleDto user;

    private GameCurrencySimpleDto currency;

}