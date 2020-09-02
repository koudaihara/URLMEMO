package com.example.quickmemo;

class CategoryManegementListItem {
    private long id = 0;
    private String categoryColor = null;
    private String categoryName = null;

    long getId() { return id; }
    String getCategoryColor() { return categoryColor; }
    String getCategoryName() { return categoryName; }

    void setId(long id) { this.id = id; }
    void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
    void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
