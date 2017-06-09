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


public class MainActivity extends Activity {

	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private LinearLayout linear8;
	private TextView value1view;
	private TextView operatorview;
	private TextView value2view;
	private TextView equalview;
	private EditText answeredit;
	private TextView resulttext;
	private TextView answerview;
	private Button nextbutton;
	private Button submitbutton;
	private Button clearbutton;
	private TextView scoretext;
	private TextView scoreview;
	private TextView cnttext;
	private TextView cntview;
	private TextView timetext;
	private TextView timeview;
	private TextView modetext;
	private TextView textviewmode;
	private TextView leveltext;
	private TextView textview_level;
	private TextView maxtimetext;
	private TextView textview_maxtime;
	private Button settingbutton;
	private Button buttonhelp;

	private double value1 = 0;
	private double value2 = 0;
	private double answer = 0;
	private double score = 0;
	private double level = 0;
	private double maxnum = 0;
	private double count = 0;
	private boolean iscorrect;
	private double operation = 0;
	private double timecnt = 0;
	private double maxtime = 0;
	private boolean isreadysubmit;
	private double useranswer = 0;
	private double opmode = 0;

	private ArrayList<String> mode = new ArrayList<String>();

	private Timer _timer = new Timer();
	private TimerTask reporttimer;
	private Intent intentcodes = new Intent();
	private SharedPreferences config;
	private Intent settingintent = new Intent();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		initializeLogic();
	}

	private void  initialize() {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		value1view = (TextView) findViewById(R.id.value1view);
		operatorview = (TextView) findViewById(R.id.operatorview);
		value2view = (TextView) findViewById(R.id.value2view);
		equalview = (TextView) findViewById(R.id.equalview);
		answeredit = (EditText) findViewById(R.id.answeredit);
		resulttext = (TextView) findViewById(R.id.resulttext);
		answerview = (TextView) findViewById(R.id.answerview);
		nextbutton = (Button) findViewById(R.id.nextbutton);
		submitbutton = (Button) findViewById(R.id.submitbutton);
		clearbutton = (Button) findViewById(R.id.clearbutton);
		scoretext = (TextView) findViewById(R.id.scoretext);
		scoreview = (TextView) findViewById(R.id.scoreview);
		cnttext = (TextView) findViewById(R.id.cnttext);
		cntview = (TextView) findViewById(R.id.cntview);
		timetext = (TextView) findViewById(R.id.timetext);
		timeview = (TextView) findViewById(R.id.timeview);
		modetext = (TextView) findViewById(R.id.modetext);
		textviewmode = (TextView) findViewById(R.id.textviewmode);
		leveltext = (TextView) findViewById(R.id.leveltext);
		textview_level = (TextView) findViewById(R.id.textview_level);
		maxtimetext = (TextView) findViewById(R.id.maxtimetext);
		textview_maxtime = (TextView) findViewById(R.id.textview_maxtime);
		settingbutton = (Button) findViewById(R.id.settingbutton);
		buttonhelp = (Button) findViewById(R.id.buttonhelp);



		config = getSharedPreferences("config", Activity.MODE_PRIVATE);


		submitbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				if (timecnt < 0) {
					showMessage("No remaining time");
				}
				else {
					if (isreadysubmit) {
						count++;
						cntview.setText(String.valueOf((long)(count)));
						_checkanswer();
						isreadysubmit = false;
					}
				}
			}
		});
		clearbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				count = 0;
				timecnt = 0;
				isreadysubmit = true;
				if (0 < maxtime) {
						reporttimer.cancel();
				}
				cntview.setText(String.valueOf((long)(count)));
				_initscore();
				_newproblem();
			}
		});
		nextbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				if (!isreadysubmit || (isreadysubmit && (timecnt < 0))) {
					if (0 < maxtime) {
							reporttimer.cancel();
					}
					isreadysubmit = true;
					_newproblem();
				}
			}
		});
		buttonhelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				intentcodes.setAction(Intent.ACTION_VIEW);
				intentcodes.setData(Uri.parse("http://calculusquiz.weebly.com"));
				startActivity(intentcodes);
			}
		});
		settingbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				settingintent.setClass(getApplicationContext(), SettingActivity.class);
				startActivity(settingintent);
			}
		});

	}

	private void  initializeLogic() {
		score = 0;
		count = 0;
		isreadysubmit = true;
		_updatesetting();
		_newproblem();
	}


	private void _newproblem () {
		_updatesetting();
		value1 = getRandom((int)(2), (int)(maxnum));
		value2 = getRandom((int)(2), (int)(value1));
		if (operation == 1) {
			answer = value1 + value2;
			operatorview.setText("+");
		}
		else {
			if (operation == 3) {
				answer = value1 * value2;
				operatorview.setText("×");
			}
			else {
				if (operation == 2) {
					answer = value1 - value2;
					operatorview.setText("-");
				}
				else {
					answer = Math.floor(value1 / value2);
					operatorview.setText("÷");
				}
			}
		}
		value1view.setText(String.valueOf((long)(value1)));
		value2view.setText(String.valueOf((long)(value2)));
		_clearanswerreport();
		_runtimecounter();
		answeredit.setVisibility(View.VISIBLE);
		resulttext.setBackgroundColor(Color.TRANSPARENT);
		answerview.setBackgroundColor(Color.TRANSPARENT);
	}
	private void _initscore () {
		score = 0;
		scoreview.setText(String.valueOf((long)(score)));
	}
	private void _updatescore (final double _delta) {
		score = score + _delta;
		if (score < 0) {
			score = 0;
		}
		scoreview.setText(String.valueOf((long)(score)));
	}
	private void _checkanswer () {
		if (answeredit.getText().toString().equals("")) {
			useranswer = 0;
		}
		else {
			useranswer = Double.parseDouble(answeredit.getText().toString());
		}
		if (useranswer == answer) {
			iscorrect = true;
			_updatescore(1);
			showMessage("Excellent!");
		}
		else {
			iscorrect = false;
			_updatescore(-1);
			showMessage("Try again!");
		}
		_updateanswerreport();
	}
	private void _updatelevel () {
		if (level == 1) {
			maxnum = 9;
			textview_level.setText("beginner");
		}
		else {
			if (level == 2) {
				maxnum = 20;
				textview_level.setText("medium");
			}
			else {
				maxnum = 99;
				textview_level.setText("advanced");
			}
		}
	}
	private void _updateanswerreport () {
		if (iscorrect) {
			resulttext.setText("Correct");
			answerview.setText("");
			resulttext.setBackgroundColor(0xFFFFC107);
		}
		else {
			resulttext.setText("Answer");
			answerview.setText(String.valueOf((long)(answer)));
			resulttext.setBackgroundColor(0xFFFFC107);
			answerview.setBackgroundColor(0xFFFFC107);
		}
	}
	private void _clearanswerreport () {
		answeredit.setText("");
		resulttext.setText("");
		answerview.setText("");
		iscorrect = false;
	}
	private void _updatemode () {
		if (opmode == 0) {
			operation = getRandom((int)(1), (int)(4));
			textviewmode.setText("random");
		}
		else {
			if (opmode == 1) {
				operation = 1;
				textviewmode.setText("addition");
			}
			else {
				if (opmode == 2) {
					operation = 2;
					textviewmode.setText("subtraction");
				}
				else {
					if (opmode == 3) {
						operation = 3;
						textviewmode.setText("multiplication");
					}
					else {
						operation = 4;
						textviewmode.setText("division");
					}
				}
			}
		}
	}
	private void _runtimecounter () {
		if (maxtime > 0) {
			timecnt = maxtime;
			timeview.setText(String.valueOf((long)(timecnt)));
			reporttimer = new TimerTask() {
						@Override
							public void run() {
								runOnUiThread(new Runnable() {
								@Override
									public void run() {
												timecnt--;
									timeview.setText(String.valueOf((long)(timecnt)));
									if (timecnt < 0) {
											reporttimer.cancel();
									}
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(reporttimer, (int)(0), (int)(1000));
		}
	}
	private void _updatesetting () {
		_getconfig();
		_updatemode();
		_updatelevel();
		textview_maxtime.setText(String.valueOf((long)(maxtime)));
	}
	private void _getconfig () {
		if (config.getString("mode", "").length() == 0) {
			opmode = 0;
		}
		else {
			opmode = Double.parseDouble(config.getString("mode", ""));
		}
		if (config.getString("level", "").length() == 0) {
			level = 1;
		}
		else {
			level = Double.parseDouble(config.getString("level", ""));
		}
		if (config.getString("maxtime", "").length() == 0) {
			maxtime = 10;
		}
		else {
			maxtime = Double.parseDouble(config.getString("maxtime", ""));
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
