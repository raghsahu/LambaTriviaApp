package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Show_League_Complete_Details {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("rules")
    @Expose
    private String rules;
    @SerializedName("data")
    @Expose
    private LeagueCompleteDetailsData data;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public LeagueCompleteDetailsData getData() {
        return data;
    }

    public void setData(LeagueCompleteDetailsData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
