package com.example.skillz_minor;

import java.util.ArrayList;

public class enrollHelperClass {
    String desc,link,title;
    ArrayList<String> list;
    public enrollHelperClass() {
    }
    public enrollHelperClass(String desc, String link, String title, ArrayList<String> list) {
        this.desc = desc;
        this.link = link;
        this.title = title;
        this.list = list;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
