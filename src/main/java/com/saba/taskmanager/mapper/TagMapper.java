package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.TagRequestDto;
import com.saba.taskmanager.dto.TagResponseDto;
import com.saba.taskmanager.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toEntity(TagRequestDto tagRequestDto) {
        Tag tag = new Tag();
        tag.setName(tagRequestDto.getName());
        return tag;
    }

    public TagResponseDto toResponseDto(Tag tag) {
        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setId(tag.getId());
        tagResponseDto.setName(tag.getName());
        return tagResponseDto;
    }
}