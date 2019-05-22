package com.example.smarttext.utils;

import java.util.ArrayList;

public class LiveChatData {
    private int sedRec;
    private String data;
    private String time;

    public int getSedRec() {
        return sedRec;
    }

    public void setSedRec(int sedRec) {
        this.sedRec = sedRec;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LiveChatData{" +
                "sedRec=" + sedRec +
                ", data='" + data + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

