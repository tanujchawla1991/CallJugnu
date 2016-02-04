package com.example.calljugnu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TermsScreen extends Activity implements View.OnClickListener{
ImageButton i1,i2,i3;
Button b1,b2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_screen);
		i1=(ImageButton)findViewById(R.id.imageButton1);
		i2=(ImageButton)findViewById(R.id.imageButton2);
		i3=(ImageButton)findViewById(R.id.imageButton3);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		i1.setOnClickListener(this);
		i2.setOnClickListener(this);
		i3.setOnClickListener(this);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.terms_screen, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId()==i1.getId())
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/calljugnu"));
			startActivity(browserIntent);
		}
		else if(arg0.getId()==i2.getId())
		{
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=Home&url=http%3A%2F%2Fwww.calljugnu.com%2F%23.UhNqdqK1etE.twitter&related="));
		startActivity(browserIntent);
		}
		else if(arg0.getId()==i3.getId())
		{
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/Login?continue=https://www.google.com/bookmarks/mark%3Fop%3Dadd%26bkmk%3Dhttp%253A%252F%252Fwww.calljugnu.com%252F%2523.UhNqn7MmRKI.google%26title%3DHome%26annotation%3D&hl=en&service=bookmarks"));
		startActivity(browserIntent);
		}
		else if(arg0.getId()==b2.getId())
		{
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.calljugnu.com/"));
		startActivity(browserIntent);
		}
		else
		{
			Intent intent = new Intent(TermsScreen.this, HomePage.class);
            startActivity(intent);
		}
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
}
