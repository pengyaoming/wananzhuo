package com.jxmfkj.www.myapplication.Entity;

public class MineEntity {
    private String name;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MineEntity(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
