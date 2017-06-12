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
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
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
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button cleanbutton;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button button0;
	private Button backbutton;
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
	private TextView ranktext;
	private TextView usertext1;
	private TextView userscore1;
	private TextView usertext2;
	private TextView userscore2;
	private TextView usertext3;
	private TextView userscore3;
	private Button settingbutton;
	private Button buttonhelp;
	private Button aboutbutton;

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
	private String username = "";
	private double delta = 0;

	private ArrayList<String> rankusers = new ArrayList<String>();
	private ArrayList<Double> rankscores = new ArrayList<Double>();

	private Timer _timer = new Timer();
	private TimerTask reporttimer;
	private Intent intentcodes = new Intent();
	private SharedPreferences config;
	private Intent settingintent = new Intent();
	private Vibrator ansvibrator;


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
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
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
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		cleanbutton = (Button) findViewById(R.id.cleanbutton);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		button0 = (Button) findViewById(R.id.button0);
		backbutton = (Button) findViewById(R.id.backbutton);
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
		ranktext = (TextView) findViewById(R.id.ranktext);
		usertext1 = (TextView) findViewById(R.id.usertext1);
		userscore1 = (TextView) findViewById(R.id.userscore1);
		usertext2 = (TextView) findViewById(R.id.usertext2);
		userscore2 = (TextView) findViewById(R.id.userscore2);
		usertext3 = (TextView) findViewById(R.id.usertext3);
		userscore3 = (TextView) findViewById(R.id.userscore3);
		settingbutton = (Button) findViewById(R.id.settingbutton);
		buttonhelp = (Button) findViewById(R.id.buttonhelp);
		aboutbutton = (Button) findViewById(R.id.aboutbutton);



		config = getSharedPreferences("config", Activity.MODE_PRIVATE);

		ansvibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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
				intentcodes.setData(Uri.parse("http://calculusquiz.weebly.com/tutorial.html"));
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
		aboutbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				intentcodes.setAction(Intent.ACTION_VIEW);
				intentcodes.setData(Uri.parse("http://calculusquiz.weebly.com"));
				startActivity(intentcodes);
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(1);
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(2);
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(3);
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(4);
			}
		});
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(5);
			}
		});
		button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(6);
			}
		});
		button7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(7);
			}
		});
		button8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(8);
			}
		});
		button9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(9);
			}
		});
		button0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				_displaynumber(0);
			}
		});
		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				if (answeredit.getText().toString().length() == 0) {
					answeredit.setText("0");
				}
				else {
					answeredit.setText(String.valueOf((long)(Double.parseDouble(answeredit.getText().toString()) / 10)));
				}
			}
		});
		cleanbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) { 
				answeredit.setText("0");
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
		delta = 1;
		if (2 < operation) {
			delta = 2;
		}
		if (1 < level) {
			delta = delta * (level * 2);
		}
		if (answeredit.getText().toString().equals("")) {
			useranswer = 0;
		}
		else {
			useranswer = Double.parseDouble(answeredit.getText().toString());
		}
		if (useranswer == answer) {
			iscorrect = true;
			_updatescore(delta);
			showMessage("Excellent!");
			ansvibrator.vibrate((long)(100));
		}
		else {
			iscorrect = false;
			_updatescore(0 - delta);
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
		_updaterank();
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
		if (config.getString("username", "").length() == 0) {
			username = "Unknown";
		}
		else {
			username = config.getString("username", "");
		}
	}
	private void _updaterank () {
		rankusers.clear();
		if (config.getString("rankuser1", "").length() == 0) {
			rankusers.add("Unknown");
			rankscores.add(Double.valueOf(Double.parseDouble("0")));
		}
		else {
			rankusers.add(config.getString("rankuser1", ""));
			rankscores.add(Double.valueOf(Double.parseDouble(config.getString("rankscore1", ""))));
		}
		if (config.getString("rankuser2", "").length() == 0) {
			rankusers.add("Unknown");
			rankscores.add(Double.valueOf(Double.parseDouble("0")));
		}
		else {
			rankusers.add(config.getString("rankuser2", ""));
			rankscores.add(Double.valueOf(Double.parseDouble(config.getString("rankscore2", ""))));
		}
		if (config.getString("rankuser3", "").length() == 0) {
			rankusers.add("Unknown");
			rankscores.add(Double.valueOf(Double.parseDouble("0")));
		}
		else {
			rankusers.add(config.getString("rankuser3", ""));
			rankscores.add(Double.valueOf(Double.parseDouble(config.getString("rankscore3", ""))));
		}
		if (rankscores.get((int)(0)).doubleValue() < score) {
			if (username.equals(rankusers.get((int)(0)))) {
				rankusers.remove((int)(0));
				rankscores.remove((int)(0));
			}
			rankusers.add((int)(0), username);
			rankscores.add((int)(0), Double.valueOf(score));
		}
		else {
			if (rankscores.get((int)(1)).doubleValue() < score) {
				if (username.equals(rankusers.get((int)(1)))) {
					rankusers.remove((int)(1));
					rankscores.remove((int)(1));
				}
				rankusers.add((int)(1), username);
				rankscores.add((int)(1), Double.valueOf(score));
			}
			else {
				if (rankscores.get((int)(2)).doubleValue() < score) {
					if (username.equals(rankusers.get((int)(2)))) {
						rankusers.remove((int)(2));
						rankscores.remove((int)(2));
					}
					rankusers.add((int)(2), username);
					rankscores.add((int)(2), Double.valueOf(score));
				}
			}
		}
		usertext1.setText(rankusers.get((int)(0)));
		userscore1.setText(String.valueOf((long)(rankscores.get((int)(0)).doubleValue())));
		config.edit().putString("rankuser1", rankusers.get((int)(0))).commit();
		config.edit().putString("rankscore1", String.valueOf((long)(rankscores.get((int)(0)).doubleValue()))).commit();
		usertext2.setText(rankusers.get((int)(1)));
		userscore2.setText(String.valueOf((long)(rankscores.get((int)(1)).doubleValue())));
		config.edit().putString("rankuser2", rankusers.get((int)(1))).commit();
		config.edit().putString("rankscore2", String.valueOf((long)(rankscores.get((int)(1)).doubleValue()))).commit();
		usertext3.setText(rankusers.get((int)(2)));
		userscore3.setText(String.valueOf((long)(rankscores.get((int)(2)).doubleValue())));
		config.edit().putString("rankuser3", rankusers.get((int)(2))).commit();
		config.edit().putString("rankscore3", String.valueOf((long)(rankscores.get((int)(2)).doubleValue()))).commit();
	}
	private void _displaynumber (final double _num) {
		if (answeredit.getText().toString().length() == 0) {
			answeredit.setText(String.valueOf((long)(_num)));
		}
		else {
			answeredit.setText(String.valueOf((long)((Double.parseDouble(answeredit.getText().toString()) * 10) + _num)));
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
