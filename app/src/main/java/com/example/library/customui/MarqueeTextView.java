package com.example.library.customui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Copyright (C)
 * FileName: MarqueeTextView
 * Author: Amamiya
 * Date: 2019/12/31 13:51
 * Description: 滚动textView
 * History:
 */
@SuppressLint("AppCompatCustomView")
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {//必须重写，且返回值是true，表示始终获取焦点
        return true;
    }
}
