package com.example.skillz_minor;

public class newsmodelclass {
    String text,link;

    public newsmodelclass() {
    }

    public newsmodelclass(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
