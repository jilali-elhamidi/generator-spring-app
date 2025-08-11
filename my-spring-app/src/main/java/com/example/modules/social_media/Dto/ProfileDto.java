package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import java.util.List;
        import com.example.modules.social_media.dto.PostSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.CommentSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.MessageSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.MessageSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.RoleSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.GroupSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.NotificationSimpleDto;
    


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