package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;


    import com.example.modules.social_media.dto.ProfileDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

private Long id;


    private String type;

    private String content;

    private Date creationDate;

    private Boolean isRead;



    
            private ProfileDto recipient;
    


    }