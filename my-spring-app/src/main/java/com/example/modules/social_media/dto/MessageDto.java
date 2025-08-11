package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    

    
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

private Long id;


    private String content;

    private Date sentDate;

    private Boolean read;



    
            private ProfileSimpleDto sender;
    

    
            private ProfileSimpleDto recipient;
    

    }