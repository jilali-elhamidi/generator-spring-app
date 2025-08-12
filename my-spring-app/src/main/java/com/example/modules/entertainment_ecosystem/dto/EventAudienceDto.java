package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;





@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventAudienceDto {

    private Long id;

    private Integer count;

    private String audienceType;

    

}