package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewCommentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyDto {

    private Long id;

    private String commentText;

    private Date replyDate;

    private UserProfileSimpleDto user;

    private ReviewCommentSimpleDto reviewComment;

}