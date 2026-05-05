package com.saba.taskmanager.service;

import com.saba.taskmanager.entity.Tag;
import com.saba.taskmanager.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag) {
        tagRepository.findByName(tag.getName())
                .ifPresent(existingTag -> {
                    throw new RuntimeException("Tag already exists with name: " + tag.getName());
                });

        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}