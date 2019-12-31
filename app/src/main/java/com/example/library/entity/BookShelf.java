package com.example.library.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Copyright (C)
 * FileName: BookShelf
 * Author: Amamiya
 * Date: 2019/12/30 9:23
 * Description: 书架表
 * History:
 */
public class BookShelf extends LitePalSupport {
    private int id;
    private String bookShelfInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookShelfInfo() {
        return bookShelfInfo;
    }

    public void setBookShelfInfo(String bookShelfInfo) {
        this.bookShelfInfo = bookShelfInfo;
    }
}
