package com.example.smarttext.utils;

public class ContactData {
    private String name;
    private String phone_no;
    private int ImageUrl;

    public ContactData(String name, String phone_no ) {
        this.name = name;
        this.phone_no = phone_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phone_no;
    }

    public void setPhoneNo(String phone_no) {
        this.phone_no = phone_no;
    }

    public int getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(int imageUrl) {
        ImageUrl = imageUrl;
    }
}
