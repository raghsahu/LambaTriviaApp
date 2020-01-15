package com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question_practice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("p_quiz_eid")
    @Expose
    private String pQuizEid;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_a")
    @Expose
    private String optionA;
    @SerializedName("option_b")
    @Expose
    private String optionB;
    @SerializedName("option_c")
    @Expose
    private String optionC;
    @SerializedName("correct_ans")
    @Expose
    private String correctAns;
    @SerializedName("coin")
    @Expose
    private String coin;
    @SerializedName("naira_currency")
    @Expose
    private String nairaCurrency;
    @SerializedName("date")
    @Expose
    private String date;

//    public Question_practice(String setcoin, String set_question, String ans1, String ans2,
//                             String ans3, String correct_ans) {
//        this.coin=setcoin;
//        this.question=set_question;
//        this.optionA=ans1;
//        this.optionB=ans2;
//        this.optionC=ans3;
//        this.correctAns=correct_ans;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPQuizEid() {
        return pQuizEid;
    }

    public void setPQuizEid(String pQuizEid) {
        this.pQuizEid = pQuizEid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getNairaCurrency() {
        return nairaCurrency;
    }

    public void setNairaCurrency(String nairaCurrency) {
        this.nairaCurrency = nairaCurrency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
