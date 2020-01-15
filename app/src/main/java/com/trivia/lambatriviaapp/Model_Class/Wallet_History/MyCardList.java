package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

import java.io.Serializable;

/**
 * Created by Raghvendra Sahu on 26/11/2019.
 */
public class MyCardList implements Serializable {
    String id;
    String user_id;
    String card_holder;
    String cart_number;
    String expiry_month;
    String expiry_year;
    String cart_ccv;

    public MyCardList(String id, String user_id, String card_holder, String cart_number, String expiry_month, String expiry_year, String cart_ccv) {
        this.id = id;
        this.user_id = user_id;
        this.card_holder = card_holder;
        this.cart_number = cart_number;
        this.expiry_month = expiry_month;
        this.expiry_year = expiry_year;
        this.cart_ccv = cart_ccv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public String getCart_number() {
        return cart_number;
    }

    public void setCart_number(String cart_number) {
        this.cart_number = cart_number;
    }

    public String getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(String expiry_month) {
        this.expiry_month = expiry_month;
    }

    public String getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(String expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getCart_ccv() {
        return cart_ccv;
    }

    public void setCart_ccv(String cart_ccv) {
        this.cart_ccv = cart_ccv;
    }
}
