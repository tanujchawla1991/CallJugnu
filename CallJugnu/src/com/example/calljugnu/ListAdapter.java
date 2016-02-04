package com.example.calljugnu;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	 ArrayList<CartDataModel> listArray;
	 DBAdapter db;
	 
	    public ListAdapter(ArrayList<CartDataModel> listArray) {
	        this.listArray = listArray;
	    }
	 
	    @Override
	    public int getCount() {
	        return listArray.size();    // total number of elements in the list
	    }
	 
	    @Override
	    public Object getItem(int i) {
	        return listArray.get(i);    // single item in the list
	    }
	 
	    @Override
	    public long getItemId(int i) {
	        return i;                   // index number
	    }
	 
	    @Override
	    public View getView(int index, View view, final ViewGroup parent) {
	 
	        if (view == null) {
	            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	            view = inflater.inflate(R.layout.cart_item, parent, false);
	        }
	 
	        final CartDataModel dataModel = listArray.get(index);
	 
	        TextView textView = (TextView) view.findViewById(R.id.textView1);
	        textView.setText(dataModel.getItemName());
	        
	        
	        int xyz=Integer.parseInt(dataModel.getPrice())*Integer.parseInt(dataModel.getQuantity());
	        textView = (TextView) view.findViewById(R.id.textView2);
	        textView.setText(dataModel.getPrice()+" X "+dataModel.getQuantity()+" = "+xyz);
	        
	        return view;
	    }
	    
	}