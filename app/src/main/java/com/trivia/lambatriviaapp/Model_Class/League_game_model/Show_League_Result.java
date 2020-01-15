package com.trivia.lambatriviaapp.Model_Class.League_game_model;

public class Show_League_Result {
    String id;
    String game_title;
    String total_coin;
    String naira_prize;
    String start_date_time;
    String image;

    public Show_League_Result(String id, String game_title, String total_coin, String naira_prize,
                              String start_date_time, String image) {
        this.id=id;
        this.game_title=game_title;
        this.total_coin=total_coin;
        this.naira_prize=naira_prize;
        this.start_date_time=start_date_time;
        this.image=image;

    }

    public String getId() {
        return id;
    }

    public String getNaira_prize() {
        return naira_prize;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public void setNaira_prize(String naira_prize) {
        this.naira_prize = naira_prize;
    }

    public String getTotal_coin() {
        return total_coin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTotal_coin(String total_coin) {
        this.total_coin = total_coin;
    }

    public String getGame_title() {
        return game_title;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }

    public void setId(String id) {
        this.id = id;
    }
}
