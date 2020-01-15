package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Show_League_Model {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<League_Data_Model> data = null;
//    @SerializedName("msg")
//    @Expose
//    private String msg;

    @SerializedName("rules")
    @Expose
    private String rules;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<League_Data_Model> getData() {
        return data;
    }

    public void setData(List<League_Data_Model> data) {
        this.data = data;
    }

//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
