package com.example.modules.social_media.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSimpleDto {
    private Long id;

    private String type;

    private String content;

    private Date creationDate;

    private Boolean isRead;

}