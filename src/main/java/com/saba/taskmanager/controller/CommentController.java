package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.CommentRequestDto;
import com.saba.taskmanager.dto.CommentResponseDto;
import com.saba.taskmanager.entity.Comment;
import com.saba.taskmanager.mapper.CommentMapper;
import com.saba.taskmanager.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public CommentResponseDto createComment(@PathVariable Long taskId,
                                            @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.toEntity(commentRequestDto);
        Comment savedComment = commentService.createComment(taskId, commentRequestDto.getUserId(), comment);
        return commentMapper.toResponseDto(savedComment);
    }

    @GetMapping
    public List<CommentResponseDto> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId)
                .stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }
}