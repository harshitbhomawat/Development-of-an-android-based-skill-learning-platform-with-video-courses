package com.example.skillz_minor;

import java.util.ArrayList;

public class courseModelClass {
    String title,desc,link;
    ArrayList<String> list;
    public courseModelClass() {
    }

    public courseModelClass(String title, String desc, String link, ArrayList<String> list) {
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}

