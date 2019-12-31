package com.example.library;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.library.adapter.BorrowAdapter;
import com.example.library.adapter.ResultAdapter;
import com.example.library.entity.Book;
import com.example.library.entity.ShelvesInfo;
import com.example.library.utils.SpacesItemDecoration;
import com.example.library.utils.Utils;

import org.litepal.LitePal;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C)
 * FileName: SearchActivity
 * Author: Amamiya
 * Date: 2019/12/29 15:16
 * Description: 搜索
 * History:
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "SearchActivity";
    public static final int SPANCOUNT = 3;//列数


    private View btn_search;
    private RecyclerView rv_result;
    private EditText editText_keyword;
    private TextView no_result;
    private TextView title;
    private ResultAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Utils.ImmersionStatusBar(this,R.id.view_search);
        btn_search = findViewById(R.id.ic_search);
        btn_search.setOnClickListener(this);
        editText_keyword = findViewById(R.id.editText_keyword);
        rv_result = findViewById(R.id.recyclerView_search_result);
        no_result = findViewById(R.id.textView_no_result);
        title = findViewById(R.id.textView_title_result);

        GridLayoutManager manager = new GridLayoutManager(this,SPANCOUNT);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if(position == 0||position == 1){
//                    return SPANCOUNT;
//                }
//                return 1;
//            }
//        });
        rv_result.setLayoutManager(manager);
        adapter = new ResultAdapter();
        rv_result.setAdapter(adapter);
        if(rv_result.getItemDecorationCount() == 0){
            float space = (Utils.getScreenWidth()-(getResources().getDimension(R.dimen.book_width)*SPANCOUNT)-getResources().getDimension(R.dimen.margin_main_h)*2)/(SPANCOUNT-1);
            Log.d(TAG, "initView: "+ Utils.getScreenWidth() + " " + space + " " + getResources().getDimension(R.dimen.book_width) + " " + getResources().getDimension(R.dimen.margin_main_h));
            rv_result.addItemDecoration(new SpacesItemDecoration(SPANCOUNT,(int) space,(int)getResources().getDimension(R.dimen.margin_main_v),false,0));//没有头部
        }
        adapter.setOnClickListener(onClickListener);//回调接口
    }

    private List<Book> getBookByKeyword() {
        List<Book> bookList = LitePal.where("name like ?","%"+editText_keyword.getText().toString()+"%").find(Book.class);
        Log.d(TAG, "getBookByKeyword: " + bookList.size() + " " + editText_keyword.getText().toString());
        return bookList;
    }

    BorrowAdapter.OnClickListener onClickListener = new BorrowAdapter.OnClickListener() {
        @Override
        public void forDetail(Intent intent) {
            startActivity(intent);
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_search:
                title.setVisibility(View.VISIBLE);
                if(getBookByKeyword().size() == 0){
                    no_result.setVisibility(View.VISIBLE);
                }else{
                    no_result.setVisibility(View.GONE);
                    adapter.setBookList(getBookByKeyword());
//                rv_result.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
