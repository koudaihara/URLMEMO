package com.example.quickmemo.Entity;

public class ItemData {
    private String itemName = null;
    private String itemUrl = null;
    private String categoryColor = null;
    private String categoryName = null;

    public String getItemName() { return itemName; }
    public String getItemUrl() { return itemUrl; }
    public String getCategoryColor() { return categoryColor; }
    public String getCategoryName() { return categoryName; }

    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemUrl(String itemUrl) { this.itemUrl = itemUrl; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

}
