package com.example.quickmemo.Entity;

public class ItemManegementListItem {
    private long id = 0;
    private String itemName = null;
    private String itemUrl = null;
    private String categoryName = null;
    private String categoryColor = null;

    public long getId() { return id; }
    public String getItemName() { return itemName; }
    public String getItemUrl() { return itemUrl; }
    public String getCategoryName() { return categoryName; }
    public String getCategoryColor() { return categoryColor; }

    public void setId(long id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemUrl(String itemUrl) { this.itemUrl = itemUrl; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
}
