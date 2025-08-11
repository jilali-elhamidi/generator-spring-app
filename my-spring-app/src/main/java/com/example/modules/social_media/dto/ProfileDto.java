package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

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
    private List<PostSimpleDto> posts; // <-- Utilise un DTO simple
    private List<CommentSimpleDto> comments; // <-- Utilise un DTO simple
    private List<MessageSimpleDto> receivedMessages; // <-- Utilise un DTO simple
    private List<MessageSimpleDto> sentMessages; // <-- Utilise un DTO simple
    private List<ProfileSimpleDto> followers;
    private List<ProfileSimpleDto> following;
    private List<RoleSimpleDto> roles;
    private List<GroupSimpleDto> groups; // <-- Utilise un DTO simple
    private List<NotificationDto> notifications;

}