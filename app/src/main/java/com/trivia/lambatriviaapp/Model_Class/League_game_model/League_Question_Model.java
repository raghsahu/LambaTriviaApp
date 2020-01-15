package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class League_Question_Model {
    @SerializedName("ques_num")
    @Expose
    private Integer ques_num;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("league_id")
    @Expose
    private String leagueId;
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
    @SerializedName("prediction_description")
    @Expose
    private String predictionDescription;
    @SerializedName("coin")
    @Expose
    private String coin;
    @SerializedName("naira_currency")
    @Expose
    private String nairaCurrency;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public Integer getQues_num() {
        return ques_num;
    }

    public void setQues_num(Integer ques_num) {
        this.ques_num = ques_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
