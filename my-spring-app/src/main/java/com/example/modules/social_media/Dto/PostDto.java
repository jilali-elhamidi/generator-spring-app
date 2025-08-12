package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.CommentSimpleDto;

import com.example.modules.social_media.dtosimple.TagSimpleDto;

import com.example.modules.social_media.dtosimple.MediaFileSimpleDto;

import com.example.modules.social_media.dtosimple.GroupSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String content;

    private Date postDate;

    private ProfileSimpleDto author;

    private List<CommentSimpleDto> comments;

    private List<TagSimpleDto> tags;

    private List<MediaFileSimpleDto> media;

    private GroupSimpleDto group;

}