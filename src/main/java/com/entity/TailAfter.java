package com.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

public class TailAfter {
    private Integer id;

    private Integer projectId;

    private Integer progressId;

    private String userId;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "TailAfter{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", progressId=" + progressId +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}