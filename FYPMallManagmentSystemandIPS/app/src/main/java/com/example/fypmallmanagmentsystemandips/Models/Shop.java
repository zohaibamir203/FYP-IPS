package com.example.fypmallmanagmentsystemandips.Models;

import java.util.ArrayList;

public class Shop {
    String shopName,ShopAddress,shopUID;
    ArrayList<String> salesAlert,shopCategory;

    public Shop(String shopName, String shopAddress, String shopUID, ArrayList<String> salesAlert, ArrayList<String> shopCategory) {
        this.shopName = shopName;
        ShopAddress = shopAddress;
        this.shopUID = shopUID;
        this.salesAlert = salesAlert;
        this.shopCategory = shopCategory;
    }

    public Shop(String shopName, String shopAddress, String shopUID) {
        this.shopName = shopName;
        ShopAddress = shopAddress;
        this.shopUID = shopUID;
    }

    public Shop(){}


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return ShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        ShopAddress = shopAddress;
    }

    public String getShopUID() {
        return shopUID;
    }

    public void setShopUID(String shopUID) {
        this.shopUID = shopUID;
    }

    public ArrayList<String> getSalesAlert() {
        return salesAlert;
    }

    public void setSalesAlert(ArrayList<String> salesAlert) {
        this.salesAlert = salesAlert;
    }

    public ArrayList<String> getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ArrayList<String> shopCategory) {
        this.shopCategory = shopCategory;
    }
}
