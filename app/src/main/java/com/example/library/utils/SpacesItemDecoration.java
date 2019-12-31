package com.example.library.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG="SpacesItemDecoration";

    private int spanCount;
    private int spacing;
    private int spacingH;
    private boolean includeEdge;
    private int headerCount;//头部的数量，用于忽略不添加边距

    /**
     *         使用办法
     *         int spanCount = 3; // 3 columns
     *         int spacing = 50; // 50px
     *         boolean includeEdge = false;
     *         mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
     */

    public SpacesItemDecoration(int spanCount, int spacing, boolean includeEdge,int headerCount) {
        new SpacesItemDecoration(spanCount, spacing, spacing, includeEdge,headerCount);
    }

    public SpacesItemDecoration(int spanCount, int spacing, int spacingH, boolean includeEdge,int headerCount) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.spacingH = spacingH;
        this.includeEdge = includeEdge;
        this.headerCount = headerCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
//        Log.d(TAG, "getItemOffsets: "+position);
        if(position >= headerCount){//第三行开始
            position = position - headerCount;
            int column = position % spanCount; // item column 第几列
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacingH;
                }
                outRect.bottom = spacingH; // item bottom
//                Log.d(TAG, "getItemOffsets/true: left="+outRect.left+"\nright="+outRect.right+"\ntop="+outRect.top+"\nbottom="+outRect.bottom);
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacingH; // item top
                }
//                Log.d(TAG, "getItemOffsets/false: left="+outRect.left+"\nright="+outRect.right+"\ntop="+outRect.top+"\nbottom="+outRect.bottom);
            }
        }
    }
}