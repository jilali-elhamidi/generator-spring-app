package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSimpleDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Date registrationDate;

    private String country;

    private String profilePictureUrl;

    private Date lastActiveDate;

    private String userStatus;

    private String bio;

}