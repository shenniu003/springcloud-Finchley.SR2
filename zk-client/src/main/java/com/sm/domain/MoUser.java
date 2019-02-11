package com.sm.domain;

/**
 * Created by Administrator on 2019/1/26.
 */
public class MoUser {

    private int id;
    private String name;

    public MoUser(){}

    public MoUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}