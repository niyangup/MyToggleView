package com.example.mytoggleview;

import com.example.mytoggleview.view.ToggleView;
import com.example.mytoggleview.view.ToggleView.OnSwitchStateUpdateListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ToggleView mToggle = (ToggleView) findViewById(R.id.toggle);
		
//		mToggle.setSwitchBackgaroundResource(R.drawable.switch_background);
//		mToggle.setSlideButtonBackgaround(R.drawable.slide_button);
//		
//		mToggle.setState(false);
		
		mToggle.setOnSwitchStateUpdateListener(new OnSwitchStateUpdateListener() {
			
			@Override
			public void onStateUpdate(boolean state) {
				Toast.makeText(MainActivity.this,""+state, 0).show();
			}
		});
	}
}
