package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class League_Data_Model  {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("game_title")
    @Expose
    private String gameTitle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("total_coin")
    @Expose
    private String totalCoin;
    @SerializedName("start_date_time")
    @Expose
    private String startDateTime;

    @SerializedName("played")
    @Expose
    private Integer played;

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("naira_prize")
    @Expose
    private String naira_prize;
    @SerializedName("questions")
    @Expose
    private List<League_Question_Model> league_questions = null;

    @SerializedName("rewards")
    @Expose
    private ArrayList<LeagueReward> rewards = null;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(String totalCoin) {
        this.totalCoin = totalCoin;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getNaira_prize() {
        return naira_prize;
    }

    public void setNaira_prize(String naira_prize) {
        this.naira_prize = naira_prize;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public List<League_Question_Model> getLeague_questions() {
        return league_questions;
    }

    public void setQuestions(List<League_Question_Model> questions) {
        this.league_questions = questions;
    }
    public ArrayList<LeagueReward> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<LeagueReward> rewards) {
        this.rewards = rewards;
    }

}
