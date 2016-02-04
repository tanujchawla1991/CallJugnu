package com.example.calljugnu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Activity {
	ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8,ib9,ib10;
	DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		ib3=(ImageButton)findViewById(R.id.imageButton3);
		ib4=(ImageButton)findViewById(R.id.imageButton4);
		ib5=(ImageButton)findViewById(R.id.imageButton5);
		ib6=(ImageButton)findViewById(R.id.imageButton6);
		ib7=(ImageButton)findViewById(R.id.imageButton7);
		ib8=(ImageButton)findViewById(R.id.imageButton8);
		ib9=(ImageButton)findViewById(R.id.imageButton9);
		ib10=(ImageButton)findViewById(R.id.imageButton10);
		
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		String destDir = "/data/data/" + getPackageName() +
				"/databases/";
				String destPath = destDir + "CallJugnu";
				File f = new File(destPath);
				if (!f.exists()) {
				//---make sure directory exists---
				File directory = new File(destDir);
				directory.mkdirs();
				//---copy the db from the assets folder into
				// the databases folder---
				try 
				{
				CopyDB(getBaseContext().getAssets().open("CallJugnu"),
				new FileOutputStream(destPath));
				} catch (FileNotFoundException e) {
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
				}
				db = new DBAdapter(this);
				db.open();
		        Cursor cursor=db.getAllItems("cart");
		        if (cursor.moveToFirst()) {
		        	Show_Dialog();
		        }
		        db.close();
				
		ib1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, TasteBuds.class);
	            startActivity(intent);
			} 
		});
		
		ib2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, Munching.class);
	            startActivity(intent);
			} 
		});
		
		ib3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, Combo.class);
	            startActivity(intent);
			} 
		});
		
		ib4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, BlowOff.class);
	            startActivity(intent);
			} 
		});
		
		ib5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, RapidAction.class);
	            startActivity(intent);
			} 
		});
		
		ib6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, Celebration.class);
	            startActivity(intent);
			} 
		});
		
		ib7.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, SipSap.class);
	            startActivity(intent);
			} 
		});
		
		ib8.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, EnquiryScreen.class);
	            startActivity(intent);
			} 
		});

		ib9.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, TermsScreen.class);
	            startActivity(intent);
			} 
		});

		ib10.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, Cart.class);
	            startActivity(intent);
			} 
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
			//---copy 1K bytes at a time---
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
			}
			inputStream.close();
			outputStream.close();
		}
	
	public void Show_Dialog() {
		final PromptDialog pd = new PromptDialog(HomePage.this);
		pd.show();
		TextView t1=(TextView) pd.findViewById(R.id.textView1);
		t1.setText("Your cart already contain some items.\nWhat would you like to do ?");
		Button reset = (Button) pd.findViewById(R.id.button1);
		Button cont = (Button) pd.findViewById(R.id.button2);
		reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	db.open();
		        boolean abc=db.deleteAllItems();
		        if(abc)
		        {
		        	Toast.makeText(getApplicationContext(), "Your cart has been cleaned.", Toast.LENGTH_LONG).show();
		        }
		        db.close();
		        pd.dismiss();
            }
        });
		cont.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	pd.dismiss();
            }
        });
		}
}