package com.example.calljugnu;

import java.util.List;
import java.util.Map;
 
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Activity context;
    private Map<String, List<String>> itemCollections;
    private List<String> items;
    private List<String> prices;
    private List<Integer> images;
    private List<Integer> images01;
    
    public ExpandableListAdapter(Activity context, List<Integer> images, List<Integer> images01, List<String> items,
            Map<String, List<String>> itemCollections, List<String> priceList) {
        this.context = context;
        this.itemCollections = itemCollections;
        this.items = items;
        this.prices= priceList;
        this.images = images;
        this.images01 = images01;
        
    }
 
    public Object getChild(int groupPosition, int childPosition) {
        return itemCollections.get(items.get(groupPosition)).get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    public View getChildView(final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        final String description = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        Integer imageId01 = (Integer) getImage01(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
 
        TextView item = (TextView) convertView.findViewById(R.id.textView1);
        item.setText(description);
        
            ImageView iv2=(ImageView)convertView.findViewById(R.id.imageView1);
            iv2.setImageResource(imageId01);        	        	
            
        return convertView;
    }
 
    public int getChildrenCount(int groupPosition) {
        return itemCollections.get(items.get(groupPosition)).size();
    }
 
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }
    
    public Object getPrice(int groupPosition) {
        return prices.get(groupPosition);
    }

    public Object getImage(int groupPosition) {
        return images.get(groupPosition);
    }
    
    public Object getImage01(int groupPosition) {
        return images01.get(groupPosition);
    }
    
    public int getGroupCount() {
        return items.size();
    }
 
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        String cost = (String) getPrice(groupPosition);
        Integer imageId = (Integer) getImage(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.textView1);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        TextView price = (TextView) convertView.findViewById(R.id.textView2);
        price.setText("Price : \u20B9 "+cost);
        ImageView iv1=(ImageView)convertView.findViewById(R.id.imageView2);
        iv1.setImageResource(imageId);
        return convertView;
    }
    
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
