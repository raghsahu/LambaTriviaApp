package com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Show_Practice_Model {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<QuizDataCount> data = null;
//    @SerializedName("msg")
//    @Expose
//    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<QuizDataCount> getData() {
        return data;
    }

    public void setData(List<QuizDataCount> data) {
        this.data = data;
    }

//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//
//    }

}
