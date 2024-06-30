package com.kis.mypay.MainActivity.ui.rate;
public class DataModel {
    private final String title;

    private final int country_icon;

    private final int money_icon;

    private final double rate;

    public DataModel(String title, int imageResId, int money_icon, double rate) {
        this.title = title;
        this.country_icon = imageResId;
        this.money_icon = money_icon;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return country_icon;
    }

    public int getMoneyIcon() {
        return money_icon;
    }

    public double getRate() {
        return rate;
    }
}
