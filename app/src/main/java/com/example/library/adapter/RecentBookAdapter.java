package com.example.library.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.library.BookDetailActivity;
import com.example.library.MyApplication;
import com.example.library.R;
import com.example.library.entity.TableBorrow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C)
 * FileName: RecentBookAdapter
 * Author: Amamiya
 * Date: 2019/12/26 18:55
 * Description: 最近借阅
 * History:
 */
public class RecentBookAdapter extends RecyclerView.Adapter{

    public static final String TABLEBORROWID = "TABLEBORROWID";
    public static final String SHELVESINFO = "SHELVESINFO";

    private List<TableBorrow> borrowList;

    private RecentBookAdapter.OnClickListener onClickListener;//回调接口


    public void addItem(TableBorrow tableBorrow) {
        addItem(0, tableBorrow);
    }

    public void addItem(int position, TableBorrow tableBorrow1){
        borrowList.add(position, tableBorrow1);
        notifyItemInserted(position);
        if (position != borrowList.size()) {
            notifyItemRangeChanged(position, borrowList.size() - position);
        }
    }

    public void setBorrowList(List<TableBorrow> borrowList){
        this.borrowList.clear();
        this.borrowList = borrowList;
    }

    public void removeItem(int position){
        borrowList.remove(position);
        notifyItemRemoved(position);
        if (position != borrowList.size()) {
            notifyItemRangeChanged(position, borrowList.size() - position);
        }
    }

    public RecentBookAdapter(List<TableBorrow> borrowList) {
        this.borrowList = borrowList;
    }

    public interface OnClickListener{
        void forDetail(Intent intent);
        void getPosition(int position);
    }

    public void setOnClickListener(RecentBookAdapter.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_recent_borrow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TableBorrow tableBorrow = borrowList.get(position);
        if(tableBorrow.getBook() != null){
            ((ViewHolder) holder).bookName.setText(tableBorrow.getBook().getName());
        }else{
            ((ViewHolder) holder).bookName.setText(R.string.homepage_query_error);//查询失败
        }
        ((ViewHolder) holder).text.setText(tableBorrow.getState());
        if(tableBorrow.getStateTableId() == TableBorrow.STATE_RETURN){
            ((ViewHolder) holder).text.setTextColor(MyApplication.getContext().getResources().getColor(R.color.color_main_black));
        }else if(tableBorrow.getStateTableId() == TableBorrow.STATE_BORROW){
            ((ViewHolder) holder).text.setTextColor(MyApplication.getContext().getResources().getColor(R.color.color_main_green));
        }else if(tableBorrow.getStateTableId() == TableBorrow.STATE_BOOKING){
            ((ViewHolder) holder).text.setTextColor(MyApplication.getContext().getResources().getColor(R.color.color_main_blue));
        }else if(tableBorrow.getStateTableId() == TableBorrow.STATE_DELAY){
            ((ViewHolder) holder).text.setTextColor(MyApplication.getContext().getResources().getColor(R.color.color_main_red));
        }else if(tableBorrow.getStateTableId() == TableBorrow.STATE_LIKE){
            ((ViewHolder) holder).text.setTextColor(MyApplication.getContext().getResources().getColor(R.color.color_main_orange));
        }

        ((ViewHolder) holder).btn_recent_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), BookDetailActivity.class);
                intent.putExtra(SHELVESINFO,tableBorrow.getShelvesInfo());
                intent.putExtra(TABLEBORROWID,String.valueOf(borrowList.get(position).getId()));//当前点击item的tableBorrowId
                onClickListener.forDetail(intent);//
                onClickListener.getPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView text;//右侧文本
        View btn_recent_book;//item按钮

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.textView_book_name);
            text = itemView.findViewById(R.id.textView_borrow_time);
            btn_recent_book = itemView.findViewById(R.id.item_recent_borrow);
        }
    }
}
