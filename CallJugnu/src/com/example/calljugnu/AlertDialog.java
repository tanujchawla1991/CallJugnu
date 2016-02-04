package com.example.calljugnu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AlertDialog extends Dialog{
	public Activity c;
	TextView t1;
	Button b1,b2;
	
	public AlertDialog(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c=a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert);
		t1=(TextView)findViewById(R.id.textView1);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
	}

}