package com.example.smarttext.utils;

public class ContactData {
    private String name;
    private String phone_no;
    private String ImageUrl;

    public ContactData(String name, String phone_no ) {
        this.name = name;
        this.phone_no = phone_no;
    }

    public ContactData(String name, String phone_no, String imageUrl) {
        this.name = name;
        this.phone_no = phone_no;
        ImageUrl = imageUrl;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
