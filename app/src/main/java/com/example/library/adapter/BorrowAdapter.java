package com.example.library.adapter;

import android.content.Intent;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.example.library.BookDetailActivity;
import com.example.library.loader.GlideImageLoader;
import com.example.library.MyApplication;
import com.example.library.R;
import com.example.library.utils.Utils;
import com.example.library.entity.ShelvesInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
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
public class BorrowAdapter extends RecyclerView.Adapter{

    private static final String TAG = "BorrowAdapter";

    private List<ShelvesInfo> shelvesInfos;
    private List<String> titles;

    private OnClickListener onClickListener;

    public static final int VIEW_BANNER = 1;
    public static final int VIEW_BOOK = 2;
    public static final int VIEW_TITLE = 3;
    public static final String SHELVESINFO = "SHELVESINFO";

    public BorrowAdapter(List<ShelvesInfo> shelvesInfos, List<String> titles) {
        this.shelvesInfos = shelvesInfos;
        this.titles = titles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_BOOK)
            return new ViewHolder_book(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_book,parent,false));
        else if(viewType == VIEW_BANNER)
            return new ViewHolder_banner(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_banner,parent,false));
        else
            return new ViewHolder_title(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_title,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder_book){
            final ShelvesInfo temp = shelvesInfos.get(position-titles.size()-1);
//            Log.d(TAG, "onBindViewHolder: "+position+" "+titles.size()+" "+getItemCount()+" "+shelvesInfos.size());
            if(temp.getBook() != null){
                ((ViewHolder_book) holder).bookName.setText(temp.getBook().getName());
            }else{
                ((ViewHolder_book) holder).bookName.setText(R.string.homepage_query_error);//查询失败
            }
            ((ViewHolder_book) holder).btn_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getContext(), BookDetailActivity.class);
//                    intent.putExtra(BOOK,books.get(0));
                    intent.putExtra(SHELVESINFO,temp);
                    onClickListener.forDetail(intent);
                }
            });
        }else if(holder instanceof ViewHolder_banner){
            ((ViewHolder_banner) holder).banner.start();
        }else if(holder instanceof ViewHolder_title){
            ((ViewHolder_title) holder).title.setText(titles.get(position-1));
        }
    }

    public interface OnClickListener{
        void forDetail(Intent intent);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return shelvesInfos.size()+titles.size()+1;
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_BANNER ;
        } else if(position == 1){
            return VIEW_TITLE;
        }else {
            return VIEW_BOOK;
        }
    }

    static class ViewHolder_book extends RecyclerView.ViewHolder {
        TextView bookName;
        View btn_book;

        public ViewHolder_book(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.textView_book_name);
            btn_book = itemView.findViewById(R.id.item_book);
        }
    }
    static class ViewHolder_title extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder_title(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
        }
    }

    static class ViewHolder_banner extends RecyclerView.ViewHolder {
        Banner banner;

        public ViewHolder_banner(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_view);
            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(getBannerData());
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
            //设置轮播时间
            banner.setDelayTime(4000);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), Utils.dip2px(15));//布局裁剪
                }
            });
            banner.setClipToOutline(true);
            //banner设置方法全部调用完毕时最后调用
//            banner.start();
        }

        //安卓9.0禁用http
        private List<String> getBannerData(){
            final List<String> imagesUrl = new ArrayList<>();
            imagesUrl.add("https://raw.githubusercontent.com/Naratsuna/images/master/pic_1.png");
            imagesUrl.add("https://raw.githubusercontent.com/Naratsuna/images/master/pic_2.jpg");
            imagesUrl.add("https://raw.githubusercontent.com/Naratsuna/images/master/pic_3.png");
            imagesUrl.add("https://raw.githubusercontent.com/Naratsuna/images/master/pic_4.png");
            return imagesUrl;
        }
    }


}
