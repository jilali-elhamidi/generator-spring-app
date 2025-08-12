package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


import java.util.List;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;

    private String roomNumber;

    private String type;

    private Integer capacity;


    private List<AppointmentSimpleDto> appointments;

}