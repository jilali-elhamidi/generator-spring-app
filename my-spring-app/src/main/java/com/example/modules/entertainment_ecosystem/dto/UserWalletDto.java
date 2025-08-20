package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TransactionSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletDto {

    private Long id;

    private Double balance;

    private UserProfileSimpleDto user;

    private List<TransactionSimpleDto> transactions;

}