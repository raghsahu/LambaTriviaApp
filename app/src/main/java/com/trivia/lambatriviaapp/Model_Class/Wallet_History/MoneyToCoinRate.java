package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

public class MoneyToCoinRate {
    String id;
    String coin;
    String naira;


    public MoneyToCoinRate(String id, String coin, String naira) {
        this.coin=coin;
        this.id=id;
        this.naira=naira;
    }

    public String getId() {
        return id;
    }

    public String getNaira() {
        return naira;
    }

    public void setNaira(String naira) {
        this.naira = naira;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public void setId(String id) {
        this.id = id;
    }
}
