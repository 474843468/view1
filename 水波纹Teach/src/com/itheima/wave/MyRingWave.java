package com.itheima.wave;

import java.util.ArrayList;

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
 * 滑动绘制圆环集合
 * 
 * @author Kevin
 * 
 */
public class MyRingWave extends View {

	private static final int MIN_DIS = 10;// 两个圆环的最短距离

	// 圆环集合
	ArrayList<Wave> mWaveList = new ArrayList<MyRingWave.Wave>();

	// 颜色集合
	int[] mColors = new int[] { Color.RED, Color.GREEN, Color.BLUE,
			Color.YELLOW };

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			flushData();
			invalidate();

			if (!mWaveList.isEmpty()) {
				mHandler.sendEmptyMessageDelayed(0, 50);
			}
		};
	};

	public MyRingWave(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyRingWave(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRingWave(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制所有圆环
		for (Wave wave : mWaveList) {
			canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.paint);
		}
	}

	/**
	 * 刷新数据
	 */
	private void flushData() {
		ArrayList<Wave> removeList = new ArrayList<MyRingWave.Wave>();// 待移除的圆环对象集合
		for (Wave wave : mWaveList) {
			wave.radius += 3;// 增加半径
			wave.paint.setStrokeWidth(wave.radius / 3);// 重新这是圆环宽度
			int alpha = wave.paint.getAlpha();

			if (alpha == 0) {// 如果圆环透明度已经为0,不会再进行绘制,需要从列表中移除
				removeList.add(wave);// 要删除的元素
			}

			alpha -= 5;// 透明度递减
			if (alpha < 0) {
				alpha = 0;
			}

			wave.paint.setAlpha(alpha);
		}

		mWaveList.removeAll(removeList);// 移除已经消失的圆环对象
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			int cx = (int) event.getX();
			int cy = (int) event.getY();

			addPoint(cx, cy);
			break;

		default:
			break;
		}

		return true;
	}

	private void addPoint(int cx, int cy) {
		if (mWaveList.isEmpty()) {// 第一次添加圆环
			addWave(cx, cy);
			mHandler.sendEmptyMessage(0);// 发送消息,启动绘制
		} else {
			Wave wave = mWaveList.get(mWaveList.size() - 1);// 获取最后一个圆环
			if (Math.abs(wave.cx - cx) > MIN_DIS
					|| Math.abs(wave.cy - cy) > MIN_DIS) {// 只有在两个圆环距离超过一定范围时才进行绘制,保证圆环彼此保持一定间距
				addWave(cx, cy);
			}
		}
	}

	/**
	 * 添加圆环对象
	 * 
	 * @param cx
	 * @param cy
	 */
	private void addWave(int cx, int cy) {
		Wave wave = new Wave();
		wave.cx = cx;
		wave.cy = cy;

		// 初始化画笔
		Paint paint = new Paint();
		paint.setColor(mColors[(int) (Math.random() * 4)]);// 画笔颜色随机
		paint.setStyle(Style.STROKE);// 绘制空心圆
		paint.setAntiAlias(true);// 没有锯齿

		wave.paint = paint;

		mWaveList.add(wave);
	}

	/**
	 * 圆环对象封装
	 */
	class Wave {
		int cx;// 圆心x坐标
		int cy;// 圆心y坐标
		int radius;// 圆环半径
		Paint paint;// 画笔
	}
}
