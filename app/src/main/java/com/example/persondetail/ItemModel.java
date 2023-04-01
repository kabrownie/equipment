package com.example.persondetail;

public class ItemModel {
    private String itemName;
    private String itemPrice;

    private String itemDescription;

    public ItemModel(String itemName, String itemPrice, String itemDescription) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
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

    public String getItemDescription() { return  itemDescription; }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
}
