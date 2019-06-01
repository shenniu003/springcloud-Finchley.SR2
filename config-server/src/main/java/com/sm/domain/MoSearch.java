package com.sm.domain;

import java.io.Serializable;

public class MoSearch implements Serializable {
    public MoSearch() {
    }

    public MoSearch(int from, int size, String keyword01, String startDate, String endDate) {
        this.from = from;
        this.size = size;
        this.keyword01 = keyword01;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //        private static final long serialVersionUID = -3919074999623209437L;
    private int from;
    private int size;
    private String keyword01;
    private String level;
    private String startDate;
    private String endDate;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getKeyword01() {
        return keyword01;
    }

    public void setKeyword01(String keyword01) {
        this.keyword01 = keyword01;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
