package com.example.samplestickerapp;

public class CategoryItem {
    private int image;
    private String Type;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public CategoryItem(int image, String type) {
        this.image = image;
        Type = type;
    }
}
