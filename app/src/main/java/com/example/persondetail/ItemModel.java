package com.example.persondetail;

public class ItemModel {
    private String item_id;
    private String itemName;
    private String itemPrice;

    private String itemDescription;
    private String itemEmail;
    private String itemPhone;



    public ItemModel(String itemName, String itemPrice, String itemDescription, String phone, String item_id, String email) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.item_id = item_id;
        this.itemPhone =phone;
        this.itemEmail = email;


    }

    // Getter and Setter
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
    public String getItem_id() {
        return item_id;
    }

    public void setItemEmail(String itemEmail) {
        this.itemEmail = itemEmail;
    }
    public String getItemEmail() {
        return itemEmail;
    }
    public void setItemPhone(String itemPhone) {
        this.itemEmail = itemPhone;
    }
    public String getItemPhone() {
        return itemPhone;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
    public String getItemDescription() { return  itemDescription; }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
}
