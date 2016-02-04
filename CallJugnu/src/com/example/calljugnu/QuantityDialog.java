package com.example.calljugnu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class QuantityDialog extends Dialog implements android.view.View.OnClickListener{
	public Activity c;
	EditText e1;
	Button b1,b2;
	
	public QuantityDialog(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c=a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_quantity);
		e1=(EditText)findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		e1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		e1.setText("");
	}
	
}
