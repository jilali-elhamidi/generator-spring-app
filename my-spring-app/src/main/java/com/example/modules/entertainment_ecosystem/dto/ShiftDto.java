package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDto {

    private Long id;

    private Date shiftDate;

    private Date startTime;

    private Date endTime;

    private EmployeeSimpleDto employee;

}