package com.example.smarttext.utils;

import java.util.ArrayList;

public class LiveChatData {
    private int sedRec;
    private ArrayList<String> data;
    private ArrayList<String> time;
    private String sandingTime;
    public LiveChatData() {
        data=new ArrayList<>();
        time=new ArrayList<>();
    }

    public int getSedRec() {
        return sedRec;
    }

    public void setSedRec(int sedRec) {
        this.sedRec = sedRec;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public String getSandingTime() {
        return sandingTime;
    }

    public void setSandingTime(String sandingTime) {
        this.sandingTime = sandingTime;
    }

    @Override
    public String toString() {
        return "LiveChatData{" +
                "sedRec=" + sedRec +
                ", data=" + data +
                ", time=" + time +
                ", sandingTime='" + sandingTime + '\'' +
                '}';
    }
}
