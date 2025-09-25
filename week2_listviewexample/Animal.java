package com.example.week2_listviewexample;

public class Animal {
    public void setPicId(int picId) {
        this.picId = picId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPicId() {
        return picId;
    }

    public String getType() {
        return type;
    }

    public Animal(int picId, String type) {
        this.picId = picId;
        this.type = type;
    }

    private String type;
    private int picId;
}
