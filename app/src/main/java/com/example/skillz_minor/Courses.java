package com.example.skillz_minor;

public class Courses {
    String title;
    Integer i;
    Integer image;
    public Courses(int image, String title,int i){
        this.title= title;
        this.image = image;
        this.i = i;
    }
    public Integer getI() {
        return i;
    }
    public void setI(Integer i) {
        this.i = i;
    }
    public String getTitle() {
        return title;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
