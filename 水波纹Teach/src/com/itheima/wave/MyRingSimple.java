package com.itheima.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

/**
 * 简单的自定义view
 * 
 * @author Kevin
 * 
 */
public class MyRingSimple extends View {

	private Paint mPaint;
	private Paint mCirclePaint;

	public MyRingSimple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public MyRingSimple(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyRingSimple(Context context) {
		super(context);
		initView();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(200, 200);// 重新定义尺寸
	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// 设置颜色
		mPaint.setStrokeWidth(3);// 设置宽度

		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.RED);
		mCirclePaint.setStrokeWidth(2);//线条宽度
		mCirclePaint.setStyle(Style.STROKE);//空心圆
		mCirclePaint.setAntiAlias(true);//设置没有锯齿
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 10; i < getWidth(); i += 20) {
			// 画线
			// 参1:起点x, 参 2:起点y,参3:终点x,参4:终点y, 参5:画笔
			canvas.drawLine(0, i, getWidth(), i, mPaint);// 水平线
			canvas.drawLine(i, 0, i, getHeight(), mPaint);// 竖直线
		}

		canvas.translate(-20, -20);// 移动画布

		// 画圆
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, 50, mCirclePaint);// 参1:圆心x坐标,参2:圆心y坐标,参3:圆半径,参4:画笔
	}
}
