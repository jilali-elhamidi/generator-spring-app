package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSimpleDto {
    private Long id;

    private String roomNumber;

    private String type;

    private Integer capacity;

}