package com.example.library.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.library.BookDetailActivity;
import com.example.library.MyApplication;
import com.example.library.R;
import com.example.library.entity.Book;
import com.example.library.entity.ShelvesInfo;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C)
 * FileName: ResultAdapter
 * Author: Amamiya
 * Date: 2019/12/30 20:29
 * Description: 搜索结果的适配器
 * History:
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private List<Book> bookList;
    private BorrowAdapter.OnClickListener onClickListener;
    public static final String BOOK = "BOOK";
    public static final String SHELVESINFO = "SHELVESINFO";


    public ResultAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public ResultAdapter() {
        this.bookList = new ArrayList<>();
    }

    public interface OnClickListener{
        void forDetail(Intent intent);
    }

    public void setOnClickListener(BorrowAdapter.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_book,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bookName.setText(bookList.get(position).getName());
        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShelvesInfo temp = LitePal.where("bookId = ?",String.valueOf(bookList.get(position).getId())).find(ShelvesInfo.class).get(0);
                Intent intent = new Intent(MyApplication.getContext(), BookDetailActivity.class);
                intent.putExtra(SHELVESINFO,temp);//上架信息
                onClickListener.forDetail(intent);
            }
        });
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList.clear();
        this.bookList = bookList;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        View btn_book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.textView_book_name);
            btn_book = itemView.findViewById(R.id.item_book);
        }
    }
}
