package com.example.skillz_minor;

public class videos {
    String videonumber,videotitle,link;
    public videos(String videonumber, String videotitle, String link){
        this.videonumber= videonumber;
        this.videotitle = videotitle;
        this.link = link;
    }

    public String getvideonumber() {
        return videonumber;
    }

    public String getvideotitle() {
        return videotitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setvideonumber(String videonumber) {
        this.videonumber = videonumber;
    }

    public void setvideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

}
