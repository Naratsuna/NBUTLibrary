package com.example.library.entity;

import com.example.library.MyApplication;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {

    private int id;

    private String name;

    private String college;// 学院

    private String discipline;// 专业

    private String gentle;//性别

    @Column(unique = true,defaultValue = "-1")
    private String stuId;// 学号

    private String password;

    private String tableBorrowId;//借书表

    private int historyBorrowedCount;//历史借书数

    @Column(defaultValue = "false")
    boolean block;//黑名单

    @Column(defaultValue = "10")
    int booksAll;// 共总可借数量

    @Column(defaultValue = "0")
    int booksBorrow;// 已借数量

    @Column(defaultValue = "0")
    double debt;//欠费金额

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTableBorrowId() {
        return tableBorrowId;
    }

    public void setTableBorrowId(String tableBorrowId) {
        this.tableBorrowId = tableBorrowId;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public int getBooksAll() {
        return booksAll;
    }

    public void setBooksAll(int booksAll) {
        this.booksAll = booksAll;
    }

    public int getBooksBorrow() {
        booksBorrow = LitePal.where("stateTableId in (?,?,?) and stuId = ?"
                ,String.valueOf(TableBorrow.STATE_BORROW),String.valueOf(TableBorrow.STATE_DELAY),String.valueOf(TableBorrow.STATE_RETURN), MyApplication.getCount())
                .find(TableBorrow.class).size();
        return booksBorrow;
    }

    public void setBooksBorrow(int booksBorrow) {
        this.booksBorrow = booksBorrow;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public String getGentle() {
        return gentle;
    }

    public void setGentle(String gentle) {
        this.gentle = gentle;
    }

    public int getHistoryBorrowedCount() {
        return historyBorrowedCount;
    }

    public void setHistoryBorrowedCount(int historyBorrowedCount) {
        this.historyBorrowedCount = historyBorrowedCount;
    }
}
