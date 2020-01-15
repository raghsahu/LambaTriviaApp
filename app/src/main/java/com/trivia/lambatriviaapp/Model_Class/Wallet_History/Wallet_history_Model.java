package com.trivia.lambatriviaapp.Model_Class.Wallet_History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.Wallet_History_Data;

import java.util.List;

public class Wallet_history_Model {

    @SerializedName("currently")
    @Expose
    private String currently;
    @SerializedName("total_winning")
    @Expose
    private String totalWinning;
    @SerializedName("data")
    @Expose
    private List<Wallet_History_Data> data = null;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getCurrently() {
        return currently;
    }

    public void setCurrently(String currently) {
        this.currently = currently;
    }

    public String getTotalWinning() {
        return totalWinning;
    }

    public void setTotalWinning(String totalWinning) {
        this.totalWinning = totalWinning;
    }

    public List<Wallet_History_Data> getData() {
        return data;
    }

    public void setData(List<Wallet_History_Data> data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}