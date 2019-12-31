package com.example.library.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterFactory;

public class IconfontLayoutFactory implements LayoutInflaterFactory {
    private Typeface mTypeface;
    private AppCompatDelegate mAppCompatDelegate;

    public IconfontLayoutFactory(Context mContext, final AppCompatDelegate appCompatDelegate ) {
        if ( mTypeface == null ) {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "iconfont.ttf");
            mAppCompatDelegate = appCompatDelegate;
        }
    }

    @Override
    public View onCreateView(final View parent, final String name, final Context context, final AttributeSet attrs ) {
        View view = mAppCompatDelegate.createView(parent,name,context,attrs);
        if(view instanceof TextView){
            ((TextView)view).setTypeface(mTypeface);
        }
        return view;
    }
}