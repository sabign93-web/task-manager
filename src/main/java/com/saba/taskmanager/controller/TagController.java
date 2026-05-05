package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.TagRequestDto;
import com.saba.taskmanager.dto.TagResponseDto;
import com.saba.taskmanager.entity.Tag;
import com.saba.taskmanager.mapper.TagMapper;
import com.saba.taskmanager.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @PostMapping
    public TagResponseDto createTag(@RequestBody TagRequestDto tagRequestDto) {
        Tag tag = tagMapper.toEntity(tagRequestDto);
        Tag savedTag = tagService.createTag(tag);
        return tagMapper.toResponseDto(savedTag);
    }

    @GetMapping
    public List<TagResponseDto> getAllTags() {
        return tagService.getAllTags()
                .stream()
                .map(tagMapper::toResponseDto)
                .toList();
    }
}