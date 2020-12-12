package com.example.quickmemo.Entity;

public class CategoryManegementListItem {
    private long id = 0;
    private String categoryColor = null;
    private String categoryName = null;

    public long getId() { return id; }
    public String getCategoryColor() { return categoryColor; }
    public String getCategoryName() { return categoryName; }

    public void setId(long id) { this.id = id; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
