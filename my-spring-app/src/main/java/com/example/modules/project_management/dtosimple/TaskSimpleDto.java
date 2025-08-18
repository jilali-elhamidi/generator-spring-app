package com.example.modules.project_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSimpleDto {

    private Long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private String status;

}