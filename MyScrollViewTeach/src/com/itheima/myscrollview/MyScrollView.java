package com.itheima.myscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义ViewPager
 * 
 * @author Kevin
 * 
 */
public class MyScrollView extends ViewGroup {

	private GestureDetector mDetector;
	private Scroller mScroller;

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyScrollView(Context context) {
		super(context);
		init();
	}

	private void init() {
		// 初始化手势识别器
		mDetector = new GestureDetector(getContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						scrollBy((int) distanceX, 0);// 水平偏移距离
						return super.onScroll(e1, e2, distanceX, distanceY);
					}
				});

		// 初始化滑动器
		mScroller = new Scroller(getContext());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:// 手指抬起
			// 计算当前应该滑动的具体位置
			int index = (getScrollX() + getWidth() / 2) / getWidth();

			// 避免位置越界
			if (index > getChildCount() - 1) {
				index = getChildCount() - 1;
			}

			setCurrentPage(index);
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * 调用Scroller的startScroll方法之后, 会回调computeScroll方法,计算当前的移动情况
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {// 判断是否移动结束
			int currX = mScroller.getCurrX();// 获取当前应该滑动的水平位置
			scrollTo(currX, 0);// 滑动
		}
	}

	/**
	 * 获得当前view的位置， 如果当前view是ViewGroup的话，应在此方法中，指定子View的位置
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 遍历所有子View, 指定每个子View的位置,保证一字排开
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).layout(0 + i * getWidth(), 0,
					getWidth() + i * getWidth(), getHeight());
		}
	}

	/**
	 * 对本view进行测量大小， 如果当前view是viewGroup,那么需要对每一个子view进行测量大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 测量每一个孩子的大小
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		// int size = MeasureSpec.getSize(widthMeasureSpec);//获取尺寸大小
		// int mode = MeasureSpec.getMode(widthMeasureSpec);//获取尺寸模式
	}

	private int startX;
	private int startY;

	/**
	 * 事件拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDetector.onTouchEvent(ev);

			startX = (int) ev.getX();
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getX();
			int endY = (int) ev.getY();

			int dx = Math.abs(endX - startX);
			int dy = Math.abs(endY - startY);

			if (dx > dy) {
				return true;
			}

			break;

		default:
			break;
		}

		return false;
	}

	/**
	 * 事件分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	private OnPageChangeListener mListener;

	/**
	 * 开关切换事件的回调接口
	 */
	public interface OnPageChangeListener {
		public void onPageSelected(int position);
	}

	/**
	 * 设置开关切换的事件监听
	 */
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}

	/**
	 * 设置当前选中页
	 * 
	 * @param positon
	 */
	public void setCurrentPage(int positon) {
		// 计算应该滑动的距离
		int distance = positon * getWidth() - getScrollX();
		mScroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));// 参1:起始x;参2:起始y;参3:x偏移;参4:y偏移;参5:滑动时间
		// scrollTo(index * getWidth(), 0);
		invalidate();// 刷新界面

		// 页面切换后的回调方法
		if (mListener != null) {
			mListener.onPageSelected(positon);
		}
	}

}
