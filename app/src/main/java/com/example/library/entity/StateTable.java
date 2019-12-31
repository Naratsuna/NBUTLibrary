package com.example.library.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Copyright (C)
 * FileName: StateTable
 * Author: Amamiya
 * Date: 2019/12/30 8:56
 * Description:借阅状态表，书架信息和借书表共用
 * History:
 */
public class StateTable extends LitePalSupport {
    private int id;
    private int stateId;//litepal无法取消自增长，因此id不是自增长需要新建
    private String state;

    public int getId() {
        return id;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
