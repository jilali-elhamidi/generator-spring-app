package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportedContentDto {

    private Long id;

    private String reason;

    private Date reportDate;

    private String status;

    private UserProfileSimpleDto user;

    private ReviewSimpleDto review;

    private ForumPostSimpleDto forumPost;

}