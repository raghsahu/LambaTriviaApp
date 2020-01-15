package com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizDataCount {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_coin")
    @Expose
    private String totalCoin;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("questions")
    @Expose
    private List<Question_practice> questions = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(String totalCoin) {
        this.totalCoin = totalCoin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Question_practice> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question_practice> questions) {
        this.questions = questions;
    }


}
