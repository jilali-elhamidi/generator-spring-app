package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.PostSimpleDto;

import com.example.modules.social_media.dtosimple.CommentSimpleDto;

import com.example.modules.social_media.dtosimple.CommentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String content;

    private Date commentDate;

    private ProfileSimpleDto author;

    private PostSimpleDto post;

    private CommentSimpleDto parentComment;

    private List<CommentSimpleDto> replies;

}