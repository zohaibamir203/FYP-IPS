package com.example.fypmallmanagmentsystemandips.Models;

public class Shopkeeper {
    String shopName,shopMail,shopPass,shopAddress;

    public Shopkeeper(String shopName, String shopMail, String shopPass, String shopAddress) {
        this.shopName = shopName;
        this.shopMail = shopMail;
        this.shopPass = shopPass;
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMail() {
        return shopMail;
    }

    public void setShopMail(String shopMail) {
        this.shopMail = shopMail;
    }

    public String getShopPass() {
        return shopPass;
    }

    public void setShopPass(String shopPass) {
        this.shopPass = shopPass;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
