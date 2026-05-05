package com.saba.taskmanager.dto;

public class TagResponseDto {

    private Long id;
    private String name;

    public TagResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}