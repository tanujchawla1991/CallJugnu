package com.example.calljugnu;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Cart extends Activity {
	ArrayList<CartDataModel> listArray;
    static DBAdapter db;
    int total=50;
    String abc="";
    String def="";
    TextView tv1;
    Button b1,b2;
	SharedPreferences prefs;
	String prefName ="myPref";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		db = new DBAdapter(this);
		listArray = new ArrayList<CartDataModel>();
        db.open();
        Cursor cursor=db.getCart();
        if (cursor.moveToFirst()) {
        	do{
        String cart_item=cursor.getString(1);
        String cart_price=cursor.getString(3);
        String cart_quantity=cursor.getString(2);
        int xyz=Integer.parseInt(cart_price)*Integer.parseInt(cart_quantity);
        
        total=total+xyz;
        abc=abc+"\n Item Name="+cart_item+" ; Price="+cart_price+" ; Quantity="+cart_quantity+" ; Subtotal="+xyz;
        def=def+"\n"+cart_item+";"+cart_price+";"+cart_quantity+"="+xyz;
        
        listArray.add(new CartDataModel(cart_item, cart_price, cart_quantity));
        	} while (cursor.moveToNext());
        }
        ListView listView = (ListView) findViewById(R.id.listView1);		 
        ListAdapter listAdapter = new ListAdapter(listArray);    
        listView.setAdapter(listAdapter);
        db.close();
		
        abc=abc+"\nTotal="+total;
        def=def+"\nTotal="+total;
        tv1=(TextView)findViewById(R.id.textView2);
        tv1.setText("Total = \u20B9 "+total);
        
        prefs=getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("Order_Mail",abc);
        editor.putString("Order_Phone", def);
        editor.commit();
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              CartDataModel dm = (CartDataModel) adapterView.getItemAtPosition(i);
              String id=dm.getItemName();
              db.open();
              db.deleteItem(id);
              db.close();
              Intent intent = new Intent(Cart.this, Cart.class);
	          startActivity(intent);
          }
      });
        
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Cart.this, PlaceOrder.class);
	            startActivity(intent);
			} 
		});
		
		b2=(Button)findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Cart.this, HomePage.class);
	            startActivity(intent);
			} 
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart, menu);
		return true;
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
}