package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageSimpleDto {

    private Long id;

    private String subject;

    private String body;

    private Date sentDate;

    private Boolean isRead;

}