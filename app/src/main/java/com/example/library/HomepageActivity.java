package com.example.library;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.utils.Utils;
import com.example.library.adapter.RecentBookAdapter;
import com.example.library.collector.ActivityCollector;
import com.example.library.entity.TableBorrow;
import com.example.library.entity.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomepageActivity extends BaseActivity implements View.OnClickListener{

    public static final String TAG = "HomepageActivity";
    public static final int PERMISSION_WRITE = 1;
    final static private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };//需要的权限


    private RecentBookAdapter adapter;
    private String count;
    private int position;//recycleView点击的位置

    private RecyclerView recent;
    private TextView no_recent;
    private TextView userName;
    private TextView info;
    private TextView borrowInfo;
    private TextView debt;
    private TextView stuId;
    private LinearLayout logout;
    private View btn_library;
    private View btn_my;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Utils.ImmersionStatusBar(this,R.id.view_homepage);
        count = MyApplication.getCount();
        initView();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HomepageActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    private List<TableBorrow> getData(String count){
        List<TableBorrow> borrowList;
        borrowList = LitePal
                .where("stuId = ?",count)
                .order("dateBorrow desc")//按借书时间降序排序
                .limit(10)//取前十个
                .find(TableBorrow.class);
        return borrowList;
    }
    /**
     * 初始化view
     */
    private void initView(){
        no_recent = findViewById(R.id.textView_no_history);
        userName = findViewById(R.id.username);
        info = findViewById(R.id.user_simple_info);
        borrowInfo = findViewById(R.id.borrow_state_num);
        debt = findViewById(R.id.textView_debt);
        stuId = findViewById(R.id.textView_stuId);
        logout = findViewById(R.id.layout_logout);
        btn_library = findViewById(R.id.button_library);
        btn_my = findViewById(R.id.button_my_book);
        logout.setOnClickListener(this);
        btn_library.setOnClickListener(this);
        btn_my.setOnClickListener(this);
        avatar = findViewById(R.id.imageView_avatar);

        recent = findViewById(R.id.recyclerView_recent);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recent.setLayoutManager(manager);
        adapter = new RecentBookAdapter(getData(count));
        adapter.setOnClickListener(onClickListener);
        recent.setAdapter(adapter);

        //无借阅提示
        if(adapter.getItemCount() == 0){
            no_recent.setVisibility(View.VISIBLE);
        }

        User user = LitePal.where("stuId = ?",MyApplication.getCount())
                .find(User.class).get(0);
        userName.setText(user.getName());
        info.setText(new StringBuffer(user.getCollege()).append(" ").append(user.getDiscipline()));
//        Log.d(TAG, "initView: 已借"+user.getBooksBorrow());
        borrowInfo.append(user.getBooksBorrow()+ "/"+  user.getBooksAll());
        debt.append(user.getDebt() +"元");
        stuId.append(user.getStuId());

        if(user.getGentle().equals("女")){
            avatar.setImageDrawable(getDrawable(R.mipmap.defarult_avatar_girl));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_logout:
                ActivityCollector.finishAll();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.button_library:
                checkPermission();//动态申请权限
                break;
            case R.id.button_my_book:
                startActivity(new Intent(this,MyBookActivity.class));
                break;
            default:
                break;
        }
    }

    RecentBookAdapter.OnClickListener onClickListener = new RecentBookAdapter.OnClickListener() {
        @Override
        public void forDetail(Intent intent) {
            intent.putExtra("tag",TAG);
            startActivity(intent);
        }

        @Override
        public void getPosition(int position) {

        }
    };

    private void checkPermission(){
        List<String> result = new ArrayList<>();//传递被拒绝的权限
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) != PackageManager.PERMISSION_GRANTED){
                result.add(permission);//传递被拒绝的权限
            }
        }
        if(!result.isEmpty()) {
            ActivityCompat.requestPermissions(this, result.toArray(new String[permissions.length]), PERMISSION_WRITE);
        }else {
            startActivity(new Intent(this,BorrowActivity.class));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_WRITE:
                //判断是否权限全部允许
                if(grantResults.length > 0){
                    for(int i=0;i<permissions.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                startActivity(new Intent(this,BorrowActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        List<TableBorrow> tableBorrows = getData(count);
        Log.d(TAG, "onPostResume: "+tableBorrows.size());
        if (tableBorrows.size() != adapter.getItemCount()) {
            adapter.setBorrowList(tableBorrows);
//            recent.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
