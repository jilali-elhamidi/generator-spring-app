package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.social_media.dtosimple.PostSimpleDto;

import com.example.modules.social_media.dtosimple.CommentSimpleDto;

import com.example.modules.social_media.dtosimple.MessageSimpleDto;

import com.example.modules.social_media.dtosimple.MessageSimpleDto;

import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.ProfileSimpleDto;

import com.example.modules.social_media.dtosimple.RoleSimpleDto;

import com.example.modules.social_media.dtosimple.GroupSimpleDto;

import com.example.modules.social_media.dtosimple.NotificationSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;

    private String name;

    private String bio;

    private String handle;

    private String email;

    private Date registrationDate;

    private List<PostSimpleDto> posts;

    private List<CommentSimpleDto> comments;

    private List<MessageSimpleDto> receivedMessages;

    private List<MessageSimpleDto> sentMessages;

    private List<ProfileSimpleDto> followers;

    private List<ProfileSimpleDto> following;

    private List<RoleSimpleDto> roles;

    private List<GroupSimpleDto> groups;

    private List<NotificationSimpleDto> notifications;

}