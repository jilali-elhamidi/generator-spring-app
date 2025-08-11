package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSimpleDto {
private Long id;

    private String name;

    private String bio;

    private String handle;

    private String email;

    private Date registrationDate;

}