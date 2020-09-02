package com.example.quickmemo;

class ItemManegementListItem {
    private long id = 0;
    private String itemName = null;
    private String itemUrl = null;
    private String categoryName = null;
    private String categoryColor = null;

    long getId() { return id; }
    String getItemName() { return itemName; }
    String getItemUrl() { return itemUrl; }
    String getCategoryName() { return categoryName; }
    String getCategoryColor() { return categoryColor; }

    void setId(long id) { this.id = id; }
    void setItemName(String itemName) { this.itemName = itemName; }
    void setItemUrl(String itemUrl) { this.itemUrl = itemUrl; }
    void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
}
