package com.example.fypmallmanagmentsystemandips.Models;

public class ShopOffers {

    String shopName,Offer;

    public ShopOffers(String offer) {
        this.Offer = offer;
    }

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }
}
