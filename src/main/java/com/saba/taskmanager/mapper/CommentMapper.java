package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.CommentRequestDto;
import com.saba.taskmanager.dto.CommentResponseDto;
import com.saba.taskmanager.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        return comment;
    }

    public CommentResponseDto toResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setCreatedAt(comment.getCreatedAt());

        if (comment.getTask() != null) {
            commentResponseDto.setTaskId(comment.getTask().getId());
        }

        if (comment.getUser() != null) {
            commentResponseDto.setUserId(comment.getUser().getId());
            commentResponseDto.setUserName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
        }

        return commentResponseDto;
    }
}