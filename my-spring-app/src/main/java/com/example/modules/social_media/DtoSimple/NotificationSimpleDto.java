package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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