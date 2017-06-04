package com.itcast.rocket09;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

/**
 * 烟雾背景页面
 * 
 * 设置页面全透明
 *  <activity
            android:name="com.itcast.rocket09.BackgroundActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" >
        </activity>
 */
public class BackgroundActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_background);

		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

		AlphaAnimation anim = new AlphaAnimation(0, 1);
		anim.setDuration(2000);
		rlRoot.startAnimation(anim);

		//动画结束之后,销毁当前activity
		//监听动画结束事件
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				//动画开始
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				//动画重复
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//动画结束
				finish();
			}
		});
	}

}
