package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyRanks {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("my_rank")
    @Expose
    private String myRank;
    @SerializedName("my_total_coin")
    @Expose
    private String myTotalCoin;
    @SerializedName("my_amount")
    @Expose
    private String myAmount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMyRank() {
        return myRank;
    }

    public void setMyRank(String myRank) {
        this.myRank = myRank;
    }

    public String getMyTotalCoin() {
        return myTotalCoin;
    }

    public void setMyTotalCoin(String myTotalCoin) {
        this.myTotalCoin = myTotalCoin;
    }

    public String getMyAmount() {
        return myAmount;
    }

    public void setMyAmount(String myAmount) {
        this.myAmount = myAmount;
    }
}
