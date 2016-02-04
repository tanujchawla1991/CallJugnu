package com.example.calljugnu;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnquiryScreen extends Activity {
	EditText e1,e2,e3,e4,e5;
	Button b1,b2;
	ImageButton ib1,ib2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enquiry_screen);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib2=(ImageButton)findViewById(R.id.imageButton2);
			
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
		ib1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EnquiryScreen.this, HomePage.class);
	            startActivity(intent);
			} 
		});
		
		ib2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EnquiryScreen.this, Cart.class);
	            startActivity(intent);
			} 
		});
		
		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e1.setText("");
				e2.setText("");
				e3.setText("");
				e4.setText("");
				e5.setText("");
			} 
		});
		
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(e1.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
					return;
				}
				else if(e2.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Email Id", Toast.LENGTH_LONG).show();					
					return;
				}
				else if (!e2.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+") && e2.length() > 0)
	            {
					Toast.makeText(getApplicationContext(), "Please Enter a Valid Email Id", Toast.LENGTH_LONG).show();					
					return;
	            }
				String a="Name : "+e1.getText().toString();
				String b="Email Id : "+e2.getText().toString();
				String c="Contact No. : "+e3.getText().toString();
				String d="Subject of Enquiry : "+e4.getText().toString();
				String e="Message :\n"+e5.getText().toString();
				
				boolean y=isInternetOn();
				if(!y)
				{
				    Toast.makeText(EnquiryScreen.this, "No Internet Access.", Toast.LENGTH_LONG).show();				
				    return;
				}

				String t=a+"\n"+b+"\n"+c+"\n"+d+"\n"+e;
				String u=Sending(t);
			    Toast.makeText(EnquiryScreen.this, u, Toast.LENGTH_LONG).show();
			    Intent intent = new Intent(EnquiryScreen.this, HomePage.class);
	            startActivity(intent);
			} 
		});
		
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enquiry_screen, menu);
		return true;
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	public String Sending(String t)
	{
	    try
	    {  
	        SendEmail l=new SendEmail();
	        l.execute(t);
	        String v=l.get();
	        return v;
	    }
	    catch (Exception x)
	    {  
	        Log.e("SendMail", x.getMessage(), x);
	        return x.toString();
	    }
	}
	
	public final boolean isInternetOn() {    
        getBaseContext();
		ConnectivityManager connec =  
                       (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
         
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
               
                return false;
            }
          return false;
        }

}