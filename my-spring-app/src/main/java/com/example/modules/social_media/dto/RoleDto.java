package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



    
        import java.util.List;
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

private Long id;


    private String name;



    
            private List<ProfileSimpleDto> profiles;
    

    }