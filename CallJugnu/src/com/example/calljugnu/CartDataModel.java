package com.example.calljugnu;

public class CartDataModel {
	private String item_name;
    private String price;
    private String quantity;
 
    public CartDataModel(String item_name, String price, String quantity) {
        this.item_name = item_name;
        this.price = price;
        this.quantity = quantity;
    }
 
    public String getItemName() {
        return item_name;
    }
 
    public void setItemName(String item_name) {
        this.item_name = item_name;
    }
    
    public String getPrice() {
        return price;
    }
 
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getQuantity() {
        return quantity;
    }
 
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}