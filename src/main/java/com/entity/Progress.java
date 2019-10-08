package com.entity;

import java.util.Date;
import java.util.List;

public class Progress {
    private Integer id;

    private Integer projectId;

    private String content;

    private String statusText;

    private String tailAfterAt;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String afterUserIds;

    private String afterUserNames;

    private String prinUserNames;

    private String prinUserIds;

    private List<TailAfter> tailAfters;

    private String userId;

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", content='" + content + '\'' +
                ", statusText='" + statusText + '\'' +
                ", tailAfterAt='" + tailAfterAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", afterUserIds='" + afterUserIds + '\'' +
                ", afterUserNames='" + afterUserNames + '\'' +
                ", prinUserNames='" + prinUserNames + '\'' +
                ", prinUserIds='" + prinUserIds + '\'' +
                ", tailAfters=" + tailAfters +
                '}';
    }

    public List<TailAfter> getTailAfters() {
        return tailAfters;
    }

    public void setTailAfters(List<TailAfter> tailAfters) {
        this.tailAfters = tailAfters;
    }

    public String getPrinUserNames() {
        return prinUserNames;
    }

    public void setPrinUserNames(String prinUserNames) {
        this.prinUserNames = prinUserNames;
    }

    public String getPrinUserIds() {
        return prinUserIds;
    }

    public void setPrinUserIds(String prinUserIds) {
        this.prinUserIds = prinUserIds;
    }

    public String getAfterUserIds() {
        return afterUserIds;
    }

    public void setAfterUserIds(String afterUserIds) {
        this.afterUserIds = afterUserIds;
    }

    public String getAfterUserNames() {
        return afterUserNames;
    }

    public void setAfterUserNames(String afterUserNames) {
        this.afterUserNames = afterUserNames;
    }


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText == null ? null : statusText.trim();
    }

    public String getTailAfterAt() {
        return tailAfterAt;
    }

    public void setTailAfterAt(String tailAfterAt) {
        this.tailAfterAt = tailAfterAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}