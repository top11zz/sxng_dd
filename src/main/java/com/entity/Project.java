package com.entity;

import java.util.Date;
import java.util.List;

public class Project {
    private Integer id;

    private String number; //项目编号

    private String name; //项目主体

    private String describe; //描述

    private String note; //说明

    private String createUserId;  //创建人

    private String createUserName; //创建人名字

    private Integer tips; //标签

    private String createdAt; //创建时间

    private String updatedAt; //更新时间

    private String deletedAt; //删除时间

    private String palUserIds;

    private String afterUserIds;

    private List<FirstLinkman> firstLinkmen;

    private List<Principal> principals;

    private String status;

    private String userId;

    private String tipName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FirstLinkman> getFirstLinkmen() {
        return firstLinkmen;
    }

    public void setFirstLinkmen(List<FirstLinkman> firstLinkmen) {
        this.firstLinkmen = firstLinkmen;
    }

    public List<Principal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<Principal> principals) {
        this.principals = principals;
    }

    public String getPalUserIds() {
        return palUserIds;
    }

    public void setPalUserIds(String palUserIds) {
        this.palUserIds = palUserIds;
    }

    public String getAfterUserIds() {
        return afterUserIds;
    }

    public void setAfterUserIds(String afterUserIds) {
        this.afterUserIds = afterUserIds;
    }

    private String palName;

    private String afterName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
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

    public String getPalName() {
        return palName;
    }

    public void setPalName(String palName) {
        this.palName = palName;
    }

    public String getAfterName() {
        return afterName;
    }

    public void setAfterName(String afterName) {
        this.afterName = afterName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", note='" + note + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", tips='" + tips + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", palUserIds='" + palUserIds + '\'' +
                ", afterUserIds='" + afterUserIds + '\'' +
                ", firstLinkmen=" + firstLinkmen +
                ", principals=" + principals +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", palName='" + palName + '\'' +
                ", afterName='" + afterName + '\'' +
                '}';
    }
}