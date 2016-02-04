package com.example.calljugnu;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PlaceOrder extends Activity {

	EditText e1,e2,e3,e5;
	Button b1,b2;
	ImageButton ib1,ib2;
	DBAdapter db;
	SharedPreferences prefs;
	String prefName ="myPref";
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";
	String u;
	PendingIntent sentPI, deliveredPI;
	BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_order);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e5=(EditText)findViewById(R.id.editText5);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		
		db = new DBAdapter(this);
		deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);
				
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
		ib1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PlaceOrder.this, HomePage.class);
	            startActivity(intent);
			} 
		});
		
		ib2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PlaceOrder.this, Cart.class);
	            startActivity(intent);
			} 
		});
		
		b2.setOnClickListener(new OnClickListener() {
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
				else if(e3.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Contact No.", Toast.LENGTH_LONG).show();					
					return;
				}
				else if(e5.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Delivery Address.", Toast.LENGTH_LONG).show();					
					return;
				}

				prefs=getSharedPreferences(prefName, MODE_PRIVATE);
				String abc=prefs.getString("Order_Mail", "");

				String a="Name : "+e1.getText().toString();
				String b="Email Id : "+e2.getText().toString();
				String c="Contact No. : "+e3.getText().toString();
				String d="Address :\n"+e5.getText().toString();
				String e="Order Summary : "+abc;
				
				boolean y=isInternetOn();
				if(!y)
				{
				    Toast.makeText(PlaceOrder.this, "No Internet Access.", Toast.LENGTH_LONG).show();				
				    return;
				}

				String t=a+"\n"+b+"\n"+c+"\n"+d+"\n"+e;
				String u=Sending(t);
			    Toast.makeText(PlaceOrder.this, u, Toast.LENGTH_LONG).show();
			    if(u.startsWith("Order Placed Successfully."))
				{
					db.open();
			        db.deleteAllItems();
			        db.close();
				}
			    Intent intent = new Intent(PlaceOrder.this, TermsScreen.class);
	            startActivity(intent);
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
				else if(e3.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Contact No.", Toast.LENGTH_LONG).show();					
					return;
				}
				else if(e5.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Your Delivery Address.", Toast.LENGTH_LONG).show();					
					return;
				}
				Show_Dialog();
			} 
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place_order, menu);
		return true;
	}

	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	public String Sending(String t)
	{
	    try
	    {  
	        SendOrderEmail l=new SendOrderEmail();
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
	
	public void Show_Dialog() {
		final AlertDialog ad = new AlertDialog(PlaceOrder.this);
		ad.show();
		Button ok = (Button) ad.findViewById(R.id.button1);
		Button cancel = (Button) ad.findViewById(R.id.button2);
		ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
				prefs=getSharedPreferences(prefName, MODE_PRIVATE);
				String def=prefs.getString("Order_Phone", "");

				String a="Name:"+e1.getText().toString();
				String b="Email Id:"+e2.getText().toString();
				String c="Contact No.:"+e3.getText().toString();
				String d="Address:\n"+e5.getText().toString();
				String e="Order Summary:"+def;
				
				String t=a+"\n"+b+"\n"+c+"\n"+d+"\n"+e;

            	SmsManager sms = SmsManager.getDefault();
            	sms.sendTextMessage("+919034260560", null, t, null, null);
    			Intent intent = new Intent(PlaceOrder.this, TermsScreen.class);
                startActivity(intent);
            	ad.dismiss();
            }
        });
		cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	ad.dismiss();
            }
        });
		}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		smsDeliveredReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
			switch (getResultCode())
			{
			case Activity.RESULT_OK:
			{
				db.open();
			    db.deleteAllItems();
			    db.close();
            	Toast.makeText(getApplicationContext(), "Order Placed Successfully.\nOur team will contact you soon.", Toast.LENGTH_LONG).show();
		        break;
			}
			case Activity.RESULT_CANCELED:
			{
				Toast.makeText(getApplicationContext(), "Your Order Could Not Be Placed. Please try again.", Toast.LENGTH_LONG).show();
		        break;
			}
			}
			}
			};
			
			registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
	}
	
}
