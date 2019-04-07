package com.example.smarttext.utils;

public class ActiveNow {
    private int active;
    private int lastActiveTime;

    @Override
    public String toString() {
        return "ActiveNow{" +
                "active=" + active +
                ", lastActiveTime=" + lastActiveTime +
                '}';
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(int lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
