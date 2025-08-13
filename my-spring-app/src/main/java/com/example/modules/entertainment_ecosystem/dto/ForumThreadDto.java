package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumThreadDto {

    private Long id;

    private String title;

    private Date creationDate;

    private Date lastPostDate;

    private UserProfileSimpleDto author;

    private List<ForumPostSimpleDto> forumPosts;

    private ForumCategorySimpleDto category;

}