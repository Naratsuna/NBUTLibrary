package com.example.library.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Copyright (C)
 * FileName: Position
 * Author: Amamiya
 * Date: 2019/12/30 9:32
 * Description: 书架地理位置表
 * History:
 */
public class Position extends LitePalSupport {
    private int id;
    private String position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
