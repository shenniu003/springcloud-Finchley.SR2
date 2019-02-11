package com.sm.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/8.
 */
public class MoUser implements Serializable {

    private int id;
    private String name;

    public MoUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MoUser() {
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