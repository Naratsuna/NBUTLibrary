package com.example.library.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.library.MyApplication;
import com.example.library.R;
import com.example.library.entity.TableBorrow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C)
 * FileName: HistoryBorrowAdapter
 * Author: Amamiya
 * Date: 2019/12/31 16:14
 * Description: 历史借阅
 * History:
 */
public class HistoryBorrowAdapter extends RecyclerView.Adapter<HistoryBorrowAdapter.ViewHolder>{
    private static final String TAG = "HistoryBorrowAdapter";

    List<TableBorrow> borrowList;

    public HistoryBorrowAdapter(List<TableBorrow> borrowList) {
        this.borrowList = borrowList;
    }

    public void updateItem(TableBorrow tableBorrow,int position){
        borrowList.remove(position);
        borrowList.add(position,tableBorrow);
        notifyItemRangeChanged(position,1);
    }

    private HistoryBorrowAdapter.OnClickListener onClickListener;//回调接口

    @NonNull
    @Override
    public HistoryBorrowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryBorrowAdapter.ViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_history,parent,false));
    }

    public interface OnClickListener{
        String renewBook(TableBorrow tableBorrow);
    }

    public void setOnClickListener(HistoryBorrowAdapter.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TableBorrow temp = borrowList.get(position);
        holder.bookState.setText(temp.getState());
        holder.bookName.setText(temp.getBook().getName());
        holder.borrowTime.setText(temp.getDateBorrow());
        holder.returnTime.setText(temp.getDateRe());//只要知道还书时间就行了
        holder.item_bg.setBackground(getDiffColor(position));
        holder.btn_renew.setTextColor(getTextDiffColor(position));
        holder.btn_renew.setVisibility(temp.isRenewable()?View.VISIBLE:View.GONE);//是否显示续借按钮
        holder.btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ temp + "position:"+position);
                String newDate = onClickListener.renewBook(temp);//续借操作
                //回调后这里仍然可以执行
                if(!borrowList.get(position).getDateRe().equals(newDate)){//日期不同时才更新
                    borrowList.get(position).setDateRe(newDate);
                    Log.d(TAG, "onClick: "+ newDate);
                    notifyItemChanged(position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookState;
        TextView bookName;
        TextView borrowTime;
        TextView returnTime;
        TextView renewable;
        Button btn_renew;
        View item_bg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookState = itemView.findViewById(R.id.book_state);
            bookName = itemView.findViewById(R.id.book_name);
            borrowTime = itemView.findViewById(R.id.book_borrow_time);
            returnTime = itemView.findViewById(R.id.book_return_time);
            renewable = itemView.findViewById(R.id.book_renewable);
            btn_renew = itemView.findViewById(R.id.btn_renew);
            item_bg = itemView.findViewById(R.id.item_history_bg);//背景颜色
        }
    }

    private Drawable getDiffColor(int position){
        //获得随机渐变背景和按钮颜色
        int color = position % 5 + 1;
        switch (color){
            case 1:
                return MyApplication.getContext().getResources().getDrawable(R.drawable.shape_ret_grd_blue,null);
            case 2:
                return MyApplication.getContext().getResources().getDrawable(R.drawable.shape_ret_grd_black,null);
            case 3:
                return MyApplication.getContext().getResources().getDrawable(R.drawable.shape_ret_grd_orange,null);
            case 4:
                return MyApplication.getContext().getResources().getDrawable(R.drawable.shape_ret_grd_red,null);
            default:
                return MyApplication.getContext().getResources().getDrawable(R.drawable.shape_ret_grd_green,null);
        }
    }

    private int getTextDiffColor(int position){
        //获得随机渐变背景和按钮颜色
        int color = position % 5 + 1;
        switch (color){
            case 1:
                return MyApplication.getContext().getResources().getColor(R.color.color_main_blue_deep);
            case 2:
                return MyApplication.getContext().getResources().getColor(R.color.color_deep_gray);
            case 3:
                return MyApplication.getContext().getResources().getColor(R.color.color_main_orange_deep);
            case 4:
                return MyApplication.getContext().getResources().getColor(R.color.color_grd_red_end);
            default:
                return MyApplication.getContext().getResources().getColor(R.color.color_main_green_deep);
        }
    }
}
