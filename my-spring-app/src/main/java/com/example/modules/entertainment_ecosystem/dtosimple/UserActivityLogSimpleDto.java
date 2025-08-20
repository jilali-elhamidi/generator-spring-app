package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityLogSimpleDto {

    private Long id;

    private String activityType;

    private Date activityDate;

    private String details;

}