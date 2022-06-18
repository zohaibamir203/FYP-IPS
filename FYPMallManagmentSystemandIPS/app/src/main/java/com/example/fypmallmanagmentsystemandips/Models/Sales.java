package com.example.fypmallmanagmentsystemandips.Models;

public class Sales {

    private String shopName;
    private String shopOffers;

    public Sales(String shopName, String shopOffers) {
        this.shopName = shopName;
        this.shopOffers = shopOffers;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOffers() {
        return shopOffers;
    }

    public void setShopOffers(String shopOffers) {
        this.shopOffers = shopOffers;
    }

}
