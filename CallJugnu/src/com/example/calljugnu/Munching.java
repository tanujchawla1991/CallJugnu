package com.example.calljugnu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class Munching extends Activity {
	List<Integer> imageList;
	List<Integer> childImageList;
	List<String> priceList;
	List<String> groupList;
    List<String> childList;
    Map<String, List<String>> itemCollection;
    ExpandableListView expListView;
    DBAdapter db;
    ImageButton ib1;
    ImageButton ib2;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_munching);
		
		if (!isTablet(this))
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Munching.this, HomePage.class);
	            startActivity(intent);
			} 
		});
		
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		ib2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Munching.this, Cart.class);
	            startActivity(intent);
			} 
		});
		
				db = new DBAdapter(this);
	
		createGroupList();
		 
        createCollection();
 
        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, imageList, childImageList, groupList, itemCollection, priceList);
        expListView.setAdapter(expListAdapter);
        
        setGroupIndicatorToRight();
        
        expListView.setOnChildClickListener(new OnChildClickListener() {
        	
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
            	final String sss=groupList.get(groupPosition);
            	String cost = (String) expListAdapter.getPrice(groupPosition);
            	Show_Dialog(sss,cost);
                return false;
            }
        });
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
	
    private void createGroupList() {
        groupList = new ArrayList<String>();
        imageList = new ArrayList<Integer>();
        childImageList = new ArrayList<Integer>();
        priceList= new ArrayList<String>();
        
        db.open();
        Cursor cursor=db.getAllItems("munching");
        if (cursor.moveToFirst()) {
        	do{
        groupList.add(cursor.getString(0));
        priceList.add(cursor.getString(1));
        String xyz=cursor.getString(3);
        String xxyyzz=xyz.replaceAll(".jpg", "");
        int resId=getResources().getIdentifier(xxyyzz, "drawable", getPackageName());
        imageList.add(resId);
        childImageList.add(resId);
        	} while (cursor.moveToNext());
        }
        db.close();
    }
 
    private void createCollection() {
        db.open();
        itemCollection = new LinkedHashMap<String, List<String>>();
        
        Cursor cursor=db.getAllItems("munching");
        int i=0;
        if (cursor.moveToFirst()) {
        	do{
        	String item=groupList.get(i);
            if (item.equals(cursor.getString(0)))
            {
                String[] abc={cursor.getString(2)};
            	loadChild(abc);
            }
            itemCollection.put(item, childList);
            i++;
        } while (cursor.moveToNext());
        	}
        db.close();
    }
 
    private void loadChild(String[] items001) {
        childList = new ArrayList<String>();
        for (String model : items001)
            childList.add(model);
    }
 
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
 
        expListView.setIndicatorBounds(width - getDipsFromPixel(60), width
                - getDipsFromPixel(20));
    }
 
    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.munching, menu);
		return true;
	}

	public void Show_Dialog(String ss,String cost) {
		final QuantityDialog qd = new QuantityDialog(Munching.this);
		qd.show();
		Button ok = (Button) qd.findViewById(R.id.button1);
		Button cancel = (Button) qd.findViewById(R.id.button2);
		final String ssss=ss;
		final String price=cost;
		ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	EditText e1=(EditText)qd.findViewById(R.id.editText1);
            	final String xyz=e1.getText().toString();
            	db.open();
		        db.insertContact(ssss, price, xyz);
		        db.close();
            	Toast.makeText(getApplicationContext(), xyz+" "+ssss+" added to cart.", Toast.LENGTH_LONG).show();
            	qd.dismiss();
            }
        });
		cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	qd.dismiss();
            }
        });
		}
	
}