package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


    import com.example.modules.social_media.dto.ProfileDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

private Long id;

    private String name;

    private List<ProfileSimpleDto> profiles;

}