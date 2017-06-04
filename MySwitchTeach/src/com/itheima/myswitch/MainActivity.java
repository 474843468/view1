package com.itheima.myswitch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.itheima.myswitch.MySwitch.OnCheckedChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// CheckBox c = new CheckBox(this);
		// c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		//
		// }
		// });

		MySwitch s = (MySwitch) findViewById(R.id.my_switch);
		s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(MySwitch view, boolean isChecked) {
				System.out.println("开关状态:" + isChecked);
				Toast.makeText(getApplicationContext(), "开关状态:" + isChecked,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
