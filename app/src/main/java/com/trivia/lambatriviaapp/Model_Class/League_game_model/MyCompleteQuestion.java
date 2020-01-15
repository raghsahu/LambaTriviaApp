package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCompleteQuestion {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("option_a")
    @Expose
    private String optionA;
    @SerializedName("option_b")
    @Expose
    private String optionB;
    @SerializedName("option_c")
    @Expose
    private String optionC;
    @SerializedName("my_ans")
    @Expose
    private String myAns;
    @SerializedName("option_a_per")
    @Expose
    private Double optionAPer;
    @SerializedName("option_b_per")
    @Expose
    private Double optionBPer;
    @SerializedName("option_c_per")
    @Expose
    private Double optionCPer;
    @SerializedName("prediction_description")
    @Expose
    private String predictionDescription;
    @SerializedName("coin")
    @Expose
    private String coin;
    @SerializedName("naira_currency")
    @Expose
    private String nairaCurrency;
    @SerializedName("correct_ans")
    @Expose
    private String correctAns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMyAns() {
        return myAns;
    }

    public void setMyAns(String myAns) {
        this.myAns = myAns;
    }

    public Double getOptionAPer() {
        return optionAPer;
    }

    public void setOptionAPer(Double optionAPer) {
        this.optionAPer = optionAPer;
    }

    public Double getOptionBPer() {
        return optionBPer;
    }

    public void setOptionBPer(Double optionBPer) {
        this.optionBPer = optionBPer;
    }

    public Double getOptionCPer() {
        return optionCPer;
    }

    public void setOptionCPer(Double optionCPer) {
        this.optionCPer = optionCPer;
    }

    public String getPredictionDescription() {
        return predictionDescription;
    }

    public void setPredictionDescription(String predictionDescription) {
        this.predictionDescription = predictionDescription;
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
}
