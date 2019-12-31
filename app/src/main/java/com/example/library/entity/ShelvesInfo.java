package com.example.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * Copyright (C)
 * FileName: ShelvesInfo
 * Author: Amamiya
 * Date: 2019/12/26 8:57
 * Description: 上架信息
 * History:
 */
public class ShelvesInfo extends LitePalSupport implements Parcelable {

    public static final int STATE_AVALIABLE = 1;//在库
    public static final int STATE_BORROWED = 2;//外借
    public static final int STATE_BOOKING = 3;//预订

    private int id;
    private int bookId;
    private int stateTableId;//借阅状态
    private int bookShelfId;//书架号 xxx-xx-x
    private int positionId;//书位置 1A
    private String lastReTime;//上次归还
    private String lastReUser;//上次读者

    protected ShelvesInfo(Parcel in) {
        id = in.readInt();
        bookId = in.readInt();
        stateTableId = in.readInt();
        positionId = in.readInt();
        bookShelfId = in.readInt();
        lastReTime = in.readString();
        lastReUser = in.readString();
    }

    public ShelvesInfo() {
    }

    public static final Creator<ShelvesInfo> CREATOR = new Creator<ShelvesInfo>() {
        @Override
        public ShelvesInfo createFromParcel(Parcel in) {
            return new ShelvesInfo(in);
        }

        @Override
        public ShelvesInfo[] newArray(int size) {
            return new ShelvesInfo[size];
        }
    };

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getStateTableId() {
        return stateTableId;
    }

    public void setStateTableId(int stateTableId) {
        this.stateTableId = stateTableId;
    }

    public boolean isBorrowable(){
        //是否可借，需要根据数据库来
        return stateTableId != STATE_BORROWED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getPosition(){
        List<Position> temp = LitePal.select("position").where("id = ?",String.valueOf(positionId)).find(Position.class);
        if(temp.size() != 0){
            return temp.get(0).getPosition();
        }else{
            return "";
        }
    }

    public String getBookShelfInfo(){
        List<BookShelf> temp = LitePal.select("bookShelfInfo").where("id = ?",String.valueOf(bookShelfId)).find(BookShelf.class);
        if(temp.size() != 0){
            return temp.get(0).getBookShelfInfo();
        }else{
            return "";
        }
    }

    public String getState(){
        List<StateTable> temp = LitePal.select("state").where("id = ?",String.valueOf(stateTableId)).find(StateTable.class);
        if(temp.size() != 0){
            return temp.get(0).getState();
        }else{
            return "出错";
        }
    }

    public Book getBook(){
        List<Book> temp = LitePal.where("id = ?",String.valueOf(bookId)).find(Book.class);
        if(temp.size() != 0){
            return temp.get(0);
        }else{
            throw new NullPointerException();
        }
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getLastReTime() {
        return lastReTime;
    }

    public void setLastReTime(String lastReTime) {
        this.lastReTime = lastReTime;
    }

    public String getLastReUser() {
        return lastReUser;
    }

    public void setLastReUser(String lastReUser) {
        this.lastReUser = lastReUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getBookShelfId() {
        return bookShelfId;
    }

    public void setBookShelfId(int bookShelfId) {
        this.bookShelfId = bookShelfId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bookId);
        dest.writeInt(stateTableId);
        dest.writeInt(positionId);
        dest.writeInt(bookShelfId);
        dest.writeString(lastReTime);
        dest.writeString(lastReUser);
    }
}
