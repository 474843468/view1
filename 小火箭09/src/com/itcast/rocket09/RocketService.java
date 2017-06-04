package com.itcast.rocket09;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class RocketService extends Service {

	private WindowManager mWM;
	private View mView;
	private WindowManager.LayoutParams mParams;

	private int startX;
	private int startY;
	private int screenWidth;
	private int screenHeight;
	private ImageView ivRocket;

	@Override
	public void onCreate() {
		super.onCreate();

		mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		screenWidth = mWM.getDefaultDisplay().getWidth();
		screenHeight = mWM.getDefaultDisplay().getHeight();

		//初始化布局参数(能看懂, 直接抄)
		mParams = new WindowManager.LayoutParams();
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//布局高度
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//布局宽度
		//标记, 定义布局的特点, 没有焦点, 不能触摸, 保持屏幕常亮
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		//| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE(去掉此代码,保证可以触摸)
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		//显示格式,显示效果,此属性不重要,可以忽略
		mParams.format = PixelFormat.TRANSLUCENT;
		//吐司动画
		//params.windowAnimations = com.android.internal.R.style.Animation_Toast;
		//窗口布局类型, 吐司类型, 定义布局显示级别,调整级别为TYPE_PHONE, 保证可以触摸
		mParams.type = WindowManager.LayoutParams.TYPE_PHONE;

		//mParams.x: 基于当前重心位置的偏移量; 重心位置默认是Center,屏幕的正中央, 坐标原点就在正中央
		//为了让坐标体系统一屏幕左上角为原点, 可以修改重心位置
		mParams.gravity = Gravity.LEFT | Gravity.TOP;

		//初始化火箭布局
		mView = View.inflate(this, R.layout.rocket, null);

		//帧动画
		ivRocket = (ImageView) mView.findViewById(R.id.iv_rocket);
		//获取当前imageview显示的图片对象, 强转成动画对象
		AnimationDrawable anim = (AnimationDrawable) ivRocket.getDrawable();
		anim.start();//启动帧动画

		//设置布局的触摸监听
		//		1. 按下之后,获取起始坐标点
		//		2. 移动之后,获取移动后的坐标点
		//		3. 计算移动偏移量
		//		4. 根据偏移量,更新控件位置
		//		5. 重新初始化起点坐标
		mView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					//System.out.println("按下");

					//1. 按下之后,获取起始坐标点

					startX = (int) event.getRawX();
					startY = (int) event.getRawY();

					//					System.out.println("rawX=" + rawX);
					//					System.out.println("rawY=" + rawY);

					//int x = (int) event.getX();//以当前控件左上角为原点, 获取相对的坐标点
					//int y = (int) event.getY();

					//					System.out.println("x=" + x);
					//					System.out.println("y=" + y);

					break;
				case MotionEvent.ACTION_MOVE:
					//					System.out.println("移动");
					//2. 移动之后,获取移动后的坐标点
					int moveX = (int) event.getRawX();
					int moveY = (int) event.getRawY();

					//3. 计算移动偏移量
					int dx = moveX - startX;
					int dy = moveY - startY;

					//4. 根据偏移量,更新控件位置
					mParams.x += dx;
					mParams.y += dy;

					//按照最新的布局参数,更新窗口布局
					mWM.updateViewLayout(mView, mParams);

					//5. 重新初始化起点坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					//System.out.println("抬起");
					int x = mParams.x;
					int y = mParams.y;

					//System.out.println("x:" + x + ";y" + y);

					//水平坐标 >= 屏幕宽度1/3,<=屏幕宽度2/3
					//竖直坐标 > 屏幕高度 - 火箭高度

					if (x >= screenWidth / 3 && x <= screenWidth * 2 / 3
							&& y > screenHeight - ivRocket.getHeight() - 50) {
						System.out.println("发射火箭啦!");

						sendRocket();

						//启动烟雾背景
						//当从service中启动activity时, 加标记FLAG_ACTIVITY_NEW_TASK
						Intent intent = new Intent(getApplicationContext(),
								BackgroundActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新建任务栈
						startActivity(intent);
					}

					break;

				default:
					break;
				}

				return true;//消费掉此事件
			}
		});

		//添加窗口布局
		mWM.addView(mView, mParams);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mParams.y = msg.arg1;
			mWM.updateViewLayout(mView, mParams);
		};
	};

	//发射火箭
	protected void sendRocket() {
		new Thread() {
			@Override
			public void run() {
				//火箭移动总距离
				int distance = screenHeight - ivRocket.getHeight() - 50;

				//for循环,距离不断变小, 更新窗口布局
				for (int i = distance; i >= 0; i -= 10) {

					SystemClock.sleep(20);

					Message msg = Message.obtain();
					msg.arg1 = i;//携带一个整数参数

					mHandler.sendMessage(msg);
				}
			}
		}.start();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mWM != null && mView != null) {
			mWM.removeView(mView);
		}
	}

}
