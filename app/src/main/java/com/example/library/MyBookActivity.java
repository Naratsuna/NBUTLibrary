package com.example.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.adapter.HistoryBorrowAdapter;
import com.example.library.adapter.RecentBookAdapter;
import com.example.library.entity.Book;
import com.example.library.entity.TableBorrow;
import com.example.library.entity.User;
import com.example.library.utils.SpacesItemDecoration;
import com.example.library.utils.Utils;

import org.litepal.LitePal;

import java.util.List;

public class MyBookActivity extends AppCompatActivity {

    private static final String TAG = "MyBookActivity";
    private TextView textView_count_borrowed;
    private TextView textView_count_now;
    private TextView textView_debt;
    private View no_history;
    private RecyclerView history;
    private HistoryBorrowAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        Utils.ImmersionStatusBar(this,R.id.view_my_book);
        user = LitePal.where("stuId = ?",MyApplication.getCount()).find(User.class).get(0);
        initView();
    }

    private List<TableBorrow> getData(){
        List<TableBorrow> borrowList;
        borrowList = LitePal
                .where("stuId = ?",MyApplication.getCount())
                .order("dateBorrow desc")//按借书时间降序
                .find(TableBorrow.class);
        return borrowList;
    }

    HistoryBorrowAdapter.OnClickListener onClickListener = new HistoryBorrowAdapter.OnClickListener() {
        @Override
        public String renewBook(TableBorrow tableBorrow) {
            if(tableBorrow.isRenewable()){
                String newDate = Utils.getDateAfterAddDays(tableBorrow.getDateRe(),tableBorrow.getPeriodRenew());
                ContentValues cv = new ContentValues();
                cv.put("dateRe",newDate);//加上续借之后的日期
                LitePal.updateAll(TableBorrow.class,cv,"bookId = ?",String.valueOf(tableBorrow.getBookId()));
                Toast.makeText(MyBookActivity.this, "续借成功", Toast.LENGTH_SHORT).show();
                return newDate;
            }else{
                Toast.makeText(MyBookActivity.this, "每本书只能续借一次！", Toast.LENGTH_SHORT).show();
                return tableBorrow.getDateRe();
            }
        }
    };

    private void initView(){
        textView_count_borrowed = findViewById(R.id.textView_count_book_borrowed);
        textView_count_now = findViewById(R.id.textView_count_book_borrowed_now);
        textView_debt = findViewById(R.id.textView_debt);
        no_history = findViewById(R.id.textView_no_history);
        history = findViewById(R.id.recyclerView_history);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        history.setLayoutManager(manager);
        adapter = new HistoryBorrowAdapter(getData());
        adapter.setOnClickListener(onClickListener);
//        if(history.getItemDecorationCount() == 0){
//            history.addItemDecoration(new SpacesItemDecoration(1, 0,(int)getResources().getDimension(R.dimen.margin_main_v),false,0));//两个头部
//        }
        history.setAdapter(adapter);
        //无借阅提示
        if(adapter.getItemCount() == 0){
            no_history.setVisibility(View.VISIBLE);
        }
        textView_count_borrowed.setText(String.valueOf(user.getHistoryBorrowedCount()));
        textView_count_now.setText(String.valueOf(user.getBooksBorrow()));
        textView_debt.setText(String.valueOf(user.getDebt()));
    }
}
