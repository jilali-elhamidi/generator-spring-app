package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSimpleDto {
private Long id;

    private String content;

    private Date commentDate;

}