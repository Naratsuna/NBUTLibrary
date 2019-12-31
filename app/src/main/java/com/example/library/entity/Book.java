package com.example.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Copyright (C)
 * FileName: Book
 * Author: Amamiya
 * Date: 2019/12/26 8:43
 * Description: 书
 * History:
 */
public class Book extends LitePalSupport implements Parcelable {

    private int id;
    private String isbn;//书编号
    private String name;
    private String publish;//出版社
    private String author;//作者
    private double price;
    private String intro;//简介

    private Book(Parcel in) {
        id = in.readInt();
        isbn = in.readString();
        name = in.readString();
        publish = in.readString();
        author = in.readString();
        price = in.readDouble();
        intro = in.readString();
    }

    public Book() {
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(isbn);
        dest.writeString(name);
        dest.writeString(publish);
        dest.writeString(author);
        dest.writeDouble(price);
        dest.writeString(intro);
    }
}
