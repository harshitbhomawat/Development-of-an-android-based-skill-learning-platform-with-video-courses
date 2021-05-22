package com.example.skillz_minor;

public class regHelperclass {
    String email,pass,mob,name,gen,uid;
    public regHelperclass() {
    }

    public regHelperclass(String name, String email, String mob, String pass, String gen,String uid) {
        this.email = email;
        this.pass = pass;
        this.mob = mob;
        this.name = name;
        this.gen = gen;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

