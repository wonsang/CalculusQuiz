package com.wy.calculus;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import java.util.*;
import java.text.*;


public class SettingActivity extends Activity {

	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private TextView textview_mode;
	private Spinner spinner_mode;
	private TextView textview_level;
	private Spinner spinner_level;
	private CheckBox checkbox_timelimit;
	private Spinner spinner_maxtime;
	private CheckBox checkbox_sound;
	private TextView usertext;
	private EditText useredit;
	private Button button_close;
	private Button button_save;
	private Button button_default;

	private double mode = 0;
	private double level = 0;
	private double maxtime = 0;
	private double issound = 0;

	private ArrayList<String> modelist = new ArrayList<String>();
	private ArrayList<String> levellist = new ArrayList<String>();
	private ArrayList<String> maxtimelist = new ArrayList<String>();

	private SharedPreferences config;
	private Intent mainintent = new Intent();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initialize();
		initializeLogic();
	}

	private void  initialize() {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview_mode = (TextView) findViewById(R.id.textview_mode);
		spinner_mode = (Spinner) findViewById(R.id.spinner_mode);
		textview_level = (TextView) findViewById(R.id.textview_level);
		spinner_level = (Spinner) findViewById(R.id.spinner_level);
		checkbox_timelimit = (CheckBox) findViewById(R.id.checkbox_timelimit);
		spinner_maxtime = (Spinner) findViewById(R.id.spinner_maxtime);
		checkbox_sound = (CheckBox) findViewById(R.id.checkbox_sound);
		usertext = (TextView) findViewById(R.id.usertext);
		useredit = (EditText) findViewById(R.id.useredit);
		button_close = (Button) findViewById(R.id.button_close);
		button_save = (Button) findViewById(R.id.button_save);
		button_default = (Button) findViewById(R.id.button_default);

		config = getSharedPreferences("config", Activity.MODE_PRIVATE);


		button_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				mainintent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(mainintent);
			}
		});
		button_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				mode = spinner_mode.getSelectedItemPosition();
				config.edit().putString("mode", String.valueOf((long)(mode))).commit();
				level = spinner_level.getSelectedItemPosition();
				config.edit().putString("level", String.valueOf((long)(level))).commit();
				if (checkbox_timelimit.isChecked()) {
					maxtime = Double.parseDouble(maxtimelist.get((int)(spinner_maxtime.getSelectedItemPosition())));
				}
				else {
					maxtime = 0;
				}
				if (checkbox_sound.isChecked()) {
					issound = 1;
				}
				else {
					issound = 0;
				}
				config.edit().putString("maxtime", String.valueOf((long)(maxtime))).commit();
				config.edit().putString("issound", String.valueOf((long)(issound))).commit();
				config.edit().putString("username", useredit.getText().toString()).commit();
				mainintent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(mainintent);
			}
		});
		button_default.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				spinner_mode.setSelection((int)(0));
				spinner_level.setSelection((int)(1));
				spinner_maxtime.setSelection((int)(1));
				checkbox_timelimit.setChecked(false);
				checkbox_sound.setChecked(false);
				useredit.setText("Unknown");
			}
		});

	}

	private void  initializeLogic() {
		modelist.add("Random");
		modelist.add("Addition");
		modelist.add("Subtraction");
		modelist.add("Multiplication");
		modelist.add("Division");
		spinner_mode.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, modelist));
		if (config.getString("mode", "").length() == 0) {
			spinner_mode.setSelection((int)(0));
		}
		else {
			spinner_mode.setSelection((int)(Double.parseDouble(config.getString("mode", ""))));
		}
		((ArrayAdapter)spinner_mode.getAdapter()).notifyDataSetChanged();
		levellist.add("Select");
		levellist.add("Beginner");
		levellist.add("Medium");
		levellist.add("Advanced");
		spinner_level.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, levellist));
		if (config.getString("level", "").length() == 0) {
			spinner_level.setSelection((int)(1));
		}
		else {
			spinner_level.setSelection((int)(Double.parseDouble(config.getString("level", ""))));
		}
		((ArrayAdapter)spinner_level.getAdapter()).notifyDataSetChanged();
		maxtimelist.add("5");
		maxtimelist.add("10");
		maxtimelist.add("15");
		maxtimelist.add("20");
		maxtimelist.add("30");
		maxtimelist.add("60");
		spinner_maxtime.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, maxtimelist));
		if (config.getString("maxtime", "").length() == 0) {
			spinner_maxtime.setSelection((int)(1));
		}
		else {
			spinner_maxtime.setSelection((int)(maxtimelist.indexOf(config.getString("maxtime", ""))));
		}
		((ArrayAdapter)spinner_maxtime.getAdapter()).notifyDataSetChanged();
		if ((config.getString("maxtime", "").length() == 0) || (Double.parseDouble(config.getString("maxtime", "")) == 0)) {
			checkbox_timelimit.setChecked(false);
		}
		else {
			checkbox_timelimit.setChecked(true);
		}
		if (config.getString("username", "").length() == 0) {
			useredit.setText("Unknown");
		}
		else {
			useredit.setText(config.getString("username", ""));
		}
		if ((config.getString("issound", "").length() == 0) || (Double.parseDouble(config.getString("issound", "")) == 0)) {
			checkbox_sound.setChecked(false);
		}
		else {
			checkbox_sound.setChecked(true);
		}
	}




	// created automatically
	private void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	private int getRandom(int _minValue ,int _maxValue){
		Random random = new Random();
		return random.nextInt(_maxValue - _minValue + 1) + _minValue;
	}

	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}

}
