package com.entity;

public class Principal {
    private Integer id;

    private Integer projectId;

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
        return "Principal{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}