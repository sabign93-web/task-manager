package com.saba.taskmanager.dto;

public class ReporterTaskSummaryDto {

    private Long reporterId;
    private String reporterName;
    private long taskCount;

    public ReporterTaskSummaryDto() {
    }

    public ReporterTaskSummaryDto(Long reporterId, String reporterName, long taskCount) {
        this.reporterId = reporterId;
        this.reporterName = reporterName;
        this.taskCount = taskCount;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }
}