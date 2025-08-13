package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumCategoryDto {

    private Long id;

    private String name;

    private List<ForumThreadSimpleDto> threads;

    private ForumCategorySimpleDto parentCategory;

    private List<ForumCategorySimpleDto> childCategories;

}