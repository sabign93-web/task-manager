package com.saba.taskmanager.dto;

public class TaskChartItemDto {

    private String label;
    private long count;

    public TaskChartItemDto() {
    }

    public TaskChartItemDto(String label, long count) {
        this.label = label;
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}