package com.itheima.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 绘制圆环
 * 
 * @author Kevin
 * 
 */
public class MyRing extends View {

	private int cx;// 圆心x坐标
	private int cy;// 圆心y坐标
	private int radius;// 圆环半径

	private Paint mPaint;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 渐变
			// 半径变大
			// 圆环宽度变大
			int alpha = mPaint.getAlpha();// 获取当前渐变值

			// 渐变值递减
			alpha -= 10;
			if (alpha < 0) {// 如果渐变值小于0,就置为0
				alpha = 0;
			}

			mPaint.setAlpha(alpha);// 设置渐变
			radius += 5;// 半径递增
			mPaint.setStrokeWidth(radius / 3);// 设置圆环宽度
			invalidate();// 重新绘制圆环

			if (alpha > 0) {// 渐变值大于0,表示圆环还未完全消失
				mHandler.sendEmptyMessageDelayed(0, 50);// 继续发送延时消息,形成递归循环
			}
		};
	};

	public MyRing(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public MyRing(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyRing(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		// 初始化画笔
		radius = 0;
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(radius / 3);
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(cx, cy, radius, mPaint);// 绘制圆环
	}

	/**
	 * 开启圆环动画
	 */
	private void startAnim() {
		mHandler.sendEmptyMessage(0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 按下后,获取当前的x,y坐标,作为圆心坐标
			cx = (int) event.getX();
			cy = (int) event.getY();
			// invalidate();
			initView();// 初始化画笔
			startAnim();// 开始绘制圆环
		}

		return super.onTouchEvent(event);
	}

}
