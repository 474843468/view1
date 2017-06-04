package com.itcast.popupwindowdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
	}

	//显示小弹窗
	//参数View代表当前被点击的控件对象; view就是当前被点击的按钮对象
	public void showPopup(View view) {
		//初始化弹窗布局
		TextView contentView = new TextView(this);
		contentView.setText("我是小弹窗哦!");
		contentView.setTextSize(25);

		//参1:弹窗布局;参2,3:弹窗宽高; 参4:是否有焦点,一般传true
		PopupWindow popup = new PopupWindow(contentView, 200, 200, true);

		//给弹窗设置背景
		//只有设置了背景之后, 点击返回键或者弹窗外侧, 弹窗才会消失
		popup.setBackgroundDrawable(new ColorDrawable(Color.RED));

		//显示在屏幕某个位置
		//参1: 父控件; 参2:重心位置; 参3,4:基于当前重心位置的x,y偏移
		//popup.showAtLocation(rlRoot, Gravity.CENTER, 0, 0);

		//显示在某个控件正下方(更常用)
		//参1: 显示在哪个控件下方; 参2,3:基于当前位置的x,y偏移
		popup.showAsDropDown(view, 0, 0);
	}
}
