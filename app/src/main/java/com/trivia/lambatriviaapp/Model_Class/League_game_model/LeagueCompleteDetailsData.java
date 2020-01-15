package com.trivia.lambatriviaapp.Model_Class.League_game_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueCompleteDetailsData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("game_title")
    @Expose
    private String gameTitle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("total_coin")
    @Expose
    private String totalCoin;
    @SerializedName("naira_prize")
    @Expose
    private String nairaPrize;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("start_date_time")
    @Expose
    private String startDateTime;
    @SerializedName("rewards")
    @Expose
    private List<LeagueReward> rewards = null;
    @SerializedName("questions")
    @Expose
    private List<MyCompleteQuestion> questions = null;

    @SerializedName("winners")
    @Expose
    private List<WinnerListLeague> winners = null;

    @SerializedName("my_ranks")
    @Expose
    private MyRanks myRanks;

    public String getId() {
        return id;
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

    public MyRanks getMyRanks() {
        return myRanks;
    }

    public void setMyRanks(MyRanks myRanks) {
        this.myRanks = myRanks;
    }

    public String getTotalCoin() {
        return totalCoin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTotalCoin(String totalCoin) {
        this.totalCoin = totalCoin;
    }

    public String getNairaPrize() {
        return nairaPrize;
    }

    public void setNairaPrize(String nairaPrize) {
        this.nairaPrize = nairaPrize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public List<LeagueReward> getRewards() {
        return rewards;
    }

    public void setRewards(List<LeagueReward> rewards) {
        this.rewards = rewards;
    }

    public List<WinnerListLeague> getWinners() {
        return winners;
    }

    public void setWinners(List<WinnerListLeague> winners) {
        this.winners = winners;
    }

    public List<MyCompleteQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MyCompleteQuestion> questions) {
        this.questions = questions;
    }
}
