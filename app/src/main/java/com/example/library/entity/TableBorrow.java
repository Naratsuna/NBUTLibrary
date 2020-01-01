package com.example.library.entity;

import android.util.Log;
import com.example.library.utils.Utils;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TableBorrow extends LitePalSupport {

    private static final String TAG = "TableBorrow";
    public static final int STATE_RETURN = 11;//已还书
    public static final int STATE_BORROW = 12;//借阅中
    public static final int STATE_DELAY = 13;//逾期
    public static final int STATE_BOOKING = 14;//预订
    public static final int STATE_LIKE = 15;//想看

    private int id;
    private String stuId;
    private int bookId;
    private int stateTableId;//借阅状态
    private int periodSettingId;//周期设置
    private String dateRe;
    private String realDateRe;
    private String dateBorrow;//所有的时间都是由服务器提供
//    private boolean renewable;//是否可续借

    public boolean isRenewable() {
        //在借并且借期一个月
        return Utils.getMonthsBetweenTwoDate(dateBorrow, dateRe) == 1&&stateTableId == STATE_BORROW;
    }

    public String getState(){
//        Log.d(TAG, "getState: "+ stateTableId);
        List<StateTable> temp = LitePal.where("stateId = ?",String.valueOf(stateTableId)).find(StateTable.class);
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
            return null;
        }
    }

    public ShelvesInfo getShelvesInfo(){
        List<ShelvesInfo> temp = LitePal.where("bookId = ?",String.valueOf(bookId))
                .find(ShelvesInfo.class);
        if(temp.size() != 0){
            return temp.get(0);
        }else{
            return null;
        }
    }

    public int getPeriodReturn(){
        List<PeriodSetting> temp = LitePal.select("returnPeriod").where("id = ?",String.valueOf(periodSettingId)).find(PeriodSetting.class);
        if(temp.size() != 0){
            return temp.get(0).getReturnPeriod();
        }else{
            return -1;
        }
    }

    public int getPeriodRenew(){
        List<PeriodSetting> temp = LitePal.select("renewPeriod").where("id = ?",String.valueOf(periodSettingId)).find(PeriodSetting.class);
        if(temp.size() != 0){
            return temp.get(0).getRenewPeriod();
        }else{
            return -1;
        }
    }

    public int getStateTableId() {
        return stateTableId;
    }

    public void setStateTableId(int stateTableId) {
        this.stateTableId = stateTableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDateRe() {
        return dateRe;
    }

    public void setDateRe(String dateRe) {
        this.dateRe = dateRe;
    }

    public String getRealDateRe() {
        return realDateRe == null?"":realDateRe;
    }

    public void setRealDateRe(String realDateRe) {
        this.realDateRe = realDateRe;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
        this.dateRe = Utils.getDateAfterAddDays(dateBorrow,getPeriodReturn());
        Log.d(TAG, "setDateBorrow: 还书时间" + dateRe);
    }

    public int getPeriodSettingId() {
        return periodSettingId;
    }

    public void setPeriodSettingId(int periodSettingId) {
        this.periodSettingId = periodSettingId;
    }
}
