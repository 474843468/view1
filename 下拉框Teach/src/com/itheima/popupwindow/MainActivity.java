package com.itheima.popupwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText etInput;
	private ImageView ivDown;

	private ArrayList<String> mList;
	private PopupWindow mPopupWindow;
	private ListView lvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etInput = (EditText) findViewById(R.id.et_input);
		ivDown = (ImageView) findViewById(R.id.iv_down);

		ivDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopup();
			}
		});

		// 初始化下拉列表数据
		mList = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			mList.add("aaabbbccc" + i);
		}

		// 初始化listview
		lvList = new ListView(this);
		lvList.setAdapter(new MyAdapter());

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = mList.get(position);
				etInput.setText(text);

				mPopupWindow.dismiss();
			}
		});
	}

	/**
	 * 展现下拉框
	 */
	protected void showPopup() {
		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(lvList, etInput.getWidth(), 200,
					true);
			mPopupWindow.setBackgroundDrawable(new ColorDrawable());
		}

		mPopupWindow.showAsDropDown(etInput);//展现在文本框的正下方
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public String getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.list_item, null);
				holder = new ViewHolder();
				holder.tvText = (TextView) convertView
						.findViewById(R.id.tv_text);
				holder.ivDelete = (ImageView) convertView
						.findViewById(R.id.iv_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// TextView view = new TextView(getApplicationContext());
			// String text = getItem(position);
			// view.setText(text);

			holder.tvText.setText(getItem(position));
			holder.ivDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//删除当前被点击的元素,并刷新listview
					mList.remove(position);
					MyAdapter.this.notifyDataSetChanged();
				}
			});

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tvText;
		public ImageView ivDelete;
	}

}
