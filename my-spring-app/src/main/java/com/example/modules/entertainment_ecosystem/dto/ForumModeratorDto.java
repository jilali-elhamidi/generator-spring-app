package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumModeratorDto {

    private Long id;

    private Date moderatorSince;

    private UserProfileSimpleDto user;

    private List<ForumCategorySimpleDto> moderatedCategories;

}