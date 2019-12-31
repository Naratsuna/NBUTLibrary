package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.library.utils.SpacesItemDecoration;
import com.example.library.utils.Utils;
import com.example.library.adapter.BorrowAdapter;
import com.example.library.entity.ShelvesInfo;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BorrowActivity extends BaseActivity {
    private static final String TAG = "BorrowActivity";
    public static final int spanCount = 3;//列数
    private ImageView imageView;
    private View btn_search;

    private List<String> titles = new ArrayList<>(Arrays.asList("热门推荐"));


    private RecyclerView rv_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        Utils.ImmersionStatusBar(this,R.id.view_borrow);
        initView();
    }

    private void initView(){
        btn_search = findViewById(R.id.bar_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApplication.getContext(),SearchActivity.class));
            }
        });

        rv_book = findViewById(R.id.recyclerView_book_list);
        GridLayoutManager manager = new GridLayoutManager(this,spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0||position == 1){
                    return spanCount;
                }
                return 1;
            }
        });
        rv_book.setLayoutManager(manager);
        BorrowAdapter adapter = new BorrowAdapter(getShelvesInfoData(),titles);
        rv_book.setAdapter(adapter);
        if(rv_book.getItemDecorationCount() == 0){
            float space = (Utils.getScreenWidth()-(getResources().getDimension(R.dimen.book_width)*spanCount)-getResources().getDimension(R.dimen.margin_main_h)*2)/(spanCount-1);
            Log.d(TAG, "initView: "+ Utils.getScreenWidth() + " " + space + " " + getResources().getDimension(R.dimen.book_width) + " " + getResources().getDimension(R.dimen.margin_main_h));
            rv_book.addItemDecoration(new SpacesItemDecoration(spanCount,(int) space,(int)getResources().getDimension(R.dimen.margin_main_v),false,titles.size()+1));//两个头部
        }
        adapter.setOnClickListener(onClickListener);//回调接口
    }

    BorrowAdapter.OnClickListener onClickListener = new BorrowAdapter.OnClickListener() {
        @Override
        public void forDetail(Intent intent) {
            startActivity(intent);
        }
    };

    private List<ShelvesInfo> getShelvesInfoData(){
        return LitePal.findAll(ShelvesInfo.class);
    }
}
