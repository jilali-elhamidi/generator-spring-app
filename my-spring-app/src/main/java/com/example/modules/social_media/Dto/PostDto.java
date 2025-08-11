package com.example.modules.social_media.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;



    
        import com.example.modules.social_media.dto.ProfileSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.CommentSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.TagSimpleDto;
    

    
        import java.util.List;
        import com.example.modules.social_media.dto.MediaFileSimpleDto;
    

    
        import com.example.modules.social_media.dto.GroupSimpleDto;
    


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