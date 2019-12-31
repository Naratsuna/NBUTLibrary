package com.example.library;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.adapter.RecentBookAdapter;
import com.example.library.utils.Utils;
import com.example.library.action.SharePrefAction;
import com.example.library.adapter.BorrowAdapter;
import com.example.library.entity.Book;
import com.example.library.entity.ShelvesInfo;
import com.example.library.entity.TableBorrow;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "BookDetailActivity";

    private Book book;
    private ShelvesInfo shelvesInfo;
    private List<TableBorrow> tableBorrowList;
    private boolean isBooking_in = false;//是否已经预订
    private boolean isLike_in = false;//是否已经想看
    private boolean isBooking_out = false;//通过进出状态的对比确定是否要操作数据库，防止重复操作数据库
    private boolean isLike_out = false;//

    private TextView book_name;
    private TextView book_author;
    private TextView book_publish;
    private TextView book_shelf;
    private TextView book_position;
    private TextView book_state;
    private TextView book_intro;
    private View button_like;
    private View button_booking;
    private TextView text_like;
    private TextView text_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Utils.ImmersionStatusBar(this,R.id.view_book_detail);
        try{
            shelvesInfo = getIntent().getParcelableExtra(BorrowAdapter.SHELVESINFO);
            book = shelvesInfo.getBook();
            tableBorrowList = LitePal.where("bookId = ? and stuId = ?",String.valueOf(book.getId()),MyApplication.getCount())
                    .find(TableBorrow.class);
            if(tableBorrowList.size() != 0) {
                //借书表中有此书时
                for (TableBorrow borrow : tableBorrowList) {
                    Log.d(TAG, "onCreate: " + borrow.getStateTableId());
                    if(borrow.getStateTableId() == TableBorrow.STATE_LIKE){
                        isLike_in = true;
                        isLike_out = true;
                    }
                    if(borrow.getStateTableId() == TableBorrow.STATE_BOOKING){
                        isBooking_in = true;
                        isBooking_out = true;
                    }
                }
                Log.d(TAG, "onCreate: "+ isLike_in + isLike_out + isBooking_in + isBooking_out);

            }
        }catch (NullPointerException e){
            Toast.makeText(this, "获取信息失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        initView();
    }

    private void initView(){
        book_name = findViewById(R.id.book_name);
        book_author = findViewById(R.id.book_author);
        book_publish = findViewById(R.id.book_publish);
        book_shelf = findViewById(R.id.book_shelf);
        book_position = findViewById(R.id.book_position);
        book_state = findViewById(R.id.book_state);
        book_intro = findViewById(R.id.book_intro);
        text_like = findViewById(R.id.textView_like);
        text_booking = findViewById(R.id.textView_booking);
        button_like = findViewById(R.id.button_like);
        button_booking = findViewById(R.id.button_booking);
        button_like.setOnClickListener(this);
        button_booking.setOnClickListener(this);

        book_name.setText(book.getName());
        book_author.setText(book.getAuthor());
        book_publish.setText(book.getPublish());
        book_shelf.append(shelvesInfo.getBookShelfInfo());
        book_position.append(shelvesInfo.getPosition());
        book_state.append(shelvesInfo.getState());
        book_intro.append(book.getIntro());
        if(isBooking_in){
            toggleText(text_booking,getString(R.string.book_booking_cancel));
        }
        if(isLike_in){
            toggleText(text_like,getString(R.string.book_like_cancel));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_like:
                if (!isLike_out) {
                    toggleText(text_like,getString(R.string.book_like_cancel));
                    Toast.makeText(this, "已添加", Toast.LENGTH_SHORT).show();
                    isLike_out = !isLike_out;
                } else {
                    toggleText(text_like,getString(R.string.book_like));
                    Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show();
                    isLike_out = !isLike_out;
                }
                break;
            case R.id.button_booking:
                if (!isBooking_out) {
                    if(shelvesInfo.getStateTableId() != ShelvesInfo.STATE_BORROWED) {//允许多人预订同一本书
                        toggleText(text_booking,getString(R.string.book_booking_cancel));
                        Toast.makeText(this, "预订成功", Toast.LENGTH_SHORT).show();
                        shelvesInfo.setStateTableId(ShelvesInfo.STATE_BOOKING);//内存中修改状态，页面重新加载时生效
                        toggleText(book_state,getString(R.string.book_borrow_state)+shelvesInfo.getState());
                        book_state.invalidate();//无效
                    }else{
                        Toast.makeText(this, "该书已借出", Toast.LENGTH_SHORT).show();
                    }
                    isBooking_out = !isBooking_out;
                } else {
                    toggleText(text_booking,getString(R.string.book_booking));
                    shelvesInfo.setStateTableId(ShelvesInfo.STATE_AVALIABLE);//内存中修改状态，页面重新加载时生效
                    toggleText(book_state,getString(R.string.book_borrow_state)+shelvesInfo.getState());
//                    book_state.invalidate();
                    Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show();
                    isBooking_out = !isBooking_out;
                }
                break;
        }
    }

    /**
     * @method
     * @description
     * @date: 2019/12/29 22:04
     * @author: Amamiya
     * @param stateTableId 借书表借阅状态
     * @param stateTableId_shelf 书架借阅状态
     * @return
     */
    private void insertBookData(int stateTableId,int stateTableId_shelf){
        Log.d(TAG, "insertBookData: " + stateTableId + " " + stateTableId_shelf);
        TableBorrow tableBorrow = new TableBorrow();
        tableBorrow.setBookId(book.getId());
        tableBorrow.setStuId(MyApplication.getCount());
        tableBorrow.setStateTableId(stateTableId);
        tableBorrow.setDateBorrow(getDate());
        tableBorrow.save();
        ContentValues cv = new ContentValues();
        cv.put("stateTableId",stateTableId_shelf);
        LitePal.updateAll(ShelvesInfo.class,cv,"id = ?",String.valueOf(shelvesInfo.getId()));
    }

    private void deleteBookData(int stateTableId,boolean updateShelvesInfo){
        Log.d(TAG, "deleteBookData: "+stateTableId+updateShelvesInfo);
        //不能只根据id来删除，一张表有多个一样的bookid，但是状态不同
        LitePal.deleteAll(TableBorrow.class,"bookId = ? and stateTableId = ?",String.valueOf(shelvesInfo.getBookId()),String.valueOf(stateTableId));//借书表删除
        //修改状态
        if(updateShelvesInfo){
            ContentValues cv = new ContentValues();
            cv.put("stateTableId",ShelvesInfo.STATE_AVALIABLE);
            LitePal.updateAll(ShelvesInfo.class,cv,"id = ?",String.valueOf(shelvesInfo.getId()));
        }
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        String dateString = formatter.format(currentTime);
//        dateString.replace("/0","/");
        return dateString;
    }

    /**
     * @method toggleBooking
     * @description 改变字体
     * @date: 2019/12/30 16:49
     * @author: Amamiya
     * @param
     * @return void
     */
    private void toggleText(View view, String text){
        //切换样式
        if(!((TextView)view).getText().equals(text)){
            ((TextView)view).setText(text);
        }
    }

    @Override
    protected void onPause() {
        //不管你怎么切换，我只在退出界面后进行数据库操作
        super.onPause();
        Log.d(TAG, "onPause;: "+ isLike_in + isLike_out + isBooking_in + isBooking_out);
        if(isBooking_in){
            if(!isBooking_out){
                //只有进来是true出去是false才删除
                deleteBookData(TableBorrow.STATE_BOOKING, true);
            }
        }else{
            if(isBooking_out){
                //只有进来是false出去是true才能预订
                insertBookData(TableBorrow.STATE_BOOKING,ShelvesInfo.STATE_BOOKING);
            }
        }
        if(isLike_in){
            if(!isLike_out) {
                //只有进来是true出去是false才删除
                deleteBookData(TableBorrow.STATE_LIKE, false);
            }
        }else{
            //进来是true，但是出去是false，那就删除
            if(isLike_out){
                //只有进来是false出去是true才能添加想看
                insertBookData(TableBorrow.STATE_LIKE,shelvesInfo.getStateTableId());
            }
            //进来是false，并且出去是false，那就什么都不做
        }
    }
}
