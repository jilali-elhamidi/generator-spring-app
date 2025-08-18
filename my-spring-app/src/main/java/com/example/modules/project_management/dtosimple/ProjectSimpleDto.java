package com.example.modules.project_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSimpleDto {

    private Long id;

    private String projectName;

    private String description;

    private Date startDate;

    private Date deadline;

    private String status;

}