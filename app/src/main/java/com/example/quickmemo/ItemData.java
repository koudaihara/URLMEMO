package com.example.quickmemo;

class ItemData {
    private String itemName = null;
    private String itemUrl = null;
    private String categoryColor = null;
    private String categoryName = null;

    String getItemName() { return itemName; }
    String getItemUrl() { return itemUrl; }
    String getCategoryColor() { return categoryColor; }
    String getCategoryName() { return categoryName; }

    void setItemName(String itemName) { this.itemName = itemName; }
    void setItemUrl(String itemUrl) { this.itemUrl = itemUrl; }
    void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
    void setCategoryName(String categoryName) { this.categoryName = categoryName; }

}
