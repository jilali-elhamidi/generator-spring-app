package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPostDto {

    private Long id;

    private String content;

    private Date postDate;

    private UserProfileSimpleDto author;

    private ForumThreadSimpleDto thread;

    private List<ForumPostSimpleDto> replies;

    private ForumPostSimpleDto parentPost;

}