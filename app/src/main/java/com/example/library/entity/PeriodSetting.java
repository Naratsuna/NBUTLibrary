package com.example.library.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Copyright (C)
 * FileName: PeriodSetting
 * Author: Amamiya
 * Date: 2019/12/30 9:15
 * Description: 还书续借周期设置
 * History:
 */
public class PeriodSetting extends LitePalSupport {
    private int id;
    private int returnPeriod;//还书周期
    private int renewPeriod;//续借周期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(int returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    public int getRenewPeriod() {
        return renewPeriod;
    }

    public void setRenewPeriod(int renewPeriod) {
        this.renewPeriod = renewPeriod;
    }
}
