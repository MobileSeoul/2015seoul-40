package com.seoul.hanokmania.models;

/**
 * Created by Ray Choe on 2015-10-20.
 */
public class BukchonItem {

    private String name;
    private String address;
    private String owner;
    private String phoneNum;
    private String homePage;
    private String cultural;
    private String type;
    private String content;
    private String house_id;

    public BukchonItem(String name, String address, String owner, String phoneNum, String homePage) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.phoneNum = phoneNum;
        this.homePage = homePage;
    }

    public BukchonItem(String name, String address, String owner, String phoneNum, String homePage, String cultural, String type) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.phoneNum = phoneNum;
        this.homePage = homePage;
        this.cultural = cultural;
        this.type = type;
    }

    public BukchonItem(String name, String address, String owner, String phoneNum, String homePage, String cultural, String type, String content) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.phoneNum = phoneNum;
        this.homePage = homePage;
        this.cultural = cultural;
        this.type = type;
        this.content = content;
    }

    public BukchonItem(String name, String address, String owner, String phoneNum, String homePage, String cultural, String type, String content, String house_id) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.phoneNum = phoneNum;
        this.homePage = homePage;
        this.cultural = cultural;
        this.type = type;
        this.content = content;
        this.house_id = house_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getType() {
        return type;
    }

    public String getCultural() {
        return cultural;
    }
}
