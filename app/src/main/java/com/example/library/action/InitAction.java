package com.example.library.action;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.library.MyApplication;
import com.example.library.entity.Book;
import com.example.library.entity.BookShelf;
import com.example.library.entity.PeriodSetting;
import com.example.library.entity.Position;
import com.example.library.entity.ShelvesInfo;
import com.example.library.entity.StateTable;
import com.example.library.entity.TableBorrow;
import com.example.library.entity.User;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Copyright (C)
 * FileName: InitAction
 * Author: Amamiya
 * Date: 2019/12/26 9:36
 * Description: 初始化配置
 * History:
 */
public class InitAction {
    private static final String TAG = "InitAction";
    public static final int INIT_USER = 1;
    public static final int INIT_BOOK = 2;
    public static final int INIT_TABLEBORROW = 3;
    public static final int INIT_SHELVESINFO = 4;
    public static final int INIT_STATE = 5;
    public static final int INIT_POSITION = 6;
    public static final int INIT_BOOKSHELF = 7;
    public static final int INIT_PERIOD = 8;

    public static void getDatabase(){
        LitePal.getDatabase();
        Log.d(TAG, "getDatabase: "+SharePrefAction.read());
        if(SharePrefAction.read().isEmpty()){
            Log.d(TAG, "getDatabase: 初始化");
            initData();
        }
    }

    /**123456
     * @description 初始化数据
     * @date: 2019/12/26 10:09
     */
    public static void initData(){
        try {
            AssetManager manager = MyApplication.getContext().getAssets();
            load(manager.open("users.txt"),INIT_USER);
            load(manager.open("books.txt"),INIT_BOOK);
            load(manager.open("shelves_info.txt"),INIT_SHELVESINFO);
            load(manager.open("table_borrow.txt"),INIT_TABLEBORROW);
            load(manager.open("state.txt"),INIT_STATE);
            load(manager.open("position.txt"),INIT_POSITION);
            load(manager.open("bookshelf.txt"),INIT_BOOKSHELF);
            load(manager.open("period.txt"),INIT_PERIOD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String load(InputStream in,int state){
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine())!= null){
                content.append(line+"\n");
                if(!"".equals(line)){
                    switch (state){
                        case INIT_USER:
                            initUser(line);
                            break;
                        case INIT_SHELVESINFO:
                            initShelvesinfo(line);
                            break;
                        case INIT_TABLEBORROW:
                            initTableborrow(line);
                            break;
                        case INIT_BOOK:
                            initBook(line);
                            break;
                        case INIT_STATE:
                            initState(line);
                            break;
                        case INIT_POSITION:
                            initPosition(line);
                            break;
                        case INIT_BOOKSHELF:
                            initBookshelf(line);
                            break;
                        case INIT_PERIOD:
                            initPeriod(line);
                            break;
                        default:
                            break;
                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    //OK
    private static void initUser(String line){
        String[] list = line.split(",");
        User user = new User();
        user.setStuId(list[0]);
        user.setName(list[1]);
        user.setCollege(list[2]);
        user.setDiscipline(list[3]);
        user.setPassword(list[4]);
        user.setGentle(list[5]);
        user.setHistoryBorrowedCount(Integer.valueOf(list[6]));
        user.setBooksAll(10);
        user.setBooksBorrow(0);
        user.save();
    }

    //ok
    public static void initBook(String line) {
        String[] list = line.split(",");
//        Log.d(TAG, "initBook: "+list);
        Book book = new Book();
        book.setName(list[0]);
        book.setAuthor(list[1]);
        book.setPublish(list[2]);
        book.setPrice(Double.valueOf(list[3]));
        book.setIsbn(list[4]);
        book.setIntro(list[5]);
//        book.setId(Integer.valueOf(list[6]));
        book.save();
    }

    //ok
    private static void initTableborrow(String line){
        String[] list = line.split(",");
        TableBorrow tableBorrow = new TableBorrow();
        tableBorrow.setStuId(list[0]);
        tableBorrow.setBookId(Integer.valueOf(list[1]));
        tableBorrow.setDateBorrow(list[2]);
        tableBorrow.setDateRe(list[3]);
        tableBorrow.setRealDateRe(list[4]);
        tableBorrow.setStateTableId(Integer.valueOf(list[5]));
        tableBorrow.setPeriodSettingId(Integer.valueOf(list[6]));
        tableBorrow.save();
    }

    //OK
    private static void initShelvesinfo(String line){
        String[] list = line.split(",");
        ShelvesInfo info = new ShelvesInfo();
        info.setBookId(Integer.valueOf(list[0]));
        info.setBookShelfId(Integer.valueOf(list[1]));
        info.setPositionId(Integer.valueOf(list[2]));
        info.setStateTableId(Integer.valueOf(list[3]));
        info.setLastReUser(list[4]);
        info.setLastReTime(list[5]);
        info.save();
    }

    private static void initState(String line){
        Log.d(TAG, "initState: "+line);
        String[] list = line.split(",");
        StateTable stateTable = new StateTable();
        stateTable.setStateId(Integer.valueOf(list[0]));
        stateTable.setState(list[1]);
        stateTable.save();
    }

    private static void initPosition(String line){
        String[] list = line.split(",");
        Position temp = new Position();
        temp.setId(Integer.valueOf(list[0]));
        temp.setPosition(list[1]);
        temp.save();
    }

    private static void initBookshelf(String line){
        String[] list = line.split(",");
        BookShelf temp = new BookShelf();
        temp.setId(Integer.valueOf(list[0]));
        temp.setBookShelfInfo(list[1]);
        temp.save();
    }

    private static void initPeriod(String line){
        String[] list = line.split(",");
        PeriodSetting temp = new PeriodSetting();
        temp.setId(Integer.valueOf(list[0]));
        temp.setReturnPeriod(Integer.valueOf(list[1]));
        temp.setRenewPeriod(Integer.valueOf(list[2]));
        temp.save();
    }
}
