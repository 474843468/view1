package com.itheima.myscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima.myscrollview.MyScrollView.OnPageChangeListener;

public class MainActivity extends Activity {

	private MyScrollView mScrollView;

	private RadioGroup rgGroup;

	//图片资源ID
	private final int[] mImageIds = new int[] { R.drawable.a1, R.drawable.a2,
			R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mScrollView = (MyScrollView) findViewById(R.id.my_scroll);
		rgGroup = (RadioGroup) findViewById(R.id.rg_group);

		//给自定义的ViewPager动态添加图片
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView view = new ImageView(this);//创建ImageView对象
			view.setBackgroundResource(mImageIds[i]);//设置图片背景(背景可以保证填充ImageView)
			mScrollView.addView(view);//添加一个ImageView
		}

		//初始化测试页面
		View item = View.inflate(this, R.layout.item_view, null);
		//将测试页面添加到第三个位置
		mScrollView.addView(item, 2);

		//根据图片数量,动态增加RadioButton(注意增加测试页面)
		for (int i = 0; i < mImageIds.length + 1; i++) {
			RadioButton rb = new RadioButton(this);
			rb.setId(i);//以当前item的位置作为id
			rgGroup.addView(rb);
		}

		rgGroup.check(0);// 默认选中第一项

		//滑动自定义ViewPager,切换RadioButton的选中状态
		mScrollView.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				rgGroup.check(position);
			}
		});

		//点击RadioButton, 切换当前页面
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mScrollView.setCurrentPage(checkedId);
			}
		});
	}

}
