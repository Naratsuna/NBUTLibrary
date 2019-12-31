package com.example.library.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.library.MyApplication;
import com.example.library.R;
import com.gyf.immersionbar.ImmersionBar;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.IdRes;

/**
 * 工具类，提供一些静态的功能函数
 */
public class Utils {

	private static final int MIN_DELAY_TIME = 3000;  // 两次出现时间间隔
	private static long lastToastTime;// 上一次toast出现的时间
	private static String lastMessage;// 上一次Toast的内容
	private static long lastClickTime;// 上一次点击事件

	/**
	 * 将字符串进行MD5加密， 主要用于密码部分
	 */
	public static String toMD5(String data) {
		if (data == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 加密转换
			md.update(data.getBytes());
			String result = new BigInteger(1, md.digest()).toString(16);

			return result;

		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	//必须在setContentView后执行，因为内部调用了findViewById
	public static void ImmersionStatusBar(Activity activity,@IdRes int viewId){
		ImmersionBar.with(activity)
				.transparentBar()
				.statusBarView(viewId)
				.statusBarDarkFont(true)
				.flymeOSStatusBarFontColor(R.color.color_main_black)
				.init();
	}

	/**
	 * 将dp转换成px
	 * @param
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue){
		final float scale = MyApplication.getContext().getResources ().getDisplayMetrics ().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 将像素转换成dp
	 * @param
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue){
		final float scale = MyApplication.getContext().getResources ().getDisplayMetrics ().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕高度
	 * @return px
	 */
	public static int getScreenHeight(){
		return MyApplication.getContext().getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕宽度
	 * @return px
	 */
	public static int getScreenWidth(){
		return MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;
	}

	/*
	 *限制按钮多次点击一秒之内不能重复点击
	 * */
	public static boolean isFastClick() {
		boolean flag = true;
		long currentClickTime = System.currentTimeMillis();
		if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
			flag = false;//正常执行
			lastClickTime = currentClickTime;
		}
		return flag;
	}

	public static void showShort(Context context, String message) {
		if (TextUtils.equals(lastMessage, message)) {
			if (System.currentTimeMillis() - lastToastTime > MIN_DELAY_TIME) {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		lastMessage = message;
		lastToastTime = System.currentTimeMillis();
	}

	public static void showMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
